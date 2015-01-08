package de.hs_mannheim_ib.tpe.chr_luk.uebung_05;

import java.util.Date;

public class Reactor extends Component implements Runnable, Heatable {

	int warming; // C° per second
	float maxTemp; // C°
	float coreTemp; // C°
	private long timeStamp; // time
	private long tmpTime = 0;
	private CoolingCircuit cc;

	public Reactor(CoolingCircuit cc) {
		this.warming = 42;
		this.maxTemp = 2878;
		this.coreTemp = 10;
		this.timeStamp = new Date().getTime();
		this.cc = cc;
	}

	@Override
	public void run() {

		while (true) {

			if (this.coreTemp >= this.maxTemp) {
				
				try {
					Thread.interrupted();
	                throw new Exception("your reactor is a bit overheated, we are sorry that we have shut down the reactor and moderate the core a bit more. Have a nice day.");
                } catch (Exception e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
                }
				break;
			}
		
		//	System.out.println("reactor");
			tmpTime = new Date().getTime() - this.timeStamp;
		
			// Convert tmpTime to seconds multiply with warming constant
			if ((float) tmpTime / 1000 * this.warming >= 1) {
		
				this.coreTemp +=  (float) tmpTime / 1000 * this.warming;
			
				this.timeStamp = new Date().getTime();
				
		

			}

			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
		return "Core Temperature: " + Math.round(this.coreTemp);
	}

}
