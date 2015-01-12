package com.epikur.nettyserver.server;

import com.epikur.nettyserver.protocol.Packet;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ServerChannelHandler extends SimpleChannelInboundHandler<Packet> {
	private Server server;
	
	public ServerChannelHandler(Server srv) {
		this.server = srv;
	}
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Packet packet)
			throws Exception {
		// just put the packet into message IN queue
		server.getMessageHandler().putInPacket(packet);
	}

}
