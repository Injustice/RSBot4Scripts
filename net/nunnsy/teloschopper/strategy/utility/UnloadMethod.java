package net.nunnsy.teloschopper.strategy.utility;

public enum UnloadMethod {

	DROP("Drop"),
	BONFIRE("Bonfire"),
	//SPLIT("Split"),
	//Fletch("Fletch"),
	BANK("Bank");
	
	
	String unloadText;
	
	UnloadMethod(String unloadText) {
		this.unloadText = unloadText;
	}
	
	public String getUnloadName() {
		return unloadText;
	}
}
