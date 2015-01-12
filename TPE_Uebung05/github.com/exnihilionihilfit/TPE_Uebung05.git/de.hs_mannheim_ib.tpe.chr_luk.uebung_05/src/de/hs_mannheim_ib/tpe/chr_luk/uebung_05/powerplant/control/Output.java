package de.hs_mannheim_ib.tpe.chr_luk.uebung_05.powerplant.control;

import java.util.Date;

import de.hs_mannheim_ib.tpe.chr_luk.uebung_05.powerplant.component.Component;
import de.hs_mannheim_ib.tpe.chr_luk.uebung_05.powerplant.component.cooling.CoolingCircuit;
import de.hs_mannheim_ib.tpe.chr_luk.uebung_05.powerplant.component.cooling.WaterPackage;

public class Output implements Runnable {

	private CoolingCircuit cc;
	private Component[] components;
	private Date timeStamp;
	private volatile boolean shutdown;
	private final long timeWait;

	public Output(CoolingCircuit cc, Component[] components) {
		this.cc = cc;
		this.components = components;
		this.timeStamp = new Date();
		this.timeWait = 1000;
	}

	@Override
	public void run() {

		while (!Thread.currentThread().isInterrupted()) {
		
			if (!this.isShutdown()) {
				synchronized (cc) {

					while (this.getTimeDiv() < this.timeWait) {
					
						try {
							cc.notifyAll();
							cc.wait();
						} catch (InterruptedException e) {
							ErrorMessage.addToList(e);
						}

					}

					System.out.println("");
					for (Component comp : components) {
						System.out.print(comp.toString());
					}
					System.out.println("");
					String tmp = "";
					for (WaterPackage pack : cc.getWaterPackages()) {
						tmp += " [" + pack.getNumber() + "] "
						        + Math.round(pack.getTemperature());
					}
					System.out.println(tmp);

					this.timeStamp = new Date();
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

	private long getTimeDiv() {
	  
	    return (new Date().getTime() - this.timeStamp.getTime()) ;
    }

	private boolean isShutdown() {

		return this.shutdown;
	}

	public void setShutdown(boolean b) {
		this.shutdown = b;

	}
}
