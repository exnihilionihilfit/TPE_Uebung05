package de.hs_mannheim_ib.tpe.chr_luk.uebung_05.powerplant;

/**
 * The power plant starts and controls the components
 * in 3 threads. this are joined threads and each thread  
 */

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import de.hs_mannheim_ib.tpe.chr_luk.uebung_05.powerplant.component.Component;
import de.hs_mannheim_ib.tpe.chr_luk.uebung_05.powerplant.component.Pump;
import de.hs_mannheim_ib.tpe.chr_luk.uebung_05.powerplant.component.Reactor;
import de.hs_mannheim_ib.tpe.chr_luk.uebung_05.powerplant.component.River;
import de.hs_mannheim_ib.tpe.chr_luk.uebung_05.powerplant.component.cooling.CoolingCircuit;
import de.hs_mannheim_ib.tpe.chr_luk.uebung_05.powerplant.control.ErrorMessage;
import de.hs_mannheim_ib.tpe.chr_luk.uebung_05.powerplant.control.Output;

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
	private boolean shutdown = false;
	private float minTemp;
	private float pumpInterval;
	private ArrayList<Thread> allThreads;
	private ErrorMessage msg;

	public PowerPlant() {
		this.minTemp = 40; // minimal temperature which must reach for shutdown
		this.runTime = 20000; // running time for powerplant (in mil. seconds)
		this.pumpInterval = 1;

		// intitialize all powerplant components
		initComponents();

		// activet all components

		startReactor();
		startPump();
		startOutput();
		startPowerPlant();

	}

	private void startPowerPlant() {
		Date startTime = new Date();

		long timePast = 0;

		while (!Thread.currentThread().isInterrupted()) {

			timePast = new Date().getTime() - startTime.getTime();

			reactor.coolingComponent();
			river.coolingComponent();

			if (timePast > this.runTime || reactor.isOverheated()) {

				this.reactor.setShutdown(true);

				if (this.minTempReached()) {

					this.river.setShutdown(true);
					this.pump.setShutdown(true);
					this.output.setShutdown(true);
					this.shutdown = true;

				}
			}

			if (this.shutdown) {

				if (this.areAllShutdown()) {
					System.out.println("Powerplant is offline");
					Thread.currentThread().interrupt();

				}

			} else {

				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					ErrorMessage.addToList(e);
				}
			}
		}

	}

	private boolean areAllShutdown() {
		for (int i = 0; i < allThreads.size(); i++) {

			if (!allThreads.get(i).getState().equals(Thread.State.TERMINATED)) {

				return false;
			}
		}
		return true;
	}

	private void getThreadStates() {
		System.out.println(reactorThread.getState() + " "
		        + pumpThread.getState() + " " + outputThread.getState());
	}

	private void initComponents() {
		coolingCircuit = new CoolingCircuit(1200, 100); // liter in the hole
		                                                // cooling circuit and
		                                                // liter each water-unit
		pump = new Pump(this.pumpInterval, coolingCircuit);
		reactor = new Reactor(coolingCircuit);
		river = new River(coolingCircuit);

		// add them to an array only for output
		Component[] components = new Component[2];

		components[0] = reactor;
		components[1] = river;

		output = new Output(coolingCircuit, components);

		this.allThreads = new ArrayList<>();

		msg = new ErrorMessage();
	}

	private boolean minTempReached() {
		if (reactor.getHeat() < this.minTemp
		        && coolingCircuit.isNormTemperature()) {
			return true;
		} else {
			return false;
		}
	}

	private void startReactor() {
		reactorThread = new Thread(reactor);
		try {
			reactorThread.join();
		} catch (InterruptedException e) {
			ErrorMessage.addToList(e);
		}
		reactorThread.start();
		this.allThreads.add(reactorThread);

	}

	private void startPump() {
		pumpThread = new Thread(pump);
		try {
			pumpThread.join();
		} catch (InterruptedException e) {
			ErrorMessage.addToList(e);
		}
		pumpThread.start();
		this.allThreads.add(pumpThread);

	}

	private void startOutput() {
		outputThread = new Thread(output);
		try {
			outputThread.join();
		} catch (InterruptedException e) {
			ErrorMessage.addToList(e);
		}
		outputThread.start();
		this.allThreads.add(outputThread);

	}

}
