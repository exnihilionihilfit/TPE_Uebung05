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
	private boolean shutdown;

	public Pump(float pumpPower, CoolingCircuit cc) {
		this.pumpPower = pumpPower;
		this.cc = cc;
		this.timeStamp = new Date();
		// pump actions per second (div. 1000 to converte to seconds)
		this.pumpInterval = 1000 / this.pumpPower;
		this.shutdown = false;
	}

	/**
	 * pick the first element from the list and add it to the same list ( as
	 * last element ) to "pump" the water
	 */
	public void pumping() {
		tmpPackage = cc.getWaterPackages().remove(0);
		cc.getWaterPackages().add(tmpPackage);
	}

	/**
	 * @param pumpPower
	 *            the pumpPower to set
	 */
	public void setPumpPower(double pumpPower) {

		this.pumpPower = pumpPower;
	}

	/**
	 * circutling the water packages in the cooling circuit synchronized over
	 * cooling circuit thread waits each thread call
	 */

	@Override
	public void run() {

		while (!Thread.currentThread().isInterrupted()) {

			if (!this.shutdown) {
				synchronized (cc) {
					while (new Date().getTime() - this.timeStamp.getTime() < this.pumpInterval) {
						try {
							cc.wait();
						} catch (InterruptedException e) {
							ErrorMessage.addToList(e);
						}
					}
					
					this.timeStamp = new Date();
					this.pumping();

					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						ErrorMessage.addToList(e);
					}

				}
			} else {
				Thread.currentThread().interrupt();
			}
		}
	}
}
