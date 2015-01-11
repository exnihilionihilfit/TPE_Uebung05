package de.hs_mannheim_ib.tpe.chr_luk.uebung_05;

import java.util.Date;

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

		while (!Thread.interrupted()) {

			if (!this.isShutdown()) {

				synchronized (cc) {
			

					// System.out.println("pump is working");

					this.timeStamp = new Date();
					this.pumping();
					cc.notifyAll();
					while (new Date().getTime() - this.timeStamp.getTime() < this.pumpInterval) {

						try {
							cc.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

				}

			}else{
				synchronized (cc) {
					cc.notifyAll();
					
				}
			}
		}
	}

}
