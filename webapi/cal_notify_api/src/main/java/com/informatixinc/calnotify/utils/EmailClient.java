package com.informatixinc.calnotify.utils;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * A simple email client
 * 
 * @author Paul Ortiz
 *
 */
public class EmailClient {

	/**
	 * Send an email
	 * 
	 * @param toEmail
	 * @param subject
	 * @param body
	 */
	public void send(String toEmail, String subject, String body) {
		final String fromEmail = "calnotify007@gmail.com";
		final String password = "Inf0rm@tix";
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		Authenticator auth = new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromEmail, password);
			}
		};
		Session session = Session.getInstance(props, auth);
		doSend(session, toEmail, subject, body);

	}

	private void doSend(Session session, String toEmail, String subject, String body) {
		try {
			MimeMessage msg = new MimeMessage(session);
			msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
			msg.addHeader("format", "flowed");
			msg.addHeader("Content-Transfer-Encoding", "8bit");
			msg.setFrom(new InternetAddress("no_reply@calnotify.org", "NoReply-CALNOTIFY"));
			msg.setReplyTo(InternetAddress.parse("no_reply@calnotify.org", false));
			msg.setSubject(subject, "UTF-8");
			msg.setText(body, "UTF-8");
			msg.setSentDate(new Date());
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
			Transport.send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
