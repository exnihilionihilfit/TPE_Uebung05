package de.hs_mannheim_ib.tpe.chr_luk.uebung_05;

import java.util.Date;

public class Output implements Runnable {

	private CoolingCircuit cc;
	private Component[] components;
	private Date timeStamp;

	public Output(CoolingCircuit cc, Component[] components) {
		this.cc = cc;
		this.components = components;
		this.timeStamp = new Date();
	}

	@Override
	public void run() {

		while (true) {
			synchronized (cc) {
				
				while(new Date().getTime() -this.timeStamp.getTime() < 500){
					try {
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

				cc.notifyAll();
				this.timeStamp = new Date();
				
				
			
			
			}
		}

	}
}
