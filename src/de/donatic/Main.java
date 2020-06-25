package de.donatic;

import de.donatic.util.network.SkribblIoConnector;

public class Main {
	
	public static void main(String[] args) {
		if(args.length > 0) {
			new Bot(args[0]);
		}
		else
			System.out.println("Token is missing");
	}
	
}
