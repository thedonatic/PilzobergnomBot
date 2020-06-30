package de.donatic;

import java.io.File;

import de.donatic.commands.PingpongCommand;
import de.donatic.commands.RandomCommand;
import de.donatic.commands.WordlistCommand;
import de.donatic.config.ConfigHandler;
import de.donatic.discord.MessageHandler;
import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;

public class Bot {

	private final DiscordClient client;
	private final GatewayDiscordClient gateway;
	private final ConfigHandler configHandler;
	private final MessageHandler messageHandler;
	
	public Bot(String token) {
	    client = DiscordClient.create(token);
	    gateway = client.login().block();
	    configHandler = new ConfigHandler();
	    messageHandler = new MessageHandler(this);
	    initCommands();
	    gateway.onDisconnect().block();
	}
	
	public GatewayDiscordClient getGateway() {
		return gateway;
	}
	
	public ConfigHandler getConfig() {
		return configHandler;
	}
	
	private void initCommands() {
		new WordlistCommand(messageHandler,new File(configHandler.getWordListFile()));
		new PingpongCommand(messageHandler);
		new RandomCommand(messageHandler);
	}
	
}
