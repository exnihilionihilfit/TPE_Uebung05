package de.hs_mannheim_ib.tpe.chr_luk.uebung_05.powerplant.component;

/**
 * basic element of the reactor
 * @author
 *
 */

public class Component {
	private Recuperator recuperator;
	private boolean shutdown = false;
	public Recuperator getRecuperator() {
	    return recuperator;
    }
/**
 * adds the recuperator to the component to exchange heat
 * @param recuperator
 */
	protected void setRecuperator(final Recuperator recuperator) {
	    this.recuperator = recuperator;
    }
	/**
	 * use the recuperator to cool the components witch cooling water
	 * 
	 */
	public void useRecuperator(){
		this.recuperator.coolComponent();
		
	}
	
	/**
	 * @return is the components is operating 
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
