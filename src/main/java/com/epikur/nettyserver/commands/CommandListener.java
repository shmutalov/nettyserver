package com.epikur.nettyserver.commands;

public interface CommandListener extends Runnable {
	// Is listener running?
	public boolean isRunning() ;

	// Set listener running status
	public void setRunning(boolean bRunning) ;

	// Execute command by its name > args[0]
	public void doCommand(String [] args);
	
	// Add command executor
	public void addCommand(String strCommandName, Command cmd);
	
	// Remove command from command list
	public void removeCommand(String strCommandName);
	
	// Print help
	public void printHelp() ;
}
