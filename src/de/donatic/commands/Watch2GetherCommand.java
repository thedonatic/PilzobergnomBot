package de.donatic.commands;

import de.donatic.command.Command;
import de.donatic.discord.MessageHandler;
import de.donatic.discord.MessageTask;
import de.donatic.util.network.Watch2GetherConnector;
import discord4j.core.event.domain.message.MessageCreateEvent;


public class Watch2GetherCommand extends Command {

	private static final String command = "watch";
	
	public Watch2GetherCommand(MessageHandler handler) {
		super(command, handler);
		
	}

	@Override
	public void onCommand(MessageCreateEvent event) {
		String[] link = event.getMessage().getContent().split(" ");
		if(link.length >= 2) {
			Watch2GetherConnector watch2getherlobby = new Watch2GetherConnector();
			watch2getherlobby.init(link[1]);
			new MessageTask().send(event.getMessage().getChannel().block(), "bruh" + watch2getherlobby.getInviteLink());
		}
	}

	
	
}
