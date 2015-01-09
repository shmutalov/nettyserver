package com.epikur.nettyserver.protocol.messages;

import com.epikur.nettyserver.protocol.Packet;

public class UnknownMessage implements Message {
	private byte [] payload;
	
	public void decodePacket(Packet p) {
		payload = p.getPayload();
	}

	public Packet encode() {
		Packet p = new Packet();
		p.setPayload(payload);
		
		return p;
	}
}
