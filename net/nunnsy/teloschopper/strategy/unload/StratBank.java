package net.nunnsy.teloschopper.strategy.unload;

import net.nunnsy.teloschopper.framework.Job;
import net.nunnsy.teloschopper.framework.Strategy;
import net.nunnsy.teloschopper.strategy.utility.BankLocation;
import net.nunnsy.teloschopper.strategy.utility.UnloadMethod;
import net.nunnsy.teloschopper.utility.Constants;
import net.nunnsy.teloschopper.utility.Dynamics;
import net.nunnsy.teloschopper.utility.state.State;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.methods.widget.Bank.Amount;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.node.Item;

public class StratBank extends Strategy implements Job {
	
	private static BankLocation bankLocation;

	@Override
	public boolean validate() {
		if (Dynamics.getState() == State.UNLOADING && Dynamics.getMethod() == UnloadMethod.BANK) {
			return true;
		}
		
		return false;
	}

	@Override
	public void run() {
		if (!Bank.isOpen()) {
			Bank.open();
			Task.sleep(Random.nextInt(1000, 2000));
		}
		
		if (Bank.isOpen()) {
			if (Inventory.contains(Constants.HATCHET_IDS)) {
				for (Item item : getDepositItems()) {
					Bank.deposit(item.getId(), Amount.ALL);
					Task.sleep(Random.nextInt(100, 200));
				}
			} else {
				Bank.depositInventory();
				Task.sleep(Random.nextInt(1000, 2000));
			}
			
			Task.sleep(Random.nextInt(2000, 3000));
		}
	}
	
	private Item[] getDepositItems() {
		return Inventory.getItems(new Filter<Item> () {
			@Override
			public boolean accept(Item item) {
				for (int hatchetID : Constants.HATCHET_IDS) {
					if (hatchetID == item.getId()) {
						return false;
					}
				}
				return true;
			}
		});
	}

	public static BankLocation getBankLocation() {
		return bankLocation;
	}
	
	public static void setBankLocation(BankLocation BL) {
		bankLocation = BL;
	}
	
	public static boolean isAtBank() {
		return bankLocation.getArea().contains(Players.getLocal());
	}
}
