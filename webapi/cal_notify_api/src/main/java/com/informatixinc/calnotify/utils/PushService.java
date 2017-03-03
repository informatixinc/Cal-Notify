package com.informatixinc.calnotify.utils;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import de.bytefish.fcmjava.client.FcmClient;
import de.bytefish.fcmjava.client.settings.PropertiesBasedSettings;
import de.bytefish.fcmjava.http.options.IFcmClientSettings;
import de.bytefish.fcmjava.model.options.FcmMessageOptions;
import de.bytefish.fcmjava.requests.data.DataUnicastMessage;
import de.bytefish.fcmjava.requests.notification.NotificationPayload;
import de.bytefish.fcmjava.responses.FcmMessageResponse;
import de.bytefish.fcmjava.responses.FcmMessageResultItem;

/**
 * Service to push a message using Firebase Cloud Messaging (fcm) platform
 * 
 * @author Paul Ortiz
 *
 */
public class PushService {

	/**
	 * Push a message using
	 * 
	 * @param token
	 *            - fcm token
	 * @param messageBody
	 * @param messageTitle
	 */
	public void push(final String token, final String messageBody, final String messageTitle) {
		final IFcmClientSettings settings = PropertiesBasedSettings
				.createFromProperties(ProjectProperties.getProperties());
		final FcmClient client = new FcmClient(settings);
		final FcmMessageOptions options = FcmMessageOptions.builder().setTimeToLive(Duration.ofMinutes(2)).build();
		final Map<String, Object> data = new HashMap<>();
		data.put("id", 1);
		data.put("text", Math.random() * 1000);
		NotificationPayload payload = NotificationPayload.builder().setBody(messageBody).setTitle(messageTitle)
				.setTag(MESSAGE_TAG).build();
		DataUnicastMessage message = new DataUnicastMessage(options, token, data, payload);
		FcmMessageResponse response = client.send(message);
		for (FcmMessageResultItem result : response.getResults()) {
			if (result.getErrorCode() != null) {
				logger.error(String.format("Sending to %s failed. Error Code %s\n", token, result.getErrorCode()));
			}
		}
	}

	private static final String MESSAGE_TAG = "Alert";
	private final Logger logger = Logger.getLogger(this.getClass());
}
