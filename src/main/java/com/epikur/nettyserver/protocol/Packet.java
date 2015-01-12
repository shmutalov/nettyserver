package com.epikur.nettyserver.protocol;

public class Packet {
	public final static int MAX_PAYLOAD_LENGTH = 256000;
	
	private ProtocolVersion protocolVersion;
	private PacketType type;
	private byte[] payload;
	
	private int channelId;
	
	public Packet () {
		protocolVersion = new ProtocolVersion((byte)(0));
		type = PacketType.UNKNOWN;
		setChannelId(-1);
	}
	
	public Packet(int id, ProtocolVersion ver, PacketType type, byte [] payload) {
		this.protocolVersion = ver;
		this.type = type;
		this.payload = payload;
		this.setChannelId(id);
	}

	public ProtocolVersion getProtocolVersion() {
		return protocolVersion;
	}

	public void setProtocolVersion(ProtocolVersion version) {
		this.protocolVersion = version;
	}

	public PacketType getType() {
		return type;
	}

	public void setType(PacketType type) {
		this.type = type;
	}

	public byte[] getPayload() {
		return payload;
	}

	public void setPayload(byte[] payload) {
		this.payload = payload;
	}

	public int getChannelId() {
		return channelId;
	}

	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}
}
