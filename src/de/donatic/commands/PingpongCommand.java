package de.donatic.commands;

import de.donatic.command.Command;
import de.donatic.discord.MessageHandler;
import discord4j.core.event.domain.message.MessageCreateEvent;

public class PingpongCommand extends Command {

	public PingpongCommand(MessageHandler handler) {
		super("ping", handler);
		
	}

	public void onCommand(MessageCreateEvent event) {
		
		int random;
		random = (int) (Math.random() * 100);
		
		if(random <= 69 && random >=68) {
			event.getMessage().getChannel().block().createMessage("du hurensohn").block();
		}
		else {
			event.getMessage().getChannel().block().createMessage("pong").block();
		}
		
		
	}

}
