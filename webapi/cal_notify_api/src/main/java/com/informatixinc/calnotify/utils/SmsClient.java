package com.informatixinc.calnotify.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;

public class SmsClient {

	public void send(final String phoneNumber, final String message) {
		//AWSCredentials credentials = new BasicAWSCredentials("xxx", "xxx");
		final AmazonSNSClient client = new AmazonSNSClient();
		final Map<String, MessageAttributeValue> smsAttributes =
		        new HashMap<String, MessageAttributeValue>();
		smsAttributes.put("AWS.SNS.SMS.SenderID", new MessageAttributeValue()
		        .withStringValue("CalNotify") 
		        .withDataType("String"));
		smsAttributes.put("AWS.SNS.SMS.MaxPrice", new MessageAttributeValue()
		        .withStringValue("0.50") 
		        .withDataType("Number"));
		smsAttributes.put("AWS.SNS.SMS.SMSType", new MessageAttributeValue()
		        .withStringValue("Promotional") 
		        .withDataType("String"));
		PublishResult result = client.publish(new PublishRequest()
	                        .withMessage(message)
	                        .withPhoneNumber(phoneNumber)
	                        .withMessageAttributes(smsAttributes));
	    log.debug(result);
	}
	
	private final Logger log = Logger.getLogger(this.getClass());
}
