package de.hs_mannheim_ib.tpe.chr_luk.uebung_05;

public class PowerPlant {

	private Reactor reactor;
	private CoolingCircuit coolingCircuit;
	private Pump pump;
	private River river;
	private Output output;
	private Thread pumpThread;
	private Thread reactorThread;
	private Thread outputThread;


	public PowerPlant() throws InterruptedException {
		river = new River();
		
		coolingCircuit = new CoolingCircuit(1200, 100, river, reactor);
		pump = new Pump(0f, coolingCircuit);
		reactor = new Reactor(coolingCircuit);
		output = new Output(coolingCircuit);

		reactorThread = new Thread(reactor);
		
		reactorThread.start();
		
		pumpThread = new Thread(pump);
		
		pumpThread.start();
		
		outputThread = new Thread(output);
		
		outputThread.start();
		
		
		while (true) {
			
		//System.out.println(reactorThread.getState()+" "+pumpThread.getState());
		
		}

	}


}
