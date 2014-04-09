package net.nunnsy.teloschopper.utility.state;

public enum State {

	STARTING("Starting"),
	WAITING("Waiting"),
	CHOPPING("Chopping"),
	UNLOADING("Unloading"),
	PICKING("Picking"),
	WALKING("Walking"),
	ANTIBAN("Antiban");
	
	String stateText;
	
	State(String stateText) {
		this.stateText = stateText;
	}
	
	public String getStateName() {
		return stateText;
	}
}
