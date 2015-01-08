package de.hs_mannheim_ib.tpe.chr_luk.uebung_05;

public class River implements Heatable {

	 static final float waterTemperatur = 10; //C° in * 100
	private float  drainTemperature;

	public River() {
		this.drainTemperature = 10;
	}

	@Override
	public void setHeat(float heat) {
		this.drainTemperature = heat;

	}

	@Override
	public float getHeat() {
		return this.waterTemperatur;
	}
	
	@Override
	public String toString(){
		return "river drain temperature: "+Math.round(this.drainTemperature);
		
	}

}
