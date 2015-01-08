package de.hs_mannheim_ib.tpe.chr_luk.uebung_05;

public class WaterPackage implements Heatable{
	
	int liter;
	float temperature; // C° * 100
	int number;
	
	public WaterPackage(int liter,float tempRiver,int number){
		this.liter = liter;
		this.temperature = tempRiver;
		this.number = number;
	}

	@Override
    public void setHeat(float heat) {
	   this.temperature = heat;	    
    }

	@Override
    public float getHeat() {
		return temperature;
    }

}
