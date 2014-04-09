package net.nunnsy.teloschopper.utility;

import java.util.ArrayList;

public class Constants {

	public final static int[] HATCHET_IDS = {1351, 1349, 1353, 1361, 1355, 1357, 1359, 6739, 13470};
	public final static int BONFIRE_ANIMATION = 16701;
	public final static int[] CHOPPING_ANIMATIONS = {867, 2846, 10251};
	public final static int[] FIRE_IDS = {70755, 70756, 70757, 70758, 70759, 70760, 70761, 70762, 70763, 70764};
	public final static int[] NEST_IDS = {5070, 5071, 5072, 5073, 5074, 5075, 5076, 7413, 11966};
	public final static int ORT_ID = 24909;
	public final static int STUMP_ID = 20315;
	public final static int PILE_ID = 46327;
	public final static int SPLITTING_ANIMATION = 12345;
	
	public static int[] keepingIDs;
	
	public static int[] keepIDs() {
		return keepingIDs;
	}
	
	public static void findKeepIDs() {
		ArrayList<Integer> IDs = new ArrayList<Integer>();
		
		if (Dynamics.isPickingNests()) {
			for (int id : NEST_IDS) {
				IDs.add(id);
			}
		}
		
		if (Dynamics.isPickingOrt()) {
			IDs.add(ORT_ID);
		}
		
		for (int id : HATCHET_IDS) {
			IDs.add(id);
		}
		
		keepingIDs = new int[IDs.size()];
		
		for (int i = 0; i < IDs.size(); i++) {
			keepingIDs[i] = IDs.get(i);
		}
	}
}
