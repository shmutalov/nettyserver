package com.epikur.nettyserver.protocol;

public enum PacketType {
	LOGIN 
	, LOGOUT
	, TEXT_MESSAGE_OPEN
	, TEXT_MESSAGE_CRYPT
	, DISPLAY_FRAME
	, CRYPT_KEY_SERVER_TO_CLIENT
	, CRYPT_KEY_CLIENT_TO_CLIENT
	, UNKNOWN;
	
	public static PacketType fromByte(byte bType) {
		switch (bType) {
		case 010:
			return LOGIN;
		case 020:
			return LOGOUT;
		case 030:
			return TEXT_MESSAGE_OPEN;
		case 031:
			return TEXT_MESSAGE_CRYPT;
		case 040:
			return DISPLAY_FRAME;
		case 050:
			return CRYPT_KEY_SERVER_TO_CLIENT;
		case 051:
			return CRYPT_KEY_CLIENT_TO_CLIENT;
		default:
			return PacketType.UNKNOWN;
		}
	}
	
	public static byte toByte(PacketType mType) {
		switch (mType) {
		case LOGIN:
			return 010;
		case LOGOUT:
			return 020;
		case TEXT_MESSAGE_OPEN:
			return 030;
		case TEXT_MESSAGE_CRYPT:
			return 030;
		case DISPLAY_FRAME:
			return 040;
		case CRYPT_KEY_SERVER_TO_CLIENT:
			return 050;
		case CRYPT_KEY_CLIENT_TO_CLIENT:
			return 051;
		case UNKNOWN:
		default:
			return -1;
		}
	}
}
