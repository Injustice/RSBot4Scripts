package net.nunnsy.teloschopper.utility.game;

public class Controls {

	private static boolean mouseUsed = false;
	private static boolean cameraUsed = false;
	
	public static void setMouseUsed(boolean b) {
		mouseUsed = b;
	}
	
	public static void setCameraUsed(boolean b) {
		cameraUsed = b;
	}
	
	public static boolean getMouseUse() {
		return mouseUsed;
	}
	
	public static boolean getCameraUse() {
		return cameraUsed;
	}
}
