package com.epikur.nettyserver.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.util.List;

public class MessageEncoder extends MessageToByteEncoder<Message> {
	
	@Override
	protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out)
			throws Exception {

		// VERSION
		out.writeByte(msg.getProtocolVersion().getVersion());
		
		// TYPE
		out.writeByte(MessageType.toByte(msg.getType())) ;
		
		// PAYLOAD LENGTH
		out.writeInt(msg.getPayload().length);
		
		// PAYLOAD
		out.writeBytes(msg.getPayload());
	}

}
