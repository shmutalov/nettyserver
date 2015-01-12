package com.epikur.nettyserver;

import org.apache.log4j.Logger;

import com.epikur.nettyserver.commands.ServerCommandListener;
import com.epikur.nettyserver.server.Server;

public class NettyServer {
	private final static Logger LOG = Logger.getLogger(NettyServer.class.getName());
	
	public static void main(String[] args) {
		Server srv = new Server();
		ServerCommandListener cmd = new ServerCommandListener(srv);
		
		Thread t = new Thread(cmd);
		t.start();
		
		while (t.isAlive()) {
			
		}
		
		if (LOG.isInfoEnabled())
			LOG.info("Exiting...");
		
		System.out.println("Exiting...");
	}

}
