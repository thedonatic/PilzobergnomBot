package de.donatic.config;

import java.io.File;
import java.io.IOException;

import com.google.gson.Gson;
import de.donatic.util.FileHandler;

public class ConfigHandler {

	private final static File configFile = new File("config.json");
	
	private Config config = null;
	
	public ConfigHandler() {
		reloadData();
		System.out.println(ConfigHandler.configFile.getAbsolutePath());
	}
	
	public char getCommandPrefix() {
		return config.commandPrefix;
	}
	
	public String getWordListFile() {
		return config.wordFilePath;
	}
	
	public void reloadData() {
		if(ConfigHandler.configFile.exists()) {
			Gson gson = new Gson();
			this.config = gson.fromJson(getConfigText(), Config.class);
		}else {
			this.config = Config.getDefaultConfig();
			saveData();
		}
	}
	
	public void saveData() {
		if(!ConfigHandler.configFile.exists()) {
			try {
				configFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
		}
		Gson gson = new Gson();
		FileHandler.writeFileContent(ConfigHandler.configFile.getAbsolutePath(), gson.toJson(config));
	}
	
	private String getConfigText() {
		return FileHandler.getFileContent(ConfigHandler.configFile.getAbsolutePath());
	}
	
}
