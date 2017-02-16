package com.informatixinc.calnotify.dao;

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
import com.informatixinc.calnotify.model.Event;

public class EventDao {

	public enum EventType {
		WARNINGS("12"), WATCHES("13"), ADVISORIES("14");
		public final String id;

		private EventType(String id) {
			this.id = id;
		}
	}

	private final String baseUrl = "https://igems.doi.gov/arcgis/rest/services/igems_haz/MapServer/find?searchText=x%3DCA&contains=true&searchFields=link&sr=4326&layerDefs=&returnGeometry=true&maxAllowableOffset=&geometryPrecision=2&dynamicLayers=&returnZ=false&returnM=false&gdbVersion=&f=pjson&layers=";
	private final DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");

	public List<Event> fetchSourceEvents(final EventType type) throws Exception {
		final List<Event> events = new ArrayList<Event>();
		final String json = doFetchJson(type);
		final JsonParser parser = new JsonParser();
		final JsonObject obj = (JsonObject) parser.parse(json);
		final JsonArray results = obj.getAsJsonArray("results");
		for (final JsonElement e : results) {
			events.add(parseEvent(e.getAsJsonObject(), type.name()));
		}
		return events;
	}

	private String doFetchJson(final EventType type) {
		final Client client = ClientBuilder.newClient();
		final StringBuilder sb = new StringBuilder(baseUrl);
		sb.append(type.id);
		final WebTarget target = client.target(sb.toString());
		final Invocation.Builder invocationBuilder = target.request(MediaType.TEXT_PLAIN_TYPE);
		final Response response = invocationBuilder.get();
		final String json = response.readEntity(String.class);
		return json;
	}

	private Event parseEvent(final JsonObject result, final String type) {
		final JsonObject attributes = result.getAsJsonObject("attributes");
		final String eventId = attributes.get("id").getAsString();
		final String description = attributes.get("event").getAsString();
		final String source = attributes.get("expires").getAsString();
		Date expires;
		try {
			expires = df.parse(source);
		} catch (ParseException e) {
			expires = new Date();
		}
		final String infoLink = attributes.get("link").getAsString();
		final JsonObject geometry = result.getAsJsonObject("geometry");
		final JsonArray rings = geometry.getAsJsonArray("rings").getAsJsonArray().get(0).getAsJsonArray().get(0)
				.getAsJsonArray();
		final StringBuilder gpsCoordinates = new StringBuilder();
		gpsCoordinates.append(rings.get(1).getAsString());
		gpsCoordinates.append(",");
		gpsCoordinates.append(rings.get(0).getAsString());
		final Event event = new Event(type, eventId, infoLink, expires, gpsCoordinates.toString(), description);
		return event;
	}

	public static void main(String... args) {
		try {
			EventDao eventDao = new EventDao();
			List<Event> events = eventDao.fetchSourceEvents(EventType.ADVISORIES);
			System.out.println("done");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.exit(0);
	}
}
