package com.epikur.nettyserver.protocol;

public class Packet {
	private ProtocolVersion protocolVersion;
	private PacketType type;
	private byte[] payload;
	
	public Packet () {
		protocolVersion = ProtocolVersion.fromByte((byte)(0));
		type = PacketType.UNKNOWN;
	}
	
	public Packet(ProtocolVersion ver, PacketType type, byte [] payload) {
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
}
