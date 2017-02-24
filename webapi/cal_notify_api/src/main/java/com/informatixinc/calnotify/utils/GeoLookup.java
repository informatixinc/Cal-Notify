package com.informatixinc.calnotify.utils;

import java.io.IOException;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.net.URLCodec;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.informatixinc.calnotify.model.Address;
import com.informatixinc.calnotify.model.Point;

public class GeoLookup {
	
	
	//Null responses will be not found
	public Point latLongFromAddress(Address address){
		CloseableHttpClient httpclient = HttpClients.createDefault();
		URLCodec codec = new URLCodec();
		StringBuilder sb = new StringBuilder();
		CloseableHttpResponse response = null;
		
		try {
			sb.append("https://maps.googleapis.com/maps/api/geocode/json?address=");
			sb.append(codec.encode(address.getAddressOne()));
			sb.append(",+");
			sb.append(codec.encode(address.getCity()));
			sb.append(",+");
			sb.append(codec.encode(address.getState()));
			sb.append("&key=");
			sb.append(ProjectProperties.getProperty("google_maps_api_key"));
			
			HttpGet httpGet = new HttpGet(sb.toString());
			response = httpclient.execute(httpGet);
			HttpEntity entity =  response.getEntity();
			String jsonStringResponse = IOUtils.toString(entity.getContent(), "UTF-8");
			JsonParser jsonParser = new JsonParser();
			JsonObject jsonResponse = jsonParser.parse(jsonStringResponse).getAsJsonObject();
			if(jsonResponse.get("status").getAsString().equals("OK")){
				JsonObject firstMatch = jsonResponse.get("results").getAsJsonArray().get(0).getAsJsonObject();
				Point point = new Point();
				point.setLatitude(firstMatch.get("geometry").getAsJsonObject().get("location").getAsJsonObject().get("lat").getAsDouble());
				point.setLongitude(firstMatch.get("geometry").getAsJsonObject().get("location").getAsJsonObject().get("lng").getAsDouble());
				return point;
			}else{
				return null;
			}
			
		} catch (EncoderException e) {
			throw new RuntimeException("Error encoding url");
		} catch (ClientProtocolException e) {
			throw new RuntimeException("Error making http google maps api call");
		} catch (IOException e) {
			throw new RuntimeException("Error making http google maps api call");
		} catch(JsonSyntaxException e){
			throw new RuntimeException("Error parsing google maps api call response");
		}finally {
			try {
				response.close();
			} catch (IOException e) {
				//Just let this time out
			}
		}
	}

}
