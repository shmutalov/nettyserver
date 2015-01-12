package com.epikur.nettyserver.protocol;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

public class PacketDecoder extends ReplayingDecoder<PacketDecoder.DecodingState> {

	public enum DecodingState {
		VERSION
		, TYPE
		, PAYLOAD_LENGTH
		, PAYLOAD
	}

	private byte version;
	private byte type;
	private int payload_length;
	
	public PacketDecoder () {
		super(PacketDecoder.DecodingState.VERSION);
	}
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf buf,
			List<Object> out) throws Exception {
		switch (this.state()) {
		case VERSION:
			version = buf.readByte();
			checkpoint(DecodingState.TYPE);
		case TYPE:
			type = buf.readByte();
			checkpoint(DecodingState.PAYLOAD_LENGTH);
		case PAYLOAD_LENGTH:
			payload_length = buf.readInt();
			checkpoint(DecodingState.PAYLOAD);
		case PAYLOAD:
			ByteBuf payload_buf = buf.readBytes(payload_length);
			checkpoint(DecodingState.VERSION);
			
			out.add(new Packet(ctx.channel().hashCode(), new ProtocolVersion(version), PacketType.fromByte(type), payload_buf.array()));
			break;
		default:
			throw new Error("Shouldn't reach here.");
		}
	}

}
