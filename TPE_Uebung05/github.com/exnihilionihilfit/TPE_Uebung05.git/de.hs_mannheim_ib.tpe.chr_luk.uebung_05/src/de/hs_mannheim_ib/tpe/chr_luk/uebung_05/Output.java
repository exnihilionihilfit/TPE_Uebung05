package de.hs_mannheim_ib.tpe.chr_luk.uebung_05;

import java.util.Date;

public class Output implements Runnable {

	private CoolingCircuit cc;
	private Component[] components;
	private Date timeStamp;
	private volatile boolean shutdown;

	public Output(CoolingCircuit cc, Component[] components) {
		this.cc = cc;
		this.components = components;
		this.timeStamp = new Date();
	}

	@Override
	public void run() {

		while (!Thread.currentThread().isInterrupted()) {
		
			if (!this.isShutdown()) {
				synchronized (cc) {

					while (new Date().getTime() - this.timeStamp.getTime() < 1000) {
					
						try {
							cc.notifyAll();
							cc.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

					System.out.println("");
					for (Component comp : components) {
						System.out.print(comp.toString());
					}
					System.out.println("");
					String tmp = "";
					for (WaterPackage pack : cc.getWaterPackages()) {
						tmp += " [" + pack.number + "] "
						        + Math.round(pack.temperature);
					}
					System.out.println(tmp);

					this.timeStamp = new Date();
					cc.notifyAll();
				
				}
			}else{
				Thread.currentThread().interrupt();
			}
		}

	}

	private boolean isShutdown() {

		return this.shutdown;
	}

	public void setShutdown(boolean b) {
		this.shutdown = b;

	}
}
