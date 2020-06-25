package de.donatic.discord;

import java.util.Timer;
import java.util.TimerTask;

import de.donatic.util.Logger;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;

public class MessageTask {

	public MessageTask() {
	}
	
	public Message send(MessageChannel channel, String message) {
		Logger.log(this, "Send message: "+message, Logger.SENDMESSAGE);
		return channel.createMessage(message).block();
	}
	
	public void sendAndDelete(MessageChannel channel, String message, int destroyAfter) {
		Message m = send(channel,message);
		deleteMessage(m,destroyAfter);
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
