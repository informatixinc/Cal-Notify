package com.informatixinc.calnotify.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import com.informatixinc.calnotify.model.AccountType;
import com.informatixinc.calnotify.model.Address;
import com.informatixinc.calnotify.model.Point;
import com.informatixinc.calnotify.model.PutResponse;
import com.informatixinc.calnotify.model.Session;
import com.informatixinc.calnotify.model.SnsToken;
import com.informatixinc.calnotify.model.SnsType;
import com.informatixinc.calnotify.model.UsState;
import com.informatixinc.calnotify.model.User;
import com.informatixinc.calnotify.model.UserNotification;
import com.informatixinc.calnotify.model.UserSettings;
import com.informatixinc.calnotify.utils.AuthMap;
import com.informatixinc.calnotify.utils.DatabaseUtils;
import com.informatixinc.calnotify.utils.EmailClient;
import com.informatixinc.calnotify.utils.ProjectProperties;
import com.informatixinc.calnotify.utils.SecurityUtils;

public class UserDao {

	public Session register(User user, Session session) {

		Connection conn = DatabasePool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		int index = 1;

		byte[] salt = SecurityUtils.getNewSalt();

		try {
			ps = conn.prepareStatement("select id from public.user where email = ?");
			ps.setString(1, user.getEmail().toLowerCase());
			rs = ps.executeQuery();

			if (rs.next()) {
				session.getErrorResponse().setError(true);
				session.getErrorResponse().setErrorMessage("Email address already registered");
				return session;
			}

			DatabaseUtils.safeClose(ps, rs);

			ps = conn.prepareStatement(
					"insert into public.user (email, password, salt, last_login, first_name, last_name, "
							+ "phone_number, signup_date, account_type)" + "values (?,?,?,now(),?,?,?,now(),?)",
					Statement.RETURN_GENERATED_KEYS);

			ps.setString(index++, user.getEmail().toLowerCase());
			ps.setBytes(index++, SecurityUtils.hashPassword(user.getPassword().getBytes(), salt));
			ps.setBytes(index++, salt);
			ps.setString(index++, user.getFirstName());
			ps.setString(index++, user.getLastName());
			ps.setString(index++, user.getPhoneNumber().replaceAll("[^\\d]", ""));
			// Defaulting this to a user account type for now
			ps.setInt(index++, AccountType.USER.getAccountType());

			if (ps.executeUpdate() == 1) {
				session.setSession(AuthMap.addLogin(user.getEmail().toLowerCase(), AccountType.USER.getAccountType()));
				rs = ps.getGeneratedKeys();
				rs.next();
				int userId = rs.getInt(1);

				DatabaseUtils.safeClose(ps, rs);

				ps = conn.prepareStatement("insert into public.user_login (user_id, date) values (?,now())");
				ps.setInt(1, userId);
				ps.executeUpdate();

				DatabaseUtils.safeClose(ps, rs);

				ps = conn.prepareStatement(
						"insert into public.user_address (user_id, address_one, address_two, city, state_id, zip_code) values (?,?,?,?,?,?)",
						Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, userId);
				ps.setString(2, user.getAddresses().get(0).getAddressOne());
				ps.setString(3, user.getAddresses().get(0).getAddressTwo());
				ps.setString(4, user.getAddresses().get(0).getCity());
				ps.setInt(5, UsState.getStateId(user.getAddresses().get(0).getState()));
				ps.setString(6, user.getAddresses().get(0).getZipCode());
				ps.executeUpdate();

				DatabaseUtils.safeClose(ps, rs);

				ps = conn.prepareStatement("select id from public.user_address where user_id = ?");
				ps.setInt(1, userId);
				rs = ps.executeQuery();
				rs.next();

				int addressId = rs.getInt("id");

				DatabaseUtils.safeClose(ps, rs);

				ps = conn.prepareStatement(
						"insert into public.user_location (user_id, location, address_id, nick_name) values (?,point(?,?),?,?)");
				ps.setInt(1, userId);
				ps.setDouble(2, user.getLocation().getLongitude());
				ps.setDouble(3, user.getLocation().getLatitude());
				ps.setInt(4, addressId);
				ps.setString(5, "Primary Location");
				ps.executeUpdate();

				DatabaseUtils.safeClose(ps, rs);

				ps = conn.prepareStatement("select id from public.user_location where address_id = ?");
				ps.setInt(1, addressId);
				rs = ps.executeQuery();
				rs.next();

				int locationId = rs.getInt("id");

				DatabaseUtils.safeClose(ps, rs);

				ps = conn.prepareStatement(
						"insert into public.notification_settings (user_location_id, sms,email,push_notification) values (?, true, true, false)");
				ps.setInt(1, locationId);
				ps.executeUpdate();

			} else {
				session.getErrorResponse().setError(true);
				session.getErrorResponse().setErrorMessage("Unknown error");
			}

		} catch (SQLException e) {
			throw new RuntimeException("SQL error statement is " + ps.toString(), e);
		} finally {
			DatabaseUtils.safeClose(conn, ps, rs);
		}
		sendConfirmationEmail(user);
		return session;
	}

	public Session login(User login, Session session) {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		byte[] salt;

		try {
			conn = DatabasePool.getConnection();
			ps = conn.prepareStatement("select salt from public.user where email = ?");
			ps.setString(1, login.getEmail().toLowerCase());
			rs = ps.executeQuery();

			if (rs.next()) {
				salt = rs.getBytes("salt");
			} else {
				session.getErrorResponse().setError(true);
				session.getErrorResponse().setErrorMessage("Invalid email or password");
				return session;
			}

			DatabaseUtils.safeClose(ps, rs);

			ps = conn.prepareStatement("select id, account_type from public.user where email = ? and password = ?");
			ps.setString(1, login.getEmail().toLowerCase());
			ps.setBytes(2, SecurityUtils.hashPassword(login.getPassword().getBytes(), salt));
			rs = ps.executeQuery();

			if (rs.next()) {
				session.setSession(AuthMap.addLogin(login.getEmail().toLowerCase(), rs.getInt("account_type")));
				session.setAccountType(rs.getInt("account_type"));
				int userId = rs.getInt("id");
				DatabaseUtils.safeClose(ps, rs);
				ps = conn.prepareStatement("insert into public.user_login (user_id, date) values (?,now())");
				ps.setInt(1, userId);
				ps.executeUpdate();
				return session;
			} else {
				session.getErrorResponse().setError(true);
				session.getErrorResponse().setErrorMessage("Invalid email or password");
				return session;
			}

		} catch (SQLException e) {
			throw new RuntimeException("SQL error statement is " + ps.toString(), e);
		} finally {
			DatabaseUtils.safeClose(conn, ps, rs);
		}

	}

	public PutResponse updateAccount(User user, PutResponse putResponse, String sessionId) {

		Connection conn = DatabasePool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		String email = AuthMap.getUserName(sessionId);
		if (email == null) {
			putResponse.getErrorResponse().setError(true);
			putResponse.getErrorResponse().setErrorMessage("Not logged in");
			return putResponse;
		}
		int addressId;
		int userId;

		try {
			// First address is their registration address
			ps = conn.prepareStatement(
					"select public.user.id as user_id, user_address.id as address_id from public.user, public.user_address where public.user.id = user_address.user_id and public.user.email = ? order by user_address.id limit 1");
			ps.setString(1, email);
			rs = ps.executeQuery();
			// ps.close();
			if (rs.next()) {
				userId = rs.getInt("user_id");
				addressId = rs.getInt("address_id");
				DatabaseUtils.safeClose(ps, rs);
				StringBuilder sb = new StringBuilder();
				sb.append("update public.user set email = ?,");
				if (user.getPassword().length() > 0) {
					sb.append("password = ?,salt = ?,");
				}
				sb.append("first_name = ?, last_name = ?, phone_number = ?");
				sb.append("where public.user.id = ?");

				int index = 1;
				ps = conn.prepareStatement(sb.toString());
				ps.setString(index++, user.getEmail().toLowerCase());
				if (user.getPassword().length() > 0) {
					byte[] salt = SecurityUtils.getNewSalt();
					ps.setBytes(index++, SecurityUtils.hashPassword(user.getPassword().getBytes(), salt));
					ps.setBytes(index++, salt);
				}
				ps.setString(index++, user.getFirstName());
				ps.setString(index++, user.getLastName());
				ps.setString(index++, user.getPhoneNumber());
				ps.setInt(index++, userId);

				if (ps.executeUpdate() == 1) {
					DatabaseUtils.safeClose(ps, rs);
					ps = conn.prepareStatement(
							"update public.user_address set address_one = ?, address_two = ?, city = ?, state_id = ?, zip_code = ? where user_address.id = ?");
					ps.setString(1, user.getAddresses().get(0).getAddressOne());
					ps.setString(2, user.getAddresses().get(0).getAddressTwo());
					ps.setString(3, user.getAddresses().get(0).getCity());
					ps.setInt(4, UsState.getStateId(user.getAddresses().get(0).getState()));
					ps.setString(5, user.getAddresses().get(0).getZipCode());
					ps.setInt(6, addressId);

					if (ps.executeUpdate() == 1) {
						return putResponse;
					} else {
						throw new RuntimeException("Error updating user address");
					}

				} else {
					throw new RuntimeException("Error updating user account");
				}

			} else {
				putResponse.getErrorResponse().setError(true);
				putResponse.getErrorResponse().setErrorMessage("Unable to locate primary address");
				return putResponse;
			}

		} catch (SQLException e) {
			throw new RuntimeException("SQL error statement is " + ps.toString(), e);
		} finally {
			DatabaseUtils.safeClose(conn, ps);
		}
	}

	public List<UserNotification> findUsersInProximityofEvent(Point point) {
		final List<UserNotification> userNotifications = new ArrayList<UserNotification>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		final int MAXDISTANCEINMILES = ProjectProperties.getProperty("app_maxDistanceInMiles", 50);
		final StringBuilder sql = new StringBuilder();
		sql.append(
				" select u.id, u.first_name, u.last_name, u.email, u.phone_number, ns.sms, ns.email, ns.push_notification, st.sns_token ");
		sql.append(" from public.user u ");
		sql.append(" inner join public.user_location ul on ul.user_id = u.id ");
		sql.append("	and (ul.location <@> POINT(?, ?)) < ? ");
		sql.append(" inner join public.notification_settings ns on ns.user_location_id = ul.id ");
		sql.append(" left outer join public.sns_token st on st.user_id = u.id ");
		try {
			conn = DatabasePool.getConnection();
			ps = conn.prepareStatement(sql.toString());
			ps.setDouble(1, point.getLongitude());
			ps.setDouble(2, point.getLatitude());
			ps.setInt(3, MAXDISTANCEINMILES);
			rs = ps.executeQuery();
			while (rs.next()) {
				final int userId = rs.getInt(1);
				final String firstName = rs.getString(2);
				final String lastName = rs.getString(3);
				final String email = rs.getString(4);
				final String phoneNumber = rs.getString(5);
				final boolean sendSms = rs.getBoolean(6);
				final boolean sendEmail = rs.getBoolean(7);
				final boolean sendPush = rs.getBoolean(8);
				final String snsToken = rs.getString(9);
				final UserNotification userNotification = new UserNotification();
				userNotification.setUserId(userId);
				userNotification.setFirstName(firstName);
				userNotification.setLastName(lastName);
				userNotification.setEmail(email);
				userNotification.setPhoneNumber(phoneNumber);
				userNotification.setSendSms(sendSms);
				userNotification.setSendEmail(sendEmail);
				userNotification.setSendPush(sendPush);
				userNotification.setSnsToken(snsToken);
				userNotifications.add(userNotification);
			}
		} catch (SQLException e) {
			throw new RuntimeException("sql error", e);
		} finally {
			DatabaseUtils.safeClose(conn, ps, rs);
		}
		return userNotifications;
	}

	public User getAccountInformation(String email) {
		User user = new User();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		conn = DatabasePool.getConnection();

		try {
			ps = conn.prepareStatement(
					"select first_name, last_name, address_one, address_two, city, state_id, zip_code, email, phone_number"
					+ " from public.user, public.user_address "
					+ "where public.user.id = user_address.user_id and public.user.email = ? order by user_address.id asc limit 1");
			ps.setString(1, email);

			rs = ps.executeQuery();

			if (rs.next()) {
				user.setAddresses(new ArrayList<Address>());
				user.getAddresses().add(new Address());
				user.setFirstName(rs.getString("first_name"));
				user.setLastName(rs.getString("last_name"));
				user.getAddresses().get(0).setAddressOne(rs.getString("address_one"));
				user.getAddresses().get(0).setAddressTwo(rs.getString("address_two"));
				user.getAddresses().get(0).setCity(rs.getString("city"));
				user.getAddresses().get(0).setState(UsState.getStateAbbreviation(rs.getInt("state_id")));
				user.getAddresses().get(0).setZipCode(rs.getString("zip_code"));
				user.setEmail(rs.getString("email"));
				user.setPhoneNumber(rs.getString("phone_number"));

			} else {
				throw new RuntimeException("User not found");
			}
		} catch (SQLException e) {
			throw new RuntimeException("sql error", e);
		} finally {
			DatabaseUtils.safeClose(conn, ps, rs);
		}

		return user;
	}

	public PutResponse addlocations(User user, PutResponse putResponse, String sessionId) {

		Connection conn = DatabasePool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		String email = AuthMap.getUserName(sessionId);
		if (email == null) {
			putResponse.getErrorResponse().setError(true);
			putResponse.getErrorResponse().setErrorMessage("Not logged in");
			return putResponse;
		}
		int userId;
		try {
			// Select user_id from user table
			ps = conn.prepareStatement("select public.user.id as user_id from public.user where public.user.email = ?");
			ps.setString(1, email);
			rs = ps.executeQuery();

			if (rs.next()) {
				userId = rs.getInt("user_id");

				DatabaseUtils.safeClose(ps, rs);
				ps = conn.prepareStatement(
						"insert into public.user_address (user_id, address_one, address_two, city, state_id, zip_code) values (?,?,?,?,?,?)",
						Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, userId);
				ps.setString(2, user.getAddresses().get(0).getAddressOne());
				ps.setString(3, user.getAddresses().get(0).getAddressTwo());
				ps.setString(4, user.getAddresses().get(0).getCity());
				ps.setInt(5, UsState.getStateId(user.getAddresses().get(0).getState()));
				ps.setString(6, user.getAddresses().get(0).getZipCode());
				ps.executeUpdate();

				rs = ps.getGeneratedKeys();
				rs.next();
				int addressId = rs.getInt(1);

				DatabaseUtils.safeClose(ps, rs);
				ps = conn.prepareStatement(
						"insert into public.user_location (user_id, location, address_id, nick_name) values (?,point(?,?),?,?)",
						Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, userId);
				ps.setDouble(2, user.getLocation().getLongitude());
				ps.setDouble(3, user.getLocation().getLatitude());
				ps.setInt(4, addressId);
				ps.setString(5, user.getAddresses().get(0).getNickName());
				ps.executeUpdate();

				rs = ps.getGeneratedKeys();
				rs.next();
				int locationId = rs.getInt(3);

				DatabaseUtils.safeClose(ps, rs);
				ps = conn.prepareStatement(
						"insert into public.notification_settings (user_location_id, sms, email, push_notification) values (?,?,?,?)");
				ps.setInt(1, locationId);
				ps.setBoolean(2, true);
				ps.setBoolean(3, true);
				ps.setBoolean(4, false);

				if (ps.executeUpdate() == 1) {
					return putResponse;
				} else {
					throw new RuntimeException("Error adding additional location");
				}

			} else {
				putResponse.getErrorResponse().setError(true);
				putResponse.getErrorResponse().setErrorMessage("Unable to locate address");
				return putResponse;
			}

		} catch (SQLException e) {
			throw new RuntimeException("SQL error statement is " + ps.toString(), e);
		} finally {
			DatabaseUtils.safeClose(conn, ps);
		}
	}

	public int lookUpUserIdByEmail(String email) {
		Connection conn = DatabasePool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		final StringBuilder sql = new StringBuilder(" select id from public.user where email = ? ");
		try {
			conn = DatabasePool.getConnection();
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, email);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			throw new RuntimeException("SQL error statement is " + ps.toString(), e);
		} finally {
			DatabaseUtils.safeClose(conn, ps, rs);
		}
		return -1;
	}
	
	public List<UserSettings> fetchSettingForAllUsers() {
		final List<UserSettings> allSettings = new ArrayList<>();
		Connection conn = DatabasePool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		final StringBuilder sql = new StringBuilder();
		sql.append(
				" select distinct u.id, u.email, u.phone_number, ns.sms, ns.email, ns.push_notification, st.sns_token ");
		sql.append(" from public.user u ");
		sql.append(" inner join public.user_location ul on ul.user_id = u.id ");
		sql.append(" inner join public.notification_settings ns on ns.user_location_id = ul.id ");
		sql.append(" inner join public.account_type at on at.id = u.account_type ");
		sql.append(" 	and at.id = ? ");
		sql.append(" left outer join public.sns_token st on st.user_id = u.id ");
		try {
			conn = DatabasePool.getConnection();
			ps = conn.prepareStatement(sql.toString());
			ps.setInt(1, 2);
			rs = ps.executeQuery();
			while (rs.next()) {
				final int userId = rs.getInt(1);
				final String email = rs.getString(2);
				final String phoneNumber = rs.getString(3);
				final boolean sendSms = rs.getBoolean(4);
				final boolean sendEmail = rs.getBoolean(5);
				final boolean sendSns = rs.getBoolean(6);
				final String token = rs.getString(7);
				final UserSettings us = new UserSettings();
				us.setUserId(userId);
				us.setEmail(email);
				us.setPhoneNumber(phoneNumber);
				us.setSendSms(sendSms);
				us.setSendEmail(sendEmail);
				us.setSendSns(sendSns);
				us.setToken(token);
				allSettings.add(us);
			}
		} catch (SQLException e) {
			throw new RuntimeException("SQL error statement is " + ps.toString(), e);
		} finally {
			DatabaseUtils.safeClose(conn, ps, rs);
		}
		return allSettings;
	}

	public PutResponse saveSnsToken(final String token, final String session) {
		final PutResponse putResponse = new PutResponse();
		SnsToken newToken = getUserSnsToken(token, session);
		SnsToken oldToken = fetchSnsToken(newToken.getUserId());
		if (oldToken == null) {
			insertSnsToken(newToken);
		} else if (oldToken.getToken() != newToken.getToken()) {
			this.updateSnsToken(newToken);
		}
		return putResponse;
	}

	private SnsToken getUserSnsToken(final String token, final String session) {
		final String email = AuthMap.getUserName(session);
		final int userId = this.lookUpUserIdByEmail(email);
		SnsToken snsToken = new SnsToken();
		snsToken.setUserId(userId);
		snsToken.setToken(token);
		return snsToken;
	}

	private void insertSnsToken(final SnsToken snsToken) {
		Connection conn = null;
		PreparedStatement ps = null;
		final StringBuilder sql = new StringBuilder();
		sql.append("insert into public.sns_token (user_id, sns_token, sns_type_id) ");
		sql.append(" values (?, ?, ?) ");
		try {
			conn = DatabasePool.getConnection();
			ps = conn.prepareStatement(sql.toString());
			ps.setInt(1, snsToken.getUserId());
			ps.setString(2, snsToken.getToken());
			ps.setInt(3, snsToken.getType().id);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("SQL error statement is " + ps.toString(), e);
		} finally {
			DatabaseUtils.safeClose(conn, ps);
		}
	}

	private void updateSnsToken(final SnsToken snsToken) {
		Connection conn = null;
		PreparedStatement ps = null;
		final StringBuilder sql = new StringBuilder();
		sql.append("update public.sns_token ");
		sql.append(" set sns_token = ?, sns_type_id = ? ");
		sql.append(" where user_id = ? ");
		try {
			conn = DatabasePool.getConnection();
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, snsToken.getToken());
			ps.setInt(2, snsToken.getType().id);
			ps.setInt(3, snsToken.getUserId());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("SQL error statement is " + ps.toString(), e);
		} finally {
			DatabaseUtils.safeClose(conn, ps);
		}
	}

	private SnsToken fetchSnsToken(final int userId) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		final StringBuilder sql = new StringBuilder();
		sql.append("select token.sns_token, type.type ");
		sql.append(" from public.sns_token token ");
		sql.append(" inner join sns_type type on type.id = token.sns_type_id ");
		sql.append(" where token.user_id = ? ");
		try {
			conn = DatabasePool.getConnection();
			ps = conn.prepareStatement(sql.toString());
			ps.setInt(1, userId);
			rs = ps.executeQuery();
			if (rs.next()) {
				final String token = rs.getString(1);
				final String type = rs.getString(2);
				final SnsToken snsToken = new SnsToken();
				snsToken.setUserId(userId);
				snsToken.setToken(token);
				snsToken.setType(SnsType.valueOf(type));
				return snsToken;
			}
		} catch (SQLException e) {
			throw new RuntimeException("SQL error statement is " + ps.toString(), e);
		} finally {
			DatabaseUtils.safeClose(conn, ps, rs);
		}
		return null;
	}
	
	public void sendConfirmationEmail(final User user) {
		final String subject = ProjectProperties.getProperty("app_regConfirmSubject");
		final String body = MessageFormat.format(ProjectProperties.getProperty("app_regConfirmBody"),
				user.getFirstName(), user.getLastName());
		EmailClient emailClient = new EmailClient();
		emailClient.send(user.getEmail(), subject, body);
	}

	public static void main(String...args) {
		try {
			ProjectProperties.init();
			User user = new User();
			user.setFirstName("Paul");
			user.setLastName("Ortiz");
			user.setEmail("paul.ortiz@informatixinc.com");
			UserDao userDao = new UserDao();
			userDao.sendConfirmationEmail(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println();
	}
}