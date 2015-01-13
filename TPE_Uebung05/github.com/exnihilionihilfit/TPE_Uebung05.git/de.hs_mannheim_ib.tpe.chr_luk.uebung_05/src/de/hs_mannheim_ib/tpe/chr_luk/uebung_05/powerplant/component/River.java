package de.hs_mannheim_ib.tpe.chr_luk.uebung_05.powerplant.component;

import java.util.Date;

import de.hs_mannheim_ib.tpe.chr_luk.uebung_05.powerplant.component.cooling.CoolingCircuit;
import de.hs_mannheim_ib.tpe.chr_luk.uebung_05.powerplant.component.cooling.WaterPackage;

public class River extends Component implements Heatable {

	private float waterTemperatur; // C°
	private float drainTemperature; // C° back to the river
	private Recuperator recuperator; // cooling the core
	private WaterPackage tmp; // temp storage for water packages

	public River(CoolingCircuit cc) {
		this.drainTemperature = 10; // filled with water from river
		this.waterTemperatur = 10; // starting temperature
		// max. distance in ciruit from 0
		this.recuperator = new Recuperator(this, cc, cc.getWaterPackages()
		        .size() / 2);
		tmp = this.recuperator.getWaterPackage();
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
	public String toString() {
		return " Rhein river drain temperature: "
		        + Math.round(this.drainTemperature);
	}

	/**
	 * checks if the water in the cooling circuit was moved by comparing the
	 * actual water package with the last one who was saved last thread call *
	 * call the recuperator which middle the temperatur betwen the actual
	 * water-package and the component
	 */
	public void coolingComponent() {
		if (!tmp.equals(this.recuperator.getWaterPackage())) {
			this.recuperator.coolComponent();
			tmp = this.recuperator.getWaterPackage();
		}
	}

}
