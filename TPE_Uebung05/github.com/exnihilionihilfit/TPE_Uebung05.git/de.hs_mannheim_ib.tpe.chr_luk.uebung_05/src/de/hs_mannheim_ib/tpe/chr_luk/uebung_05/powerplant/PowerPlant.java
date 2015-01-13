package de.hs_mannheim_ib.tpe.chr_luk.uebung_05.powerplant;

/**
 * The power plant starts and controls the components
 * in 3 threads. this are joined threads and each thread  
 */

import java.util.ArrayList;
import java.util.Date;

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
	private long runTime;	
	private float pumpInterval;
	private ArrayList<Thread> allThreads;
	private ErrorMessage msg;

	/**
	 * Init. all components and starts 3 Threads
	 *
	 * @param runTime
	 *            of the power plant
	 * @param pumpInterval
	 *            of the cooling circuit
	 */
	public PowerPlant(int runTime, float pumpInterval) {

		this.runTime = runTime; // running time for powerplant (in mil. seconds)
		this.pumpInterval = pumpInterval;

		// intitialize all powerplant components
		initComponents();

		// activet all components

		startReactor();
		startPump();
		startOutput();
		startPowerPlant();

	}

	/**
	 * running the main loop which controls the 3 threads each thread is
	 * joined() and will be waked up by sync. coolingCiruit. After time is up or
	 * the reactor have a melt down the power plant will shutdown
	 * 
	 */
	private void startPowerPlant() {
		Date startTime = new Date();

		long timePast = 0;

		while (!Thread.currentThread().isInterrupted()) {

			// Wake up all Threads
			synchronized (coolingCircuit) {
				coolingCircuit.notifyAll();
			}

			timePast = new Date().getTime() - startTime.getTime();

			reactor.coolingComponent();
			river.coolingComponent();

			// give signal to terminate threads internally
			if (timePast > this.runTime || reactor.isOverheated()) {

				this.reactor.setShutdown(true);
				this.river.setShutdown(true);
				this.pump.setShutdown(true);
				this.output.setShutdown(true);

				System.out.println("Powerplant is offline");
				Thread.currentThread().interrupt();
			}

			else {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					ErrorMessage.addToList(e);
				}
			}
		}

	}

	/**
	 * init all components and save the threads in field allThreads to loop over
	 * there state to proper terminate them
	 */
	private void initComponents() {
		this.coolingCircuit = new CoolingCircuit( 1200, 100); // liter
		// in the
		// hole
		// cooling circuit and
		// liter each water-unit
		this.pump = new Pump(this.pumpInterval, coolingCircuit);
		this.reactor = new Reactor(coolingCircuit);
		this.river = new River(coolingCircuit);

		// add them to an array only for output
		Component[] components = new Component[2];

		components[0] = reactor;
		components[1] = river;

		// last parameter is update rate in mili. seconds
		this.output = new Output(coolingCircuit, components); 

		this.allThreads = new ArrayList<>();

		this.msg = new ErrorMessage();
	}

	/**
	 * the reactor wich produce energy and warms. is added to allThreads and
	 * joined which all other threads
	 */
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

	/**
	 * the pump which pumps the cooling water is added to allThreads and joined
	 * which all other threads
	 */
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

	/**
	 * output all information to the conole is added to allThreads and joined
	 * which all other threads
	 */
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
