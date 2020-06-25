package de.donatic.commands;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import de.donatic.command.Command;
import de.donatic.discord.MessageHandler;
import de.donatic.discord.MessageTask;
import de.donatic.util.Logger;
import de.donatic.util.FileHandler;
import de.donatic.util.network.SkribblIoConnector;
import discord4j.core.event.domain.message.MessageCreateEvent;

public class WordlistCommand extends Command{
	
	private static final String command = "wordlist";
	private static final int deleteDelay = 3000;
	private static final char listSeperator = ',';
	
	private File wordListFile;
	private SkribblIoConnector connector;

	public WordlistCommand(MessageHandler handler, File wordListFile) {
		super(WordlistCommand.command, handler);
		this.wordListFile = wordListFile;
	}

	@Override
	public void onCommand(MessageCreateEvent event) {
		String message = event.getMessage().getContent();
		String[] args = message.split("\"");
		if(args.length == 1) {
			onGet(event);
		}
		else if(args.length == 2 || args.length == 3) {
			onAdd(args[1],event);
		}
		else if(args.length == 4 || args.length == 5) {
			onEdit(args[1],args[3]);
		}
		new MessageTask().deleteMessage(event.getMessage(), deleteDelay);
	}
	
	private void onAdd(String word,MessageCreateEvent event) {
		if(checkForWord(word)) {
			Logger.log(this,"Word "+word+" is already in the list",Logger.INFO);
			new MessageTask().sendAndDelete(event.getMessage().getChannel().block(),word+" is already in the list of doom",deleteDelay);
		}else {
			Logger.log(this, "Add word "+word+" to wordlist", Logger.INFO);
			appendFileToList(word);
			new MessageTask().sendAndDelete(event.getMessage().getChannel().block(),"Added "+word+" to the list of doom",deleteDelay);
		}
	}
	
	private void onGet(MessageCreateEvent event) {
		if(this.connector == null) {
			String list = FileHandler.getFileContent(this.wordListFile.getAbsolutePath());
			list = list.replaceAll("\n", "\\\\n").replaceAll("\r", "");
			new MessageTask().sendAndDelete(event.getMessage().getChannel().block(),"Generating your lobby ...",5000);
			this.connector = new SkribblIoConnector();
			connector.init(list);
			connector.setExclusiveWordsOnly();
			new MessageTask().send(event.getMessage().getChannel().block(),"Link to lobby: "+connector.getInviteLink());
		}else {
			connector.startGame();
			new MessageTask().sendAndDelete(event.getMessage().getChannel().block(),"Starting Game",deleteDelay);
			connector.close();
			connector = null;
		}
		
	}
	
	private void onEdit(String oldString, String newString) {
		replaceInFile(oldString,newString);
	}
	
	private void appendFileToList(String word) {
		FileWriter fw = null;
        try {
        	fw = new FileWriter(wordListFile.getAbsoluteFile(),true);
			fw.write("\n"+word+WordlistCommand.listSeperator);
			fw.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private boolean checkForWord(String word) {
		BufferedReader br = null;
        try {
        	br = new BufferedReader(new FileReader(wordListFile.getAbsoluteFile()));
        	String line;
            while ((line = br.readLine()) != null) {
            	String test = line.substring(0,line.length()-1);
                if(test.equalsIgnoreCase(word)) {
                	br.close();
                	return true;
                }
            }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
        return false;
	}
	
	private boolean checkForWordAn(String word) {
		BufferedReader br = null;
        try {
        	br = new BufferedReader(new FileReader(wordListFile.getAbsoluteFile()));
        	String line;
            while ((line = br.readLine()) != null) {
            	String test = line.substring(0,line.length()-1);
                if(test.equalsIgnoreCase(word)) {
                	br.close();
                	return true;
                }
            }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
        return false;
	}
	
	private void replaceInFile(String oldString, String newString) {
		List<String> fileContent = null;
		try {
			fileContent = new ArrayList<String>(Files.readAllLines(this.wordListFile.toPath(), StandardCharsets.UTF_8));
			for (int i = 0; i < fileContent.size(); i++) {
			    if (fileContent.get(i).equals(oldString.substring(0, oldString.length()-2))) {
			        fileContent.set(i, oldString);
			        break;
			    }
			}

			try {
				Files.write(this.wordListFile.toPath(), fileContent, StandardCharsets.UTF_8);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}
	
}
