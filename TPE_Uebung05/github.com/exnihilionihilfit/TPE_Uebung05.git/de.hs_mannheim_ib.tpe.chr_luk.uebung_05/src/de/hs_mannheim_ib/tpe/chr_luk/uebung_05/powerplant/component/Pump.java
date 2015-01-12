package de.hs_mannheim_ib.tpe.chr_luk.uebung_05.powerplant.component;

import java.util.Date;

import de.hs_mannheim_ib.tpe.chr_luk.uebung_05.powerplant.component.cooling.CoolingCircuit;
import de.hs_mannheim_ib.tpe.chr_luk.uebung_05.powerplant.component.cooling.WaterPackage;
import de.hs_mannheim_ib.tpe.chr_luk.uebung_05.powerplant.control.ErrorMessage;

public class Pump extends Component implements Runnable {

	// pump power in water per second
	private double pumpPower;
	private CoolingCircuit cc; // cooling circuit
	private WaterPackage tmpPackage;
	private Date timeStamp;
	private double pumpInterval;
	private volatile boolean shutdown;

	public Pump(float pumpPower, CoolingCircuit cc) {
		this.pumpPower = pumpPower;
		this.cc = cc;
		this.timeStamp = new Date();
		this.pumpInterval = 1000 / this.pumpPower;
	}

	public void pumping() {

		tmpPackage = cc.getWaterPackages().remove(0);
		cc.getWaterPackages().add(tmpPackage);

	}

	/**
	 * @return the pumpPower
	 */
	public double getPumpPower() {

		return pumpPower;
	}

	/**
	 * @param pumpPower
	 *            the pumpPower to set
	 */
	public void setPumpPower(double pumpPower) {

		this.pumpPower = pumpPower;
	}

	@Override
	public void run() {

		while (!Thread.currentThread().isInterrupted()) {
		
			if (!this.isShutdown()) {
				synchronized (cc) {

					while (new Date().getTime() - this.timeStamp.getTime() < this.pumpInterval) {
					
						try {
							cc.notifyAll();
							cc.wait();
						} catch (InterruptedException e) {
							ErrorMessage.addToList(e);
						}

					}

					// System.out.println("pump is working");

					this.timeStamp = new Date();
					this.pumping();
					cc.notifyAll();
				
					try {
			            Thread.sleep(10);
		            } catch (InterruptedException e) {
		            	ErrorMessage.addToList(e);
		            }
				}
			}else{
				Thread.currentThread().interrupt();
			}
			
			
		}
	}

}
