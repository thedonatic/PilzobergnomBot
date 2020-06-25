package de.donatic.discord;

import java.util.HashMap;
import java.util.Map;

import de.donatic.Bot;
import de.donatic.command.Command;
import de.donatic.util.Logger;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;

public class MessageHandler {
	
	private Map<String,Command> commandList;
	private Bot bot;
	
	public MessageHandler(Bot bot) {
		registerMessageListener(bot.getGateway());
		commandList = new HashMap<String,Command>();
		this.bot = bot;
	}
	
	public void onMessage(MessageCreateEvent event) {
		if(event.getMember().get().isBot())
			return;
		Logger.log(this, "Recived message: "+event.getMessage().getContent(), Logger.INCOMINGMESSAGE);
		String message = event.getMessage().getContent();
		String prefix = String.valueOf(bot.getConfig().getCommandPrefix());
		if(message.startsWith(prefix)) {
			Command c = commandList.get(event.getMessage().getContent().split(" ")[0].substring(1));
			if(c != null) 
				c.onCommand(event);
		}
	}
	
	private void registerMessageListener(GatewayDiscordClient gateway) {
		gateway.on(MessageCreateEvent.class).subscribe(event -> {
		      onMessage(event);
		});
	}
	
	public void registerCommand(Command command) {
		this.commandList.put(command.getCommand(), command);
	}
	
	
}
