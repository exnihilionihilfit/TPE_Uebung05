package de.hs_mannheim_ib.tpe.chr_luk.uebung_05;

public class Output implements Runnable {

	private CoolingCircuit cc;

	public Output(CoolingCircuit cc) {
		this.cc = cc;
	}

	@Override
	public void run() {

		while (true) {
			System.out.println(cc.toString());
			String tmp="";
			for(WaterPackage pack:cc.getWaterPackages()){
				tmp+=" ["+pack.number+"] "+ Math.round(pack.temperature);
			}
			System.out.println(tmp);

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
}
