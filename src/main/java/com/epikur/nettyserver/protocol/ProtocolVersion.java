package com.epikur.nettyserver.protocol;

public class ProtocolVersion {
	private byte version_id;
	
	public byte getVersion() {
		return version_id;
	}

	public void setVersion(byte bVersion) {
		this.version_id = bVersion;
	}

	public static ProtocolVersion fromByte(byte bVersion) {
		ProtocolVersion v = new ProtocolVersion();
		v.version_id = bVersion;
		
		return v;
	}
}
