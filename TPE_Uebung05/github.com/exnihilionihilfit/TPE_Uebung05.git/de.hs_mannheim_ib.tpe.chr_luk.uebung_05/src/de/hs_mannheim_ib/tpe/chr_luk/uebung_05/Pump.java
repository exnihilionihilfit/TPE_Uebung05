package de.hs_mannheim_ib.tpe.chr_luk.uebung_05;

import java.util.Date;

public class Pump extends Component implements Runnable{

	// pump power in water per second
	private double pumpPower;
	private CoolingCircuit cc; // cooling circuit
	private WaterPackage tmpPackage;
	private Date timeStamp;

	public Pump(float pumpPower, CoolingCircuit cc) {
		this.pumpPower = pumpPower;
		this.cc = cc;
		this.timeStamp = new Date();
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

		while (true) {

	
			synchronized (cc) {
				
				while(cc.){
					
				}
				
				

				if (new Date().getTime() - this.timeStamp.getTime() > 1000/this.pumpPower) {

					this.pumping();

					this.cc.getRecupReactor().coolingWater(
					        this.cc.getWaterPackages().get(3));

					this.cc.getRecupRiver().coolingWater(
					        this.cc.getWaterPackages().get(9));

					this.timeStamp = new Date();

				}
				
				try {
	                Thread.sleep(10);
	            } catch (InterruptedException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }

	            
            }

		}
	}

}
