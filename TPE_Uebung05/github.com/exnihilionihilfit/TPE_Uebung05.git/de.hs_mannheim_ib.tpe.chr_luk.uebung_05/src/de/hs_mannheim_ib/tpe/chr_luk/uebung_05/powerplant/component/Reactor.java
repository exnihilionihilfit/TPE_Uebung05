package de.hs_mannheim_ib.tpe.chr_luk.uebung_05.powerplant.component;

import java.util.Date;

import de.hs_mannheim_ib.tpe.chr_luk.uebung_05.powerplant.component.cooling.CoolingCircuit;
import de.hs_mannheim_ib.tpe.chr_luk.uebung_05.powerplant.component.cooling.WaterPackage;
import de.hs_mannheim_ib.tpe.chr_luk.uebung_05.powerplant.control.ErrorMessage;
/**
 * the reactor produce heat 
 * get cooled by recuperator which is used internally
 * if the cooling circuit has moved the cooling water
 * is shutting down if over heated 
 * @author 
 *
 */
public class Reactor extends Component implements Runnable, Heatable {

	private int warming; // C° per second
	private float maxTemp; // C°
	private float coreTemp; // C°
	private long timeStamp; // time
	private long tmpTime = 0;
	private CoolingCircuit cc;
	private Recuperator recuperator;
	private WaterPackage tmp;
	private boolean shutdown;

	public Reactor(CoolingCircuit cc) {
		this.warming = 43;
		this.maxTemp = 2878;
		this.coreTemp = 10;
		this.shutdown = false;
		this.timeStamp = new Date().getTime();
		this.cc = cc;
		this.recuperator = new Recuperator(this, cc, 0); // connected with
		                                                 // coolingCircuit at 0
		tmp = this.recuperator.getWaterPackage();
	}
	
	/**
	 * heating of reactor in 1°C steps 
	 * get by time diverence
	 * synchronized over cooling circuit
	 * thread waits each thread call 
	 */

	@Override
	public void run() {

		while (!Thread.currentThread().isInterrupted()) {

			if (!this.shutdown && !isOverheated()) {

				synchronized (cc) {

					while (this.isMinTempAddReached()) {

						try {
							cc.wait();
						} catch (InterruptedException e) {
							ErrorMessage.addToList(e);
						}
						tmpTime = new Date().getTime() - this.timeStamp;
					}

					tmpTime = new Date().getTime() - this.timeStamp;
					// check if heating is equal or higher then 1 C°
					// Convert tmpTime to seconds multiply with warming constant
					if ((float) tmpTime / 1000 * this.warming >= 1) {
						this.coreTemp += (float) tmpTime / 1000 * this.warming;
						this.timeStamp = new Date().getTime();
					}

					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						ErrorMessage.addToList(e);
					}

				}

			} else {
				Thread.currentThread().interrupt();
			}

		}
	}

	/**
	 * checks if heating is over 1 C°
	 * 
	 * @return true if temperatur is above 1 C° false if not
	 */
	private boolean isMinTempAddReached() {
		// converte the time with div. to seconds		
		return ((float) tmpTime / 1000 * this.warming < 1);
	}

	/**
	 * 
	 * @return true if the temperature is over given value false if nominal
	 */
	public boolean isOverheated() {
		if (this.coreTemp >= this.maxTemp) {
			return true;
		} else {
			return false;
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
		String output = "";

		if (this.isOverheated()) {
			output += " [OVERHEATED] ";
		}
		if (this.isShutdown()) {
			output += " [reactor offline]\n ";
		}
		output += "Core Temperature: " + Math.round(this.coreTemp);
		return output;
	}

	/**
	 * checks if the water in the cooling circuit was moved by comparing the
	 * actual water package with the last one who was saved last thread call
	 */
	private boolean isWaterPumped() {

		if (tmp.equals(this.recuperator.getWaterPackage())) {
			return false;
		} else {
			tmp = this.recuperator.getWaterPackage();
			return true;
		}
	}

	/**
	 * call the recuperator which middle the temperatur betwen the actual
	 * water-package and the component
	 */
	public void coolingComponent() {

		if (this.isWaterPumped()) {
			this.recuperator.coolComponent();
		}
	}

}
