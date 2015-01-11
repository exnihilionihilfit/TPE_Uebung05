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

}
