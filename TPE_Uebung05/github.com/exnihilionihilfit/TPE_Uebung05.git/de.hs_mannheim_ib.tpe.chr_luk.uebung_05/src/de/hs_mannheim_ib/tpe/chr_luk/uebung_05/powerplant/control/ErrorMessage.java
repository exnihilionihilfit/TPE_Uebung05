package de.hs_mannheim_ib.tpe.chr_luk.uebung_05.powerplant.control;

import java.util.ArrayList;

/**
 * This saves the error messages
 * stored in msgList
 * @author 
 *
 */
public class ErrorMessage {

	private static ArrayList<InterruptedException> msgList;

	public ErrorMessage() {
		msgList = new ArrayList<>();
	}

	/**
	 * adds a new error message to the list
	 * @param e
	 */
	public static void addToList(InterruptedException e) {
		msgList.add(e);
	}
	/**
	 * prints all error messages via console
	 */

	public static void msgToConsole() {
		for(InterruptedException e: msgList){
			System.out.println(e.getMessage());
		}
	}



}
