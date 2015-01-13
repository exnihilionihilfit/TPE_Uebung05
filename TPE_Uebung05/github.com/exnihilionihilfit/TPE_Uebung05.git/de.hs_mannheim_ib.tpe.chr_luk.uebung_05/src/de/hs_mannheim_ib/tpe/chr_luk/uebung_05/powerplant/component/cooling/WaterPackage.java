package de.hs_mannheim_ib.tpe.chr_luk.uebung_05.powerplant.component.cooling;

import de.hs_mannheim_ib.tpe.chr_luk.uebung_05.powerplant.component.Heatable;
/**
 * Represents the water inside the cooling circuit
 * which can exchange heat (interface heatable)
 * @author
 *
 */

public class WaterPackage implements Heatable{

	private int liter; // of water
	private float temperature; // C° * 100
	private int number;
	
	
	public WaterPackage(int liter,float tempRiver,int number){
		this.liter = liter;
		this.setTemperature(tempRiver);
		this.number = number;
	
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
    @Override
    public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + liter;
	    result = prime * result + number;
	    result = prime * result + Float.floatToIntBits(temperature);
	    return result;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
    @Override
    public boolean equals(Object obj) {
	    if (this == obj)
		    return true;
	    if (obj == null)
		    return false;
	    if (getClass() != obj.getClass())
		    return false;
	    WaterPackage other = (WaterPackage) obj;
	    if (liter != other.liter)
		    return false;
	    if (number != other.number)
		    return false;
	    if (Float.floatToIntBits(temperature) != Float
	            .floatToIntBits(other.temperature))
		    return false;
	    return true;
    }

	@Override
    public void setHeat(float heat) {
		
	   this.setTemperature(heat);	    
    }

	@Override
    public float getHeat() {
		return getTemperature();
    }



	

	public float getTemperature() {
	    return temperature;
    }

	public void setTemperature(float temperature) {
	    this.temperature = temperature;
    }

}
