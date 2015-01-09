package com.epikur.nettyserver.protocol.messages;

import com.epikur.nettyserver.protocol.Packet;

/**
 * Interface which used with business logic message handler.
 * @author Epikur
 *
 */
public interface Message {
	/** 
	 * Decodes and converts packet <i>p</i> to proper {@link Message} type.
	 * @param p Input {@link Packet}
	 */
	public void decodePacket(Packet p);
	
	/** 
	 * Encodes {@link Message} to {@link Packet}, which can be send upstream.
	 * @return encoded {@link Packet}
	 */
	public Packet encode();
}
