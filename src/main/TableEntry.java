package main;

import javax.swing.JTextArea;

public class TableEntry {
	
	private int seq;
	private int letter;
	private int legal;
	

	public TableEntry() {}
	
	
	public static void printHeader(JTextArea ta) {
	    ta.append(String.format("%12s %2s %6s %2s %6s", "  Sequence #", "|", "Letter", "|", "Legal " + "\n"));
	    ta.append(String.format("%s", "--------------------------------" + "\n"));
	}
	
	public void printSequence(JTextArea ta) {
		int seq = getSeq();
		int letter = getLetter();
		int legal = getLegal();
		String letterString = String.valueOf(letter);
		String legalString = String.valueOf(legal);
		
		letterString = (((getLetter() < 1) && (legal > letter)) ? "-" : String.valueOf(getLetter())); // Replace 0 counts with dashes.
		legalString = (((getLegal() < 1) && (letter > legal)) ? "-" : String.valueOf(getLegal()));
		
		ta.append(String.format("%10s %4s %6s %2s %6s", seq, "|", letterString, "|", legalString + "     "));
		ta.setCaretPosition(ta.getDocument().getLength());
	}


	public int getLetter() {
		return letter;
	}


	public void setLetter(int letter) {
		this.letter = letter;
	}


	public int getLegal() {
		return legal;
	}


	public void setLegal(int legal) {
		this.legal = legal;
	}


	public int getSeq() {
		return seq;
	}


	public void setSeq(int seq) {
		this.seq = seq;
	}
}

