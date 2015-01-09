package com.epikur.nettyserver.protocol;

public enum MessageType {
	LOGIN 
	, LOGOUT
	, TEXT_MESSAGE
	, DISPLAY_FRAME
	, UNKNOWN;
	
	public static MessageType fromByte(byte bType) {
		switch (bType) {
		case 100:
			return MessageType.LOGIN;
		default:
			return MessageType.UNKNOWN;
		}
	}
	
	public static byte toByte(MessageType mType) {
		switch (mType) {
		case LOGIN:
			return 100;
		default:
			return -1;
		}
	}
}
