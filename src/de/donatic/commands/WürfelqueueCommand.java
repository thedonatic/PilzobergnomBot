package de.donatic.commands;

import de.donatic.command.Command;
import de.donatic.discord.MessageHandler;
import discord4j.core.event.domain.message.MessageCreateEvent;

public class W�rfelqueueCommand extends Command {

	public W�rfelqueueCommand(MessageHandler handler) {
		super(command, handler);
	}

	public static final String command = "w�rfelqueue";

	@Override
	public void onCommand(MessageCreateEvent event) {
		// TODO Auto-generated method stub
		
	}
	
}
