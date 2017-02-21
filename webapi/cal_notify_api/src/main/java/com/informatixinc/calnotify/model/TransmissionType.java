package com.informatixinc.calnotify.model;

public enum TransmissionType {
	SMS(1), EMAIL(2), SNS(3);
	public final int id;

	private TransmissionType(int id) {
		this.id = id;
	}

}
