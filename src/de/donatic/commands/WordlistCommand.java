package de.donatic.commands;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import de.donatic.command.Command;
import de.donatic.discord.MessageHandler;
import de.donatic.discord.MessageTask;
import de.donatic.util.Logger;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;

public class WordlistCommand extends Command{
	
	private static final String command = "wordlist";
	private static final int deleteDelay = 3000;
	private static final char listSeperator = ',';
	
	private File wordListFile;

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
		else if(args.length == 2) {
			onAdd(args[1],event);
		}
	}
	
	private void onAdd(String word,MessageCreateEvent event) {
		if(checkForWord(word)) {
			Logger.log(this,"Word "+word+" is already in the list",Logger.INFO);
			new MessageTask().sendAndDelete(event.getMessage().getChannel().block(),word+" is already in the list of doom",deleteDelay);
		}else {
			System.out.println("Add word "+word);
			Logger.log(this, "Add word "+word+" to wordlist", Logger.INFO);
			appendFileToList(word);
			new MessageTask().sendAndDelete(event.getMessage().getChannel().block(),"Added "+word+" to the list of doom",deleteDelay);
		}
		new MessageTask().deleteMessage(event.getMessage(), deleteDelay);
	}
	
	private void onGet(MessageCreateEvent event) {
		System.out.println("Get words");
		new MessageTask().sendAndDelete(event.getMessage().getChannel().block(),"Not availible yet",deleteDelay);
	}
	
	private void onEdit() {
		
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
	
}
