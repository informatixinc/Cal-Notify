package com.informatixinc.calnotify.model;

public enum SnsType {
	FCM(1);

	public final int id;

	// sns type id
	private SnsType(int id) {
		this.id = id;
	}
}
