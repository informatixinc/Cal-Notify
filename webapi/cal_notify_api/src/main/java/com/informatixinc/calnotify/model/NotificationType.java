package com.informatixinc.calnotify.model;

public enum NotificationType {
	WARNINGS(12), WATCHES(13), ADVISORIES(14);
	public final int id;

	private NotificationType(int id) {
		this.id = id;
	}
}
