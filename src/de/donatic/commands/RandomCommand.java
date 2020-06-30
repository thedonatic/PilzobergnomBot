package de.donatic.commands;

import de.donatic.command.Command;
import de.donatic.discord.MessageHandler;
import de.donatic.discord.MessageTask;
import discord4j.core.event.domain.message.MessageCreateEvent;

public class RandomCommand extends Command {

	private static final String command = "random";
	
	public RandomCommand(MessageHandler handler) {
		super(command, handler);
	}

	@Override
	public void onCommand(MessageCreateEvent event) {
		String[] args = event.getMessage().getContent().split("\\s+");
		new MessageTask().send(event.getMessage().getChannel().block(), "Randomizer says: "+args[((int) ((Math.random()*args.length-1))+1)]);
	}

}
