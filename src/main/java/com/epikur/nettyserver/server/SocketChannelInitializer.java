package com.epikur.nettyserver.server;

import com.epikur.nettyserver.protocol.PacketDecoder;
import com.epikur.nettyserver.protocol.PacketEncoder;

import io.netty.channel.*;

public class SocketChannelInitializer extends ChannelInitializer<Channel>{
	private Server server;
	
	public SocketChannelInitializer(Server srv) {
		this.server = srv;
	}
	
	@Override
	protected void initChannel(Channel ch) throws Exception {
		ch.pipeline().addLast("decoder", new PacketDecoder());
		ch.pipeline().addLast("encoder", new PacketEncoder());
		ch.pipeline().addLast("handler", server.getChannelHandler());
	}

}
