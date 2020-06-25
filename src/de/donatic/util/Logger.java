package de.donatic.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
	
	public static final int INFO = 0;
	public static final int WARNING = 1;
	public static final int ERROR = 2;
	public static final int INCOMINGMESSAGE = 3;
	public static final int SENDMESSAGE = 4;

	public static void log(Object sender,String message,int type) {
		Class<?> enclosingClass = sender.getClass();
		System.out.println(Logger.getCurrentTime()+" ["+type+"] ["+enclosingClass.getName()+"] "+message);
	}
	
	private static String getCurrentTime() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("[yyyy/MM/dd HH:mm:ss]");  
		LocalDateTime now = LocalDateTime.now();  
		return dtf.format(now);  
	}
	
}
