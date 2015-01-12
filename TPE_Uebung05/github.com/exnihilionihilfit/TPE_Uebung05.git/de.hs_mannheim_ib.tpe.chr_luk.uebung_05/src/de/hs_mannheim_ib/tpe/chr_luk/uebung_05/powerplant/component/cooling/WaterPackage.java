package de.hs_mannheim_ib.tpe.chr_luk.uebung_05.powerplant.component.cooling;

import de.hs_mannheim_ib.tpe.chr_luk.uebung_05.powerplant.component.Heatable;

public class WaterPackage implements Heatable{

	int liter;
	private float temperature; // C° * 100
	private int number;
	
	public WaterPackage(int liter,float tempRiver,int number){
		this.liter = liter;
		this.setTemperature(tempRiver);
		this.setNumber(number);
	}

	@Override
    public void setHeat(float heat) {
		
	   this.setTemperature(heat);	    
    }

	@Override
    public float getHeat() {
		return getTemperature();
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
	    if (getNumber() != other.getNumber())
		    return false;
	    if (Float.floatToIntBits(getTemperature()) != Float
	            .floatToIntBits(other.getTemperature()))
		    return false;
	    return true;
    }
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
    @Override
    public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + liter;
	    result = prime * result + getNumber();
	    result = prime * result + Float.floatToIntBits(getTemperature());
	    return result;
    }

	public int getNumber() {
	    return number;
    }

	public void setNumber(int number) {
	    this.number = number;
    }

	public float getTemperature() {
	    return temperature;
    }

	public void setTemperature(float temperature) {
	    this.temperature = temperature;
    }

}
