package de.hs_mannheim_ib.tpe.chr_luk.uebung_05.powerplant.component;


public class Component {
	private Recuperator recuperator;
	private boolean shutdown = false;
	public Recuperator getRecuperator() {
	    return recuperator;
    }

	protected void setRecuperator(final Recuperator recuperator) {
	    this.recuperator = recuperator;
    }
	public void useRecuperator(){
		this.recuperator.coolComponent();
		
	}
	
	/**
	 * @return the shutdown
	 */
	public boolean isShutdown() {
		return shutdown;
	}

	/**
	 * @param shutdown the shutdown to set
	 */
	public void setShutdown(boolean shutdown) {
		this.shutdown = shutdown;
	}
	
	
}
