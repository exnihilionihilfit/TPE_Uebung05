package de.hs_mannheim_ib.tpe.chr_luk.uebung_05;

import java.util.Date;

public class Reactor extends Component implements Runnable, Heatable {

	int warming; // C° per second
	float maxTemp; // C°
	float coreTemp; // C°
	private long timeStamp; // time
	private long tmpTime = 0;
	private CoolingCircuit cc;
	private Recuperator recuperator;
	private WaterPackage tmpWaterPackage;
	private WaterPackage tmp;

	public Reactor(CoolingCircuit cc) {
		this.warming = 43;
		this.maxTemp = 2878;
		this.coreTemp = 10;

		this.timeStamp = new Date().getTime();
		this.cc = cc;
		this.recuperator = new Recuperator(this, cc, 0); // connected with
		                                                 // coolingCircuit at 0
		tmp = this.recuperator.getWaterPackage();
	}

	@Override
	public void run() {
		
		while (!Thread.currentThread().isInterrupted()) {
	
		
			if (!this.isShutdown() && !isOverheated()) {

				synchronized (cc) {
		
					
					while (this.isMinTempAddReached()) {
			
					
						try {
							cc.notifyAll();
							cc.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						tmpTime = new Date().getTime() - this.timeStamp;
					}
				
					tmpTime = new Date().getTime() - this.timeStamp;
					// Convert tmpTime to seconds multiply with warming constant
					if ((float) tmpTime / 1000 * this.warming >= 1
					       ) {
						this.coreTemp += (float) tmpTime / 1000 * this.warming;
						this.timeStamp = new Date().getTime();
					}

				}

			} else {
			
				Thread.currentThread().interrupt();
			}

		}
	}

	private boolean isMinTempAddReached() {
		
	    return ((float) tmpTime / 1000 * this.warming < 1);
    }

	public boolean isOverheated() {
		if (this.coreTemp >= this.maxTemp) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void setHeat(float heat) {
		this.coreTemp = heat;

	}

	@Override
	public float getHeat() {
		return this.coreTemp;
	}

	@Override
	public String toString() {
		
		return "Core Temperature: " + Math.round(this.coreTemp)
		        + " [ is aktive  " + !this.isShutdown() + " ]";
	}


	private boolean isWaterPumped() {
		

		if (tmp.equals(this.recuperator.getWaterPackage())) {	
			return false;
		} else {
			tmp = this.recuperator.getWaterPackage();
			return true;
		}
	}
	
	protected void coolingComponent() {

		if (this.isWaterPumped()) {
			this.recuperator.coolComponent();
		}
	}

}
