package de.hs_mannheim_ib.tpe.chr_luk.uebung_05.junitTest;

import static org.junit.Assert.*;

import org.junit.Test;

import de.hs_mannheim_ib.tpe.chr_luk.uebung_05.powerplant.PowerPlant;
import de.hs_mannheim_ib.tpe.chr_luk.uebung_05.powerplant.component.Heatable;
import de.hs_mannheim_ib.tpe.chr_luk.uebung_05.powerplant.component.Reactor;
import de.hs_mannheim_ib.tpe.chr_luk.uebung_05.powerplant.component.Recuperator;
import de.hs_mannheim_ib.tpe.chr_luk.uebung_05.powerplant.component.cooling.CoolingCircuit;

public class JUnitTest {

	@Test
	public void testPowerPlant() {
	
		 PowerPlant pp =   new PowerPlant(20000, 1);
		

	     
		 
		 assertEquals( pp.getClass(), PowerPlant.class);
	 
	

	}
	@Test
	public void testComponents(){
		
		 CoolingCircuit cc = new CoolingCircuit(12000, 1000);
		 Heatable component = new Reactor(cc);
		 int posInCircuit = 0; 
		 Recuperator rcup = new Recuperator(component, cc, posInCircuit);
	}

}
