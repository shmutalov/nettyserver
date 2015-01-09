package com.epikur.nettyserver.server;

import io.netty.bootstrap.ChannelFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

public class Server {
	private static final Logger LOG = Logger.getLogger(Server.class.getName());
	
	private int port;
	
	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public Server() {
		
	}
	
	public Server(int portNumber) {
		this.port = portNumber;
	}

	public boolean isRunning() {
		// TODO Auto-generated method stub
		return false;
	}

	public void stop() {
		LOG.info("Stopping...");
		
		LOG.info("Stopped");
	}

	public void start(String[] args) {
		// TODO Auto-generated method stub
		
	}
}
