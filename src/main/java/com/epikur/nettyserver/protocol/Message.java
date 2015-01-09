package com.epikur.nettyserver.protocol;

public class Message {
	private ProtocolVersion version;
	private MessageType type;
	private byte[] payload;
	
	public Message () {
		version = ProtocolVersion.fromByte((byte)(0));
		type = MessageType.UNKNOWN;
	}
	
	public Message(ProtocolVersion ver, MessageType type, byte [] payload) {
		this.version = ver;
		this.type = type;
		this.payload = payload;
	}

	public ProtocolVersion getVersion() {
		return version;
	}

	public void setVersion(ProtocolVersion version) {
		this.version = version;
	}

	public MessageType getType() {
		return type;
	}

	public void setType(MessageType type) {
		this.type = type;
	}

	public byte[] getPayload() {
		return payload;
	}

	public void setPayload(byte[] payload) {
		this.payload = payload;
	}
}
