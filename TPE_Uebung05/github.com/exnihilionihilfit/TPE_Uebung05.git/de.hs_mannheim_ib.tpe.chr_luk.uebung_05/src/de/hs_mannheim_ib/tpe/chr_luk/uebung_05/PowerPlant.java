package de.hs_mannheim_ib.tpe.chr_luk.uebung_05;

import java.util.Date;

public class PowerPlant {

	private Reactor reactor;
	private CoolingCircuit coolingCircuit;
	private Pump pump;
	private River river;
	private Output output;
	private Thread pumpThread;
	private Thread reactorThread;
	private Thread outputThread;
	private Thread riverThread;
	private Date timeStamp = new Date();
	private long runTime;

	public PowerPlant() throws InterruptedException {

		coolingCircuit = new CoolingCircuit(1200, 100);
		pump = new Pump(1f, coolingCircuit);
		reactor = new Reactor(coolingCircuit);
		river = new River(coolingCircuit);

		Component[] components = new Component[2];

		components[0] = reactor;
		components[1] = river;

		output = new Output(coolingCircuit, components);

		riverThread = new Thread(river);
		riverThread.join();
		riverThread.start();

		reactorThread = new Thread(reactor);
		reactorThread.join();
		reactorThread.start();

		pumpThread = new Thread(pump);
		pumpThread.join();
		pumpThread.start();

		outputThread = new Thread(output);
		outputThread.join();
		outputThread.start();

		Date startTime = new Date();
		this.runTime = 20000; // running 20 seconds (in mil. seconds)
		long timePast = 0;

		while (true) {
			timePast = new Date().getTime() - startTime.getTime();

			reactor.coolingComponent();
			river.coolingComponent();

			if (reactor.isOverheated() || timePast > this.runTime) {
				
			

				reactor.setShutdown(true);

				if (reactor.getHeat() < 20
				        && coolingCircuit.isNormTemperature()) {

					river.setShutdown(true);
					pump.setShutdown(true);

					if (reactorThread.getState().equals("RUNNABLE")) {
						reactorThread.interrupt();
					}
					if (riverThread.getState().equals("RUNNABLE")) {
						riverThread.interrupt();
					}
					if (pumpThread.getState().equals("RUNNABLE")) {
						pumpThread.interrupt();
					}
				}
			}

			Thread.sleep(10);

		}

	}

}
