package net.nunnsy.teloschopper.strategy.utility;


public enum TreeType {
	NORMAL("Normal", 1511, new int[] {1383, 1282, 1286, 1289, 38787, 38784, 38782, 38783, 38760, 38785, 38786, 38788, 61191, 61192, 61193, 47600, 47598}, 25),
	EVERGREEN("Evergreen", 1511, new int[] {54778, 54787}, 25),
	ALCHY("Alchy", 2862, new int[] {69554, 69556}, 25),
	OAK("Oak", 1521, new int[] {38732, 38731}, 37.5),
	WILLOW("Willow", 1519, new int[] {58006, 38627, 38616}, 67.5),
	TEAK("Teak", 6333, new int[] {9036, 49275}, 85),
	MAPLE("Maple", 1517, new int[] {51843}, 100),
	MAHOGANY("Mahogany", 6332, new int[] {46274}, 140.2),
	ARCTICPINE("Arctic Pine", 10810, new int[] {70057}, 140.2),
	YEW("Yew", 1515, new int[] {38755}, 175),
	//IVY("Ivy", 0, new int[] {470, 670, 673, 675}, 332.5),
	MAGIC("Magic", 1513, new int[] {63176}, 250);
	
	String name;
	int itemID;
	int[] sceneID;
	double XP;

	TreeType(String name, int itemID, int[] sceneID, double XP) {
		this.name = name;
		this.itemID = itemID;
		this.sceneID = sceneID;
		this.XP = XP;
	}
	
	public String getName() {
		return name;
	}
	
	public int getItemID() {
		return itemID;
	}
	
	public int[] getSceneID() {
		return sceneID;
	}
	
	public double getXP() {
		return XP;
	}
}
