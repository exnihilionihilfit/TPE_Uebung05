package de.hs_mannheim_ib.tpe.chr_luk.uebung_05;

public class Recuperator {
	
	private Heatable component;

	public Recuperator(Heatable component){
		
		this.component = component;
	}

	public void coolingWater( Heatable coolingWater) {

		float tmpTemp = component.getHeat() + coolingWater.getHeat();
		tmpTemp /= 2;
	
		component.setHeat(tmpTemp);
		coolingWater.setHeat( tmpTemp);
		
	}
	


}
