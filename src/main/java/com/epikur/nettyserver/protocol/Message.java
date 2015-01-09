package com.epikur.nettyserver.protocol;

public class Message {
	private ProtocolVersion protocolVersion;
	private MessageType type;
	private byte[] payload;
	
	public Message () {
		protocolVersion = ProtocolVersion.fromByte((byte)(0));
		type = MessageType.UNKNOWN;
	}
	
	public Message(ProtocolVersion ver, MessageType type, byte [] payload) {
		this.protocolVersion = ver;
		this.type = type;
		this.payload = payload;
	}

	public ProtocolVersion getProtocolVersion() {
		return protocolVersion;
	}

	public void setProtocolVersion(ProtocolVersion version) {
		this.protocolVersion = version;
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
