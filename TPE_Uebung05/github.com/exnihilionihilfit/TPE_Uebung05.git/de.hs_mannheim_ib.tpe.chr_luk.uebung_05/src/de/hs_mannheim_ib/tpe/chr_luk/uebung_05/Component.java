package de.hs_mannheim_ib.tpe.chr_luk.uebung_05;

public class Component {
	private volatile boolean first = true;

	public boolean isFirst() {
	    return first;
    }

	public void setFirst(boolean itsTurn) {
	    this.first = itsTurn;
    }
}
