package de.hs_mannheim_ib.tpe.chr_luk.uebung_05.powerplant.component.cooling;

import java.util.ArrayList;

/**
 * mainly has a waterPackages List which has a number of so 
 * called water packages each one could exchange heat with a component
 * its "filled" by constructor, cant be changed after power plant runs
 * @author 
 *
 */

public class CoolingCircuit {


	private volatile ArrayList<WaterPackage> waterPackages;
	private int waterVolume;
	private int waterPerPackage;

	public CoolingCircuit(int waterVolume, int waterPerPackage) {
	
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
	/**
	 * fills the cooling circuit with water packages
	 * by given values for volume per package 
	 */

	private void fillCoolingCircuit() {
		// number of packages is the whole water volume div. to number of packages
		int numPackage = this.waterVolume / this.waterPerPackage;

		for (int i = 0; i < numPackage; i++) {
			this.waterPackages
			        .add(new WaterPackage(this.waterPerPackage, 10,i));
		}
	}

}
