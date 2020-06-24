package de.donatic.config;

public class Config {
	
	public char commandPrefix;
	public String wordFilePath;
	
	private Config(char commandPrefix, String wordFilePath) {
		this.commandPrefix = commandPrefix;
		this.wordFilePath = wordFilePath;
	}
	
	public static Config getDefaultConfig() {
		return new Config(
				'-',
				"words.txt"
				);
	}
	
}
