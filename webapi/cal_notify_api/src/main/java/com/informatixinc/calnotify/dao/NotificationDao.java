package com.informatixinc.calnotify.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
			throw new RuntimeException("SQL error statement is " + ps.toString());
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
			throw new RuntimeException("SQL error statement is " + ps.toString());
		} finally {
			DatabaseUtils.safeClose(conn, ps, rs);
		}

		return notification;
	}

	public List<Notification> fetchSourceNotifications(final NotificationType type) throws Exception {
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

	public PutResponse setNotificationSettings(NotificationSettings settings) {
		PutResponse putResponse = new PutResponse();

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
		final Notification notification = new Notification(type.name(), type.id, title, infoUrl, notificationId, new Date(),
				expireTime, location);
		return notification;
	}

}
