package com.informatixinc.calnotify.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.informatixinc.calnotify.model.Notification;
import com.informatixinc.calnotify.model.NotificationClassification;
import com.informatixinc.calnotify.model.NotificationSettings;
import com.informatixinc.calnotify.model.NotificationType;
import com.informatixinc.calnotify.model.Point;
import com.informatixinc.calnotify.model.PutResponse;
import com.informatixinc.calnotify.utils.DatabaseUtils;
import com.informatixinc.calnotify.utils.StringUtils;

public class NotificationDao {
	private final String baseUrl = "https://igems.doi.gov/arcgis/rest/services/igems_haz/MapServer/find?searchText=x%3DCA&contains=true&searchFields=link&sr=4326&layerDefs=&returnGeometry=true&maxAllowableOffset=&geometryPrecision=2&dynamicLayers=&returnZ=false&returnM=false&gdbVersion=&f=pjson&layers=";
	private final DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");

	public ArrayList<Notification> getActiveNotifications() {

		ArrayList<Notification> notifications = new ArrayList<Notification>();

		Connection conn = DatabasePool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(
					"select notification.id as notification_id, type, issue_time, expire_time, icon from public.notification, public.notification_type "
							+ "where notification.notification_type = notification_type.id and expire_time > now()");

			rs = ps.executeQuery();

			while (rs.next()) {
				Notification notification = new Notification();
				notification.setTitle(rs.getString("type"));
				notification.setSendTime(new Date(rs.getTimestamp("issue_time").getTime()));
				notification.setExpireTime(new Date(rs.getTimestamp("expire_time").getTime()));
				notification.setImageUrl(rs.getString("icon"));
				notification.setNotificationId(rs.getString("notification_id"));

				notifications.add(notification);
			}

		} catch (SQLException e) {
			throw new RuntimeException("SQL error statement is " + ps.toString(), e);
		} finally {
			DatabaseUtils.safeClose(conn, ps, rs);
		}

		return notifications;
	}

	public Notification getNotificationById(int id) {
		Connection conn = DatabasePool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		Notification notification = new Notification();

		try {
			ps = conn.prepareStatement(
					"select notification.id as notification_id, type, issue_time, expire_time, icon from public.notification, public.notification_type "
							+ "where notification.notification_type = notification_type.id and notification.id = ?");
			ps.setInt(1, id);

			rs = ps.executeQuery();

			if (rs.next()) {
				notification.setTitle(rs.getString("type"));
				notification.setSendTime(new Date(rs.getTimestamp("issue_time").getTime()));
				notification.setExpireTime(new Date(rs.getTimestamp("expire_time").getTime()));
				notification.setImageUrl(rs.getString("icon"));
				notification.setNotificationId(rs.getString("notification_id"));
			} else {
				notification.getErrorResponse().setError(true);
				notification.getErrorResponse().setErrorMessage("Unable to locate notification");
			}

		} catch (SQLException e) {
			throw new RuntimeException("SQL error statement is " + ps.toString(), e);
		} finally {
			DatabaseUtils.safeClose(conn, ps, rs);
		}

		return notification;
	}

	public List<Notification> fetchSourceNotifications(final NotificationType type) {
		final List<Notification> notifications = new ArrayList<Notification>();
		final String json = doFetchJson(type);
		final JsonParser parser = new JsonParser();
		final JsonObject obj = (JsonObject) parser.parse(json);
		final JsonArray results = obj.getAsJsonArray("results");
		for (final JsonElement e : results) {
			notifications.add(parseNotification(e.getAsJsonObject(), type));
		}
		return notifications;
	}

	public int addNew(Notification notification) {
		if (!isDuplicate(notification)) {
			return doInsert(notification);
		} else {
			return -1;
		}
	}

	public PutResponse setNotificationSettings(NotificationSettings settings) {
		PutResponse putResponse = new PutResponse();
		
		Connection conn = DatabasePool.getConnection();
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement("insert into public.notification_settings (user_location_id, sms, email, push_notification) "
					+ "values (?, ?, ?, ?) "
					+ "on conflict (user_location_id) "
					+ "do update set sms = ?, email = ?, push_notification = ?");
			ps.setInt(1, settings.getUserLocationId());
			ps.setBoolean(2, settings.isSms());
			ps.setBoolean(3, settings.isEmail());
			ps.setBoolean(4, settings.isSns());
			ps.setBoolean(5, settings.isSms());
			ps.setBoolean(6, settings.isEmail());
			ps.setBoolean(7, settings.isSns());
			
			if(ps.executeUpdate() == 1){
				return putResponse;
			}else{
				putResponse.getErrorResponse().setError(true);
				putResponse.getErrorResponse().setErrorMessage("Error updating notification settings");
			}
			
		} catch (SQLException e) {
			throw new RuntimeException("SQL error statement is " + ps.toString(), e);
		} finally {
			DatabaseUtils.safeClose(conn, ps);
		}

		return putResponse;
	}

	private String doFetchJson(final NotificationType type) {
		final Client client = ClientBuilder.newClient();
		final StringBuilder sb = new StringBuilder(baseUrl);
		sb.append(String.valueOf(type.id));
		final WebTarget target = client.target(sb.toString());
		final Invocation.Builder invocationBuilder = target.request(MediaType.TEXT_PLAIN_TYPE);
		final Response response = invocationBuilder.get();
		final String json = response.readEntity(String.class);
		return json;
	}

	private Notification parseNotification(final JsonObject result, final NotificationType type) {
		final JsonObject attributes = result.getAsJsonObject("attributes");
		final String notificationId = attributes.get("id").getAsString();
		final String title = attributes.get("event").getAsString();
		final String expiresStr = attributes.get("expires").getAsString();
		Date expireTime;
		try {
			expireTime = df.parse(expiresStr);
		} catch (ParseException e) {
			expireTime = new Date();
		}
		final String infoUrl = attributes.get("link").getAsString();
		final JsonObject geometry = result.getAsJsonObject("geometry");
		final JsonArray rings = geometry.getAsJsonArray("rings").getAsJsonArray().get(0).getAsJsonArray().get(0)
				.getAsJsonArray();
		final Point location = new Point();
		double latitude = StringUtils.parseDouble(rings.get(1).getAsString());
		double longitude = StringUtils.parseDouble(rings.get(0).getAsString());
		location.setLatitude(latitude);
		location.setLongitude(longitude);
		int classificationId = this.lookUpClassificationId(title);
		final Notification notification = new Notification(type.name(), type.id, classificationId, title, infoUrl,
				notificationId, new Date(), expireTime, location);
		return notification;
	}
	
	private int doInsert(Notification n) {
		Connection conn = DatabasePool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		final StringBuilder sql = new StringBuilder();
		sql.append(" insert into public.notification ");
		sql.append(" (type_id, classification_id, title, info_url, notification_id, send_time, expire_time, location) ");
		sql.append(" values(?, ?, ?, ?, ?, ?, ?, POINT(?, ?)) ");
		try {
			ps = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, n.getTypeId());
			ps.setInt(2, n.getClassificationId());
			ps.setString(3, n.getTitle());
			ps.setString(4, n.getInfoUrl());
			ps.setString(5, n.getNotificationId());
			final Timestamp sendTime = new Timestamp(n.getSendTime().getTime());
			final Timestamp expireTime = new Timestamp(n.getExpireTime().getTime());
			ps.setTimestamp(6, sendTime);
			ps.setTimestamp(7, expireTime);
			ps.setDouble(8, n.getLocation().getLongitude());
			ps.setDouble(9, n.getLocation().getLatitude());
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			rs.next();
			return rs.getInt(1);
		} catch (SQLException e) {
			throw new RuntimeException("SQL error statement is " + ps.toString(), e);
		} finally {
			DatabaseUtils.safeClose(conn, ps);
		}
	}

	private boolean isDuplicate(Notification n) {
		Connection conn = DatabasePool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		final StringBuilder sql = new StringBuilder();
		sql.append(" select count(*) ");
		sql.append(" from public.notification ");
		sql.append(" where type_id = ? and title = ? and notification_id = ? and expire_time = ? and info_url = ? ");
		try {
			ps = conn.prepareStatement(sql.toString());
			ps.setInt(1, n.getTypeId());
			ps.setString(2, n.getTitle());
			ps.setString(3, n.getNotificationId());
			final Timestamp ts = new Timestamp(n.getExpireTime().getTime());
			ps.setTimestamp(4, ts);
			ps.setString(5, n.getInfoUrl());
			rs = ps.executeQuery();
			rs.next();
			final int i = rs.getInt(1);
			return i > 0;
		} catch (SQLException e) {
			throw new RuntimeException("SQL error statement is " + ps.toString(), e);
		} finally {
			DatabaseUtils.safeClose(conn, ps, rs);
		}
	}
	
	private int lookUpClassificationId(final String title) {
		NotificationClassification nc = NotificationClassification.valueOf(title.replaceAll(" ", ""));
		return nc.getId();
	}
	
	public ArrayList<NotificationSettings> getNotificationSettings(String email){
		ArrayList<NotificationSettings> settings = new ArrayList<NotificationSettings>();
		
		Connection conn = DatabasePool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement("select sms, notification_settings.email, push_notification from public.user, "
					+ "public.user_location, public.notification_settings where public.user.email = ? and "
					+ "public.user.id = user_location.user_id and user_location.id = notification_settings.user_location_id");
			
			ps.setString(1, email);
			rs = ps.executeQuery();
			
			while(rs.next()){
				NotificationSettings setting = new NotificationSettings();
				setting.setEmail(rs.getBoolean("email"));
				setting.setSms(rs.getBoolean("sms"));
				setting.setSns(rs.getBoolean("sns"));
				
				settings.add(setting);
			}
		} catch (SQLException e) {
			throw new RuntimeException("SQL error statement is " + ps.toString(), e);
		} finally {
			DatabaseUtils.safeClose(conn, ps, rs);
		}
		
		return settings;
	}
}
