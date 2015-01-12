package com.epikur.nettyserver.protocol.messages;

import com.epikur.nettyserver.protocol.PacketType;

public class MessageFactory {
	public static Message getMessage(PacketType packetType) {
		Message msg;
		
		switch (packetType) {
		case CRYPT_KEY_CLIENT_TO_CLIENT:
			msg = new CryptKeyClientToClientMessage();
			break;
		case CRYPT_KEY_SERVER_TO_CLIENT:
			msg = new CryptKeyServerToClientMessage();
			break;
		case DISPLAY_FRAME:
			msg = new DisplayFrameMessage();
			break;
		case LOGIN:
			msg = new LoginMessage();
			break;
		case LOGOUT:
			msg = new LogoutMessage();
			break;
		case TEXT_MESSAGE_CRYPT:
			msg = new CryptedTextMessage();
			break;
		case TEXT_MESSAGE_OPEN:
			msg = new OpenTextMessage();
			break;
		case UNKNOWN:
		default:
			msg = null;
		}
		
		return msg;
	}
}
