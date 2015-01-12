package com.epikur.nettyserver.protocol.messages;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.epikur.nettyserver.protocol.Packet;
import com.epikur.nettyserver.server.Server;

public class MessageHandler implements Runnable {
	private Server server;
	
	private Queue<Message> qInMessages;
	private Queue<Message> qOutMessages;
	
	/**
	 * Retrieves and removes next <b>IN</b> message from queue.
	 * @return {@link Message} or null, if there is no message in queue.
	 */
	public Message getInMessage() {
		return qInMessages.poll();
	}
	
	/**
	 * Puts the message to <b>OUT</b> queue, which will be send to client later.
	 * @param msg {@link Message} to send.
	 */
	public void putOutMessage(Message msg) {
		qOutMessages.offer(msg);
	}
	
	/**
	 * Puts the message to <b>IN</b> queue, which can be handled with Business Logic later.
	 * This method is for {@link Server} only.
	 * @param msg {@link Message} to send
	 */
	public void putInMessage(Message msg) {
		qInMessages.offer(msg);
	}

	/**
	 * Puts the packet to <b>IN</b> queue, which will be converted into {@link Message} and will be handled by Business Logic later.
	 * This method is for {@link Server} only.
	 * @param p {@link Packet} to send
	 */
	public void putInPacket(Packet p) {
		Message msg;
		
		if (p.getProtocolVersion().getVersion() == 0) {
			msg = MessageFactory.getMessage(p.getType());
			
			if (msg != null) {
				msg.decodePacket(p);
				putInMessage(msg);
			}
		}
	}
	
	public MessageHandler(Server srv) {
		this.server = srv;
		
		qInMessages = new ConcurrentLinkedQueue<Message>();
		qOutMessages = new ConcurrentLinkedQueue<Message>();
	}
	
	public void run() {
		while (server.isRunning()) {
			// if OUT message queue is not empty, peek next message, encode it to Packet and send
			if (!qOutMessages.isEmpty())
				server.send(qOutMessages.poll().encode());
		}
		
		qInMessages.clear();
		qOutMessages.clear();
	}
}
