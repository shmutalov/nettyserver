package com.epikur.nettyserver.protocol.messages;

import io.netty.buffer.ByteBufUtil;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.Arrays;

import com.epikur.nettyserver.crypting.Crypter.CryptingMethod;
import com.epikur.nettyserver.protocol.Packet;
import com.epikur.nettyserver.protocol.PacketType;
import com.epikur.nettyserver.protocol.ProtocolVersion;

// TODO 1.1 Implement Packet decoding algorithm

public class CryptKeyServerToClientMessage implements Message {
	private CryptingMethod cryptMethod;
	private byte [] key;
	
	/**
	 * CryptKeyServerToClientMessage =>
	 * <br/><b>1st byte</b> = {@link CryptingMethod}
	 * <br/><b>2nd int</b> = key hash length (keyLength)
	 * <br/><b>3rd bytes</b> = key hash keyLength bytes
	 * @see com.epikur.nettyserver.protocol.messages.Message#decodePacket(com.epikur.nettyserver.protocol.Packet)
	 */
	public void decodePacket(Packet p) {
		ByteBuffer buf = ByteBuffer.wrap(p.getPayload());
		
		byte bCryptMethod = buf.get();	// {CryptingMethod}
		int keyLength = buf.getInt();   // key hash length
		
		key = Arrays.copyOfRange(p.getPayload(), buf.position(), buf.remaining());
		cryptMethod = CryptingMethod.fromByte(bCryptMethod);
	}

	/**
	 * 1 byte = {@link CryptingMethod}
	 * 4 byte = key hash length (int)
	 * key.length bytes
	 */
	public Packet encode() {
		ByteBuffer buf = ByteBuffer.allocate(1 + 4 + key.length);
		
		buf.put(CryptingMethod.toByte(cryptMethod));
		buf.putInt(key.length);
		buf.put(key);
		
		Packet p = new Packet();
		p.setPayload(buf.array());
		p.setType(PacketType.CRYPT_KEY_SERVER_TO_CLIENT);
		
		return p;
	}

	public CryptingMethod getCryptMethod() {
		return cryptMethod;
	}

	public void setCryptMethod(CryptingMethod cryptMethod) {
		this.cryptMethod = cryptMethod;
	}

	public byte[] getKey() {
		return key;
	}

	public void setKey(byte[] key) {
		this.key = key;
	}

}
