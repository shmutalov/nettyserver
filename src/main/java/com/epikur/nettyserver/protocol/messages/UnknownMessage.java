package com.epikur.nettyserver.protocol.messages;

import com.epikur.nettyserver.protocol.Packet;
import com.epikur.nettyserver.protocol.PacketType;
import com.epikur.nettyserver.protocol.ProtocolVersion;

public class UnknownMessage implements Message {
	private byte [] payload;
	
	public void decodePacket(Packet p) {
		payload = p.getPayload();
	}

	public Packet encode() {
		Packet p = new Packet();
		p.setPayload(payload);
		p.setProtocolVersion(new ProtocolVersion((byte)0));
		p.setType(PacketType.UNKNOWN);
		
		return p;
	}
}
