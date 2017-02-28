package com.informatixinc.calnotify.actions;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.informatixinc.calnotify.dao.AdminDao;
import com.informatixinc.calnotify.dao.NotificationTransmissionDao;
import com.informatixinc.calnotify.dao.UserDao;
import com.informatixinc.calnotify.model.AdminMessage;
import com.informatixinc.calnotify.model.NotificationClassification;
import com.informatixinc.calnotify.model.NotificationTransmission;
import com.informatixinc.calnotify.model.TransmissionType;
import com.informatixinc.calnotify.model.UserSettings;
import com.informatixinc.calnotify.utils.EmailClient;
import com.informatixinc.calnotify.utils.ProjectProperties;
import com.informatixinc.calnotify.utils.PushService;
import com.informatixinc.calnotify.utils.SmsClient;
import com.informatixinc.calnotify.utils.StringUtils;

public class AdminMessageAction {

	public void notify(AdminMessage message) {
		final UserDao userDao = new UserDao();
		final AdminDao adminDao = new AdminDao();
		adminDao.addMessage(message);
		final List<UserSettings> allUsersSettings = userDao.fetchSettingForAllUsers();
		for (UserSettings us : allUsersSettings) {
			if (us.isSendSms()) {
				sendSms(message, us.getPhoneNumber(), us.getUserId());
			}
			if (us.isSendEmail()) {
				sendEmail(message, us.getEmail(), us.getUserId());
			}
			if (us.isSendSns()) {
				sendPush(message, us.getToken(), us.getUserId());
			}
		}
	}

	private void sendSms(final AdminMessage message, String phoneNumber, int userId) {
		final String msgPrefix = ProjectProperties.getProperty("app_notificationSubject");
		final Date d = new Date(message.getExpirationDate());
		final String expires = df.format(d);
		final String msgBody = MessageFormat.format(ProjectProperties.getProperty("app_notificationBody"),
				message.getTitle(), expires, message.getMessage());
		final SmsClient smsClient = new SmsClient();
		smsClient.send("+1" + phoneNumber, msgPrefix + ":" + msgBody);
		addNewTransmission(message, userId, TransmissionType.SMS);
	}

	private void sendEmail(final AdminMessage message, String email, int userId) {
		final String subject = ProjectProperties.getProperty("app_notificationSubject");
		final Date d = new Date(message.getExpirationDate());
		final String expires = df.format(d);
		final String body = MessageFormat.format(ProjectProperties.getProperty("app_notificationBody"),
				message.getTitle(), expires, message.getMessage());
		final EmailClient emailClient = new EmailClient();
		emailClient.send(email, subject, body);
		addNewTransmission(message, userId, TransmissionType.EMAIL);
	}

	private void sendPush(final AdminMessage message, String token, int userId) {
		if (StringUtils.isNull(token)) {
			log.error("Push failed: push setting set to true, but token is null. UserId: " + String.valueOf(userId));
			return;
		}
		final String messageTitle = ProjectProperties.getProperty("app_notificationSubject");
		final Date d = new Date(message.getExpirationDate());
		final String expires = df.format(d);
		final String messageBody = MessageFormat.format(ProjectProperties.getProperty("app_notificationBody"),
				message.getTitle(), expires, message.getMessage());
		PushService ps = new PushService();
		ps.push(token, messageBody, messageTitle);
	}

	private void addNewTransmission(AdminMessage message, int userId, TransmissionType tt) {
		final NotificationTransmission nt = new NotificationTransmission();
		nt.setUserId(userId);
		nt.setNotificationId(NotificationClassification.AdminNotification.getId());
		nt.setSendTime(new Date());
		nt.setTransmissionTypeId(tt.id);
		NotificationTransmissionDao dao = new NotificationTransmissionDao();
		dao.addNew(nt);
	}

	private final Logger log = Logger.getLogger(this.getClass());
	private final DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy hh:mm:ss a");
}
