package com.epikur.nettyserver.protocol.messages;

import com.epikur.nettyserver.protocol.Packet;

public interface Message {
	public void decodePacket(Packet p);
	public Packet encode();
}
