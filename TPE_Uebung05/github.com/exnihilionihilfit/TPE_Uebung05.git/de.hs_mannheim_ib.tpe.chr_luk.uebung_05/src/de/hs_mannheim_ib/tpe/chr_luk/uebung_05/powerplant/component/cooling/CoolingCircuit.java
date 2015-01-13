package de.hs_mannheim_ib.tpe.chr_luk.uebung_05.powerplant.component.cooling;

import java.util.ArrayList;

public class CoolingCircuit {

	private final float normTemperature ;
	private volatile ArrayList<WaterPackage> waterPackages;
	private int waterVolume;
	private int waterPerPackage;

	public CoolingCircuit(int normTemp ,int waterVolume, int waterPerPackage) {
		this.normTemperature = normTemp;
		this.waterPackages = new ArrayList<>();
		this.waterVolume = waterVolume;
		this.waterPerPackage = waterPerPackage;

		this.fillCoolingCircuit();

	}

	/**
	 * @return the waterPackages
	 */
	public ArrayList<WaterPackage> getWaterPackages() {
		return waterPackages;
	}

	/**
	 * @param waterPackages
	 *            the waterPackages to set
	 */
	public void setWaterPackages(ArrayList<WaterPackage> waterPackages) {
		this.waterPackages = waterPackages;
	}

	private void fillCoolingCircuit() {
		int numPackage = this.waterVolume / this.waterPerPackage;

		for (int i = 0; i < numPackage; i++) {
			this.waterPackages
			        .add(new WaterPackage(this.waterPerPackage, 10, i));
		}
	}

	public boolean isNormTemperature() {

		synchronized (this) {
			for (WaterPackage pack : waterPackages) {

				if (pack.getHeat() > this.normTemperature) {
					return false;
				}

			}

			return true;
		}
	}
	

}
