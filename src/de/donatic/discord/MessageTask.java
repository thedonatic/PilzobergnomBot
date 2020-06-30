package de.donatic.discord;

import java.util.Timer;
import java.util.TimerTask;

import de.donatic.util.Logger;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;

public class MessageTask {

	private static final int deleteDelay = 3000;
	
	public MessageTask() {
	}
	
	public Message send(MessageChannel channel, String message) {
		Logger.log(this, "Send message: "+message, Logger.SENDMESSAGE);
		return channel.createMessage(message).block();
	}
	
	public void sendAndDelete(MessageChannel channel, String message, int delay) {
		Message m = send(channel,message);
		deleteMessage(m,delay);
	}
	
	public void sendAndDelete(MessageChannel channel, String message) {
		Message m = send(channel,message);
		deleteMessageWithDelfaultDelay(m);
	}
	
	public void deleteMessageWithDelfaultDelay(Message message) {
		deleteMessage(message,deleteDelay);
	}
	
	public void deleteMessage(Message message, int delay) {
		Timer time = new Timer();
		time.schedule(new DeleteMessageTask(message), delay);
	}
	
	class DeleteMessageTask extends TimerTask {

	    private final Message message;


	    DeleteMessageTask(Message message){
	      this.message = message;
	    }

	    public void run() {
	    	this.message.delete().block();
	    	Logger.log(this, "Deleted \""+message+"\"", Logger.INFO);
	    }
	}
	
}
