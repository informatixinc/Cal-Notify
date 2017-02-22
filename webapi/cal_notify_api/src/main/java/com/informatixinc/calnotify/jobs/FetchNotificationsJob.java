package com.informatixinc.calnotify.jobs;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.informatixinc.calnotify.dao.DatabasePool;
import com.informatixinc.calnotify.dao.NotificationDao;
import com.informatixinc.calnotify.dao.NotificationTransmissionDao;
import com.informatixinc.calnotify.dao.UserDao;
import com.informatixinc.calnotify.model.Notification;
import com.informatixinc.calnotify.model.NotificationTransmission;
import com.informatixinc.calnotify.model.NotificationType;
import com.informatixinc.calnotify.model.Point;
import com.informatixinc.calnotify.model.TransmissionType;
import com.informatixinc.calnotify.model.UserNotification;
import com.informatixinc.calnotify.utils.EmailClient;
import com.informatixinc.calnotify.utils.ProjectProperties;
import com.informatixinc.calnotify.utils.SmsClient;

public class FetchNotificationsJob implements Runnable {

	private final DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");

	@Override
	public void run() {
		try {
			List<Notification> notifications = fetchNotifications();
			for (Notification notification : notifications) {
				int id = addNew(notification);
				if (id == -1) {
					continue;
				}
				notification.setId(id);
				final List<UserNotification> userNotifications = findUsersInProximityOfEvent(
						notification.getLocation());
				for (UserNotification userNotification : userNotifications) {
					userNotification.setNotificationId(notification.getId());
					userNotification.setNotificationType(notification.getType());
					userNotification.setNotificationTitle(notification.getTitle());
					userNotification.setInfoUrl(notification.getInfoUrl());
					userNotification.setExpires(notification.getExpireTime());
					userNotification.setSendTime(notification.getSendTime());
					if (userNotification.isSendSms()) {
						sendSms(userNotification);
					}
					if (userNotification.isSendEmail()) {
						sendEmail(userNotification);
					}
				}
			}
		} catch (RuntimeException e) {
			log.error("A runtime exeception occurred", e);
		}
	}

	private List<Notification> fetchNotifications() {
		final List<Notification> notifications = new ArrayList<Notification>();
		final NotificationDao dao = new NotificationDao();
		notifications.addAll(dao.fetchSourceNotifications(NotificationType.ADVISORIES));
		notifications.addAll(dao.fetchSourceNotifications(NotificationType.WARNINGS));
		notifications.addAll(dao.fetchSourceNotifications(NotificationType.WATCHES));
		return notifications;
	}

	private int addNew(final Notification notification) {
		final NotificationDao dao = new NotificationDao();
		return dao.addNew(notification);
	}

	private List<UserNotification> findUsersInProximityOfEvent(final Point point) {
		final UserDao userDao = new UserDao();
		return userDao.findUsersInProximityofEvent(point);
	}

	private void sendSms(final UserNotification userNotification) {
		final String msgPrefix = ProjectProperties.getProperty("app_notificationSubject");
		final String expires = df.format(userNotification.getExpires());
		final String msgBody = MessageFormat.format(ProjectProperties.getProperty("app_notificationBody"),
				userNotification.getNotificationTitle(), expires, userNotification.getInfoUrl());
		final SmsClient smsClient = new SmsClient();
		smsClient.send("+1" + userNotification.getPhoneNumber(), msgPrefix + ":" + msgBody);
		addNewTransmission(userNotification, TransmissionType.SMS);
	}

	private void sendEmail(final UserNotification userNotification) {
		final String subject = ProjectProperties.getProperty("app_notificationSubject");
		final String expires = df.format(userNotification.getExpires());
		final String body = MessageFormat.format(ProjectProperties.getProperty("app_notificationBody"),
				userNotification.getNotificationTitle(), expires, userNotification.getInfoUrl());
		final EmailClient emailClient = new EmailClient();
		emailClient.send(userNotification.getEmail(), subject, body);
		addNewTransmission(userNotification, TransmissionType.EMAIL);
	}

	private void addNewTransmission(UserNotification un, TransmissionType tt) {
		final NotificationTransmission nt = new NotificationTransmission();
		nt.setUserId(un.getUserId());
		nt.setNotificationId(un.getNotificationId());
		nt.setSendTime(un.getSendTime());
		nt.setTransmissionTypeId(tt.id);
		NotificationTransmissionDao dao = new NotificationTransmissionDao();
		dao.addNew(nt);
	}

	public static void main(String... args) {
		try {
			ProjectProperties.init();
			DatabasePool.init();
			FetchNotificationsJob job = new FetchNotificationsJob();
			job.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.exit(0);
	}

	public final Logger log = Logger.getLogger(this.getClass());
}
