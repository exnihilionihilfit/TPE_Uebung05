package de.hs_mannheim_ib.tpe.chr_luk.uebung_05.junitTest;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;

import de.hs_mannheim_ib.tpe.chr_luk.uebung_05.powerplant.PowerPlant;
import de.hs_mannheim_ib.tpe.chr_luk.uebung_05.powerplant.component.Heatable;
import de.hs_mannheim_ib.tpe.chr_luk.uebung_05.powerplant.component.Reactor;
import de.hs_mannheim_ib.tpe.chr_luk.uebung_05.powerplant.component.Recuperator;
import de.hs_mannheim_ib.tpe.chr_luk.uebung_05.powerplant.component.cooling.CoolingCircuit;
import de.hs_mannheim_ib.tpe.chr_luk.uebung_05.powerplant.control.ErrorMessage;

public class JUnitTest {

	@Test
	public void testPowerPlant() {

		PowerPlant pp = new PowerPlant(20000, 1);

		assertEquals(pp.getClass(), PowerPlant.class);

	}

	@Test
	public void testComponents() {

		CoolingCircuit cc = new CoolingCircuit(12000, 1000);
		Heatable component = new Reactor(cc);
		int posInCircuit = 0; // on wich part component is connected in the cc
		Recuperator rcup = new Recuperator(component, cc, posInCircuit);

		assertEquals(rcup.getComponent(), component);
		// test recuperator
		assertEquals(rcup.getComponent().getHeat(), 10, 0.1);

		rcup.getComponent().setHeat(20);
		rcup.coolComponent();

		assertEquals(rcup.getComponent().getHeat(), 15, 0.1);
	}

	@After
	public void testException()  {
		ErrorMessage eMsg = new ErrorMessage();
		CoolingCircuit cc = new CoolingCircuit(12000, 1000);
		Reactor reactor = new Reactor(cc);
		
		Thread t = new Thread(reactor);
		t.start();
		
		assertEquals(t.getState(), Thread.State.RUNNABLE);
		
		
		
	
	
	}

}
