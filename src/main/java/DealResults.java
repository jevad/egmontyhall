// default package

public class DealResults {
	private DealSetup dealSetup;
	private Door pickedDoor;
	
	private DealResults(final DealSetup dealSetup) {
		this.dealSetup = dealSetup;
	}

	public static DealResults getNewInstance(final DealSetup dealSetup) {
		return new DealResults(dealSetup);
	}
	
	public void pickDoor() {
		this.pickedDoor = Door.pickOneAtRandom();
	}
	
	public boolean isWinnerIfNoSwitch() {
		return dealSetup.isWinner(pickedDoor);
	}
	
	public boolean isWinnerIfSwitch() {
		boolean isWinner = false;
		
		if (dealSetup.isWinner(pickedDoor)) {
			// winning door was switched-away-from -- leave as false
		} else {
			// Note that this block will always result in isWinner "true";
			// however, the purpose of this simulation is to prove that,
			// so we'll go through the logic.  It is possible, however,
			// that the hotspot compiler might detect that this will always
			// result in isWinner "true" and optimize this code away.
			final Door [] otherDoors = pickedDoor.otherTwoDoors();
			final Door montyOpened = dealSetup.isWinner(otherDoors[0]) ? otherDoors[1] : otherDoors[0];
			final Door switchToDoor = montyOpened.equals(otherDoors[0]) ? otherDoors[1] : otherDoors[0];
			isWinner = dealSetup.isWinner(switchToDoor);
		}
		
		return isWinner;
	}
	
	public int countIsWinnerIfNoSwitch() {
		return isWinnerIfSwitch() ? 1 : 0;
	}
	
	public int countIsWinnerIfSwitch() {
		return isWinnerIfNoSwitch() ? 0 : 1;
	}
}
