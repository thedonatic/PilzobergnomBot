package de.donatic.command;

import de.donatic.discord.MessageHandler;
import discord4j.core.event.domain.message.MessageCreateEvent;

public abstract class Command {
	
	private String command;
	private MessageHandler handler;
	
	public Command(String command, MessageHandler handler) {
		this.command = command;
		this.handler = handler;
		handler.registerCommand(this);
	}
	
	public abstract void onCommand(MessageCreateEvent event);

	public String getCommand() {
		return command;
	}

	public MessageHandler getHandler() {
		return handler;
	}
	
}
