package com.epikur.nettyserver.server;

import io.netty.bootstrap.ChannelFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.*;

import java.net.InetSocketAddress;

import javax.crypto.spec.GCMParameterSpec;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.epikur.nettyserver.protocol.Packet;
import com.epikur.nettyserver.protocol.messages.MessageHandler;

public class Server {
	private static final Logger LOG = Logger.getLogger(Server.class.getName());
	private static final int cores = Runtime.getRuntime().availableProcessors();
	
	private boolean running = false;
	
	private int port;
	
	private Channel srv_channel;
	
	EventLoopGroup bossExecutor;
	EventLoopGroup workerExecutor;
	ServerBootstrap bootstrap;
	
	ServerChannelHandler channelHandler;
	MessageHandler messageHandler;
	Thread msgHandlerThread;
	
	public MessageHandler getMessageHandler() {
		return messageHandler;
	}

	public void setMessageHandler(MessageHandler messageHandler) {
		this.messageHandler = messageHandler;
	}

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
		return running;
	}

	public void stop() {
		if (LOG.isInfoEnabled())
			LOG.info("Stopping server...");
		System.out.println("Stopping server...");
		
		if (isRunning()) {
			this.running = false; 
			
			if (LOG.isInfoEnabled())
				LOG.info("Stopping message handler...");
			System.out.println("Stopping message handler...");
			
			try {
				Thread.sleep(1000);
			} catch (Exception ex) {}
			
			/* Message handler still alive? 
			 * Ok, we will wait for extra 5 seconds, then
			 * we will kill it HARDLY
			 */
			if (msgHandlerThread.isAlive()) {
				if (LOG.isInfoEnabled())
					LOG.info("Message handler still running... ");
				System.out.println("Message handler still running... ");
				
				try {
					Thread.sleep(1000);
				} catch (Exception ex) {}
				
				msgHandlerThread.stop();
			}
			
			bossExecutor.shutdownGracefully();
			workerExecutor.shutdownGracefully();
			
			bossExecutor.terminationFuture().awaitUninterruptibly();
			workerExecutor.terminationFuture().awaitUninterruptibly();
			
			srv_channel.closeFuture().awaitUninterruptibly();
		}
		
		if (LOG.isInfoEnabled())
			LOG.info("Server stopped.");
		System.out.println("Server stopped.");
	}

	public void start(String[] args) {
		if (isRunning()) {
			if (LOG.isInfoEnabled()) {
				LOG.info("Server is already running");
				LOG.info("Firstly stop the server, then run it again");
			}
			
			System.out.println("Server is already running");
			System.out.println("Firstly stop the server, then start it again");
			
			return;
		} else {
			// is PORT number passed as parameter?
			if (args.length > 1) {
				try {
					this.port = Integer.parseInt(args[1]);
				} catch (Exception ex) {
					LOG.error("Invalid PORT number");
					LOG.log(Level.ERROR, "Exception: ", ex);
					
					return;
				}
			}	
			
			if (port <= 0) {
				LOG.error("PORT number must be greater than 0");
				return;
			}
			
			if (LOG.isInfoEnabled())
				LOG.info("Starting server on port " + port + "...");
			System.out.println("Starting server on port " + port + "...");
			
			bootstrap = new ServerBootstrap();
			
			// TODO implement LINUX EPOLL configuration check here
			if (false) {
				if (LOG.isInfoEnabled())
					LOG.info("EPOLL event loop groups will be used");
				System.out.println("EPOLL event loop groups will be used");
				
				bossExecutor = new EpollEventLoopGroup();
				workerExecutor = new EpollEventLoopGroup(cores);
				
				bootstrap.channel(EpollServerSocketChannel.class);
			} else {
				if (LOG.isInfoEnabled())
					LOG.info("Default NIO event loop groups will be used");
				System.out.println("Default NIO event loop groups will be used");
				
				bossExecutor = new NioEventLoopGroup();
				workerExecutor = new NioEventLoopGroup(cores);
				
				bootstrap.channel(NioServerSocketChannel.class);
			}
			
			bootstrap.group(bossExecutor, workerExecutor);
			
			bootstrap.localAddress(port);
			bootstrap.option(ChannelOption.SO_BACKLOG, 512);
			bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000); // 10 seconds
			
			bootstrap.childOption(ChannelOption.TCP_NODELAY, true);
			bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
			
			channelHandler = new ServerChannelHandler(this);
			messageHandler = new MessageHandler(this);
			msgHandlerThread = new Thread(messageHandler);
			
			if (LOG.isInfoEnabled())
				LOG.info("Setting up handlers...");
			System.out.println("Setting up handlers...");
			
			bootstrap.childHandler(new SocketChannelInitializer(this));
			
			if (LOG.isInfoEnabled())
				LOG.info("Binding to port...");
			System.out.println("Binding to port...");
			
			ChannelFuture f = bootstrap.bind();
			
			while (!f.isDone());
			
			if (f.isSuccess()) {
				srv_channel = f.channel();
				
				running = true;
				
				if (LOG.isInfoEnabled())
					LOG.info("Starting message handler...");
				System.out.println("Starting message handler...");
				
				msgHandlerThread.start();
				
				try {
					Thread.sleep(1000);
				} catch (Exception ex) {}
				
				if (msgHandlerThread.isAlive()) {
					if (LOG.isInfoEnabled())
						LOG.info("Server started.");
					System.out.println("Server started.");
				}
					
			} else {
				LOG.log(Level.ERROR, "Cannot start server. Exception: ", f.cause());
				System.out.println("Cannot start server.");
				
				bossExecutor.shutdownGracefully();
				workerExecutor.shutdownGracefully();
				
				bossExecutor.terminationFuture().awaitUninterruptibly();
				workerExecutor.terminationFuture().awaitUninterruptibly();
				
				running = false;
			}
		}
	}
	
	public ServerChannelHandler getChannelHandler() {
		return channelHandler;
	}

	public void setChannelHandler(ServerChannelHandler channelHandler) {
		this.channelHandler = channelHandler;
	}

	public void send(Packet p) {
		
	}
}
