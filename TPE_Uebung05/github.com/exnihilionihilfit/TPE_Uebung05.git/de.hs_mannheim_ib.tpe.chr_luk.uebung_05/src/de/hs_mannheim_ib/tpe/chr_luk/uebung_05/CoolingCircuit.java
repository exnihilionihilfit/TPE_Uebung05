package de.hs_mannheim_ib.tpe.chr_luk.uebung_05;


import java.util.ArrayList;

public class CoolingCircuit {

	private ArrayList<WaterPackage> waterPackages;
	private int waterVolume;
	private int waterPerPackage;
	private River river;

	private Recuperator recupReactor;
	private Recuperator recupRiver;
	private Reactor reactor;

	public CoolingCircuit(int waterVolume, int waterPerPackage, River river,Reactor reactor) {
		this.waterPackages = new ArrayList<>();
		this.waterVolume = waterVolume;
		this.waterPerPackage = waterPerPackage;
		this.river = river;
		this.reactor= reactor;

		this.recupReactor = new Recuperator(river);
		this.recupRiver   = new Recuperator(reactor);
		
		this.fillCoolingCircuit();
	
	}

	/**
	 * @return the river
	 */
	public River getRiver() {
		return river;
	}

	/**
	 * @return the recupReactor
	 */
	public Recuperator getRecupReactor() {
		return recupReactor;
	}

	/**
	 * @return the recupRiver
	 */
	public Recuperator getRecupRiver() {
		return recupRiver;
	}

	/**
	 * @return the waterPackages
	 */
	public ArrayList<WaterPackage> getWaterPackages() {
		return waterPackages;
	}

	/**
	 * @param waterPackages the waterPackages to set
	 */
	public void setWaterPackages(ArrayList<WaterPackage> waterPackages) {
		this.waterPackages = waterPackages;
	}

	private void fillCoolingCircuit() {
		int numPackage = this.waterVolume / this.waterPerPackage;

		for (int i = 0; i < numPackage; i++) {
			this.waterPackages.add(new WaterPackage(this.waterPerPackage,
			        this.river.getHeat(),i));
		}
	}
	
	@Override
	public String toString(){
		return ""+this.reactor.toString()+" "+this.river.toString();
	}
}
