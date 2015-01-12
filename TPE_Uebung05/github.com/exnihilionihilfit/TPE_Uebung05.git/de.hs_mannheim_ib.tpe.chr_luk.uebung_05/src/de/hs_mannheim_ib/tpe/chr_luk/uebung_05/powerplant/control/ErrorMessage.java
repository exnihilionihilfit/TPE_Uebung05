package de.hs_mannheim_ib.tpe.chr_luk.uebung_05.powerplant.control;

import java.util.ArrayList;

public class ErrorMessage {

	private static ArrayList<InterruptedException> msgList;

	public ErrorMessage() {
		msgList = new ArrayList<>();
	}

	public static void addToList(InterruptedException e) {
		msgList.add(e);
	}

	public static void msgToConsole() {
		for(InterruptedException e: msgList){
			System.out.println(e.getMessage());
		}
	}



}
