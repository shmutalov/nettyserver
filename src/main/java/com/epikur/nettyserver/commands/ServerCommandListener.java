package com.epikur.nettyserver.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.epikur.nettyserver.server.Server;

public class ServerCommandListener implements CommandListener {
	private final static Logger LOG = Logger.getLogger(ServerCommandListener.class.getName());
	
	private boolean bRunning;
	
	public boolean isRunning() {
		return bRunning;
	}

	public void setRunning(boolean bRunning) {
		this.bRunning = bRunning;
	}

	Scanner reader;
	
	private Server server;
	private Map<String, Command> commands;
	
	public Server getServer() {
		return server;
	}
	
	public void setServer(Server server) {
		this.server = server;
	}
	
	public ServerCommandListener (Server srv) {
		setServer(srv);
		
		commands = new HashMap<String, Command>();
		
		// add default "help" command;
		commands.put("help", new Command() {

			public void run(String[] args) {
				if (args.length > 1) {
					if (commands.containsKey(args[1])) {
						if (LOG.isInfoEnabled())
							LOG.info(args[1] + " " + commands.get(args[1]).getHelp());
						
						System.out.println(args[1] + " " + commands.get(args[1]).getHelp());
					} else {
						LOG.error("Unknown command <" + args[1] + ">");
						
						System.out.println("Unknown command <" + args[1] + ">");
					}
				} else {
					printHelp();
				}
			}

			public String getHelp() {
				return "[COMMAND] \t - Prints command list or help for [COMMAND]";
			}
		});
		
		// add default "exit" command;
		commands.put("exit", new Command() {

			public void run(String[] args) {
				if (getServer().isRunning()) {
					getServer().stop();
				} 
				
				setRunning(false);
			}

			public String getHelp() {
				return "\t\t - Exits from application";
			}
		});
		
		// add default "start" command;
		commands.put("start", new Command() {

			public void run(String[] args) {
				if (!getServer().isRunning()) {
					getServer().start(args);
				} else {
					if (LOG.isInfoEnabled())
						LOG.info("Server is already running");
					
					System.out.println("Server is already running");
				}
			}

			public String getHelp() {
				return "[PORT] \t - Prints command list or help for [COMMAND]";
			}
		});
		
		// add default "status" command;
		commands.put("status", new Command() {

			public void run(String[] args) {
				if (!getServer().isRunning()) {
					if (LOG.isInfoEnabled())
						LOG.info("Server is not running");
					
					System.out.println("Server is not running");
				} else {
					if (LOG.isInfoEnabled())
						LOG.info("Server is running on port " + server.getPort());
					
					System.out.println("Server is running on port " + server.getPort());
				}
			}

			public String getHelp() {
				return "\t\t - Prints server running status";
			}
		});
		
		// add default "stop" command;
		commands.put("stop", new Command() {

			public void run(String[] args) {
				if (getServer().isRunning()) {
					getServer().stop();
				}
			}

			public String getHelp() {
				return "\t\t - Stops the server";
			}
		});

		bRunning = true;
	}
	
	public void doCommand(String [] args) {
		if (commands.containsKey(args[0])) {
			commands.get(args[0]).run(args);
		} else {
			LOG.error("Unknown command <" + args[0] + ">");
			
			System.out.println("Unknown command <" + args[0] + ">");
		}
	}
	
	public void addCommand(String strCommandName, Command cmd) {
		if (commands.containsKey(strCommandName)) {
			LOG.error("Command <" + strCommandName + "> already exists");
			
			System.out.println("Command <" + strCommandName + "> already exists");
		} else {
			commands.put(strCommandName, cmd);
			
			if (LOG.isInfoEnabled())
				LOG.info("Command <" + strCommandName + "> added");
			
			System.out.println("Command <" + strCommandName + "> added");
		}
	}
	
	public void removeCommand(String strCommandName) {
		if (commands.containsKey(strCommandName)) {
			commands.remove(strCommandName);
			
			if (LOG.isInfoEnabled())
				LOG.info("Command <" + strCommandName + "> removed");
			
			System.out.println("Command <" + strCommandName + "> removed");
		} else {
			LOG.error("Command <" + strCommandName + "> not exists");
			
			System.out.println("Command <" + strCommandName + "> not exists");
		}
	}
	
	public void printHelp() {
		if (LOG.isInfoEnabled())
			LOG.info("Command list:");
		
		System.out.println("Command list:");
		
		List<String> keys = new ArrayList<String>(commands.keySet());
		
		Collections.sort(keys);
		
		for(String s : keys) {
			if (LOG.isInfoEnabled())
				LOG.info(s + " " + commands.get(s).getHelp());
			
			System.out.println(s + " " + commands.get(s).getHelp());
		}
	}
	
	public void run() {
		if (LOG.isInfoEnabled())
			LOG.info("Command listener started.");
		
		System.out.println("Command listener started.");
		
		reader = new Scanner(System.in);
		
		String strCmd = "";
		
		setRunning(true);
		
		printHelp();
		
		while (isRunning()) {
			System.out.print("> ");
			
			strCmd = reader.nextLine();
			
			if (strCmd.length() > 0) {
				String [] args = strCmd.split(" ");
				doCommand(args);
			}
		}
		
		if (LOG.isInfoEnabled())
			LOG.info("Command listener stopped");
		
		System.out.println("Command listener stopped");
	}
}
