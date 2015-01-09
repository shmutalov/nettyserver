package com.epikur.nettyserver.protocol;

public class ProtocolVersion {
	private byte version;
	
	public byte getVersion() {
		return version;
	}
	
	public void setVersion(byte bVersion) {
		this.version = bVersion;
	}
	
	public ProtocolVersion(byte bVersion) {
		this.version = bVersion;
	}
}
