package com.epikur.nettyserver.commands;

public interface Command {
	public void run(String [] args);
	
	public String getHelp();
}
