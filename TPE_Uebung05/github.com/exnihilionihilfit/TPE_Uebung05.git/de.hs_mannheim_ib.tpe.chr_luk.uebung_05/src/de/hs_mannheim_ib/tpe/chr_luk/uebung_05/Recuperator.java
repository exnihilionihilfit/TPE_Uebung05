package de.hs_mannheim_ib.tpe.chr_luk.uebung_05;

public class Recuperator {
	
	private Heatable component;
	private int posInCircuit;
	private CoolingCircuit cc;
	private WaterPackage coolingWater;

	public Recuperator(Heatable component,CoolingCircuit cc,int posInCircuit){
		
		this.component    = component;
		this.posInCircuit = posInCircuit;
		this.cc = cc;
	}

	/**
	 * @return the posInCircuit
	 */
	public int getPosInCircuit() {
		return posInCircuit;
	}
	public WaterPackage getWaterPackage(){
		return this.cc.getWaterPackages().get(posInCircuit);
	}

	/**
	 * @param posInCircuit the posInCircuit to set
	 */
	public void setPosInCircuit(int posInCircuit) {
		this.posInCircuit = posInCircuit;
	}

	public void coolComponent() {
		
		coolingWater = cc.getWaterPackages().get(posInCircuit);
		float tmpTemp = component.getHeat() + coolingWater.getHeat();
		tmpTemp /= 2;
	
		component.setHeat(tmpTemp);
		coolingWater.setHeat(tmpTemp);
		
	}
	


}
