import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


public enum Door {
	NUMBER_ONE,
	NUMBER_TWO,
	NUMBER_THREE;
	
	// private static final SecureThreadLocalRandom randy = new SecureThreadLocalRandom();
	
	private Door [] otherTwo;


	public Door [] otherTwoDoors() {
		if (null == this.otherTwo) {
			if (this.equals(Door.NUMBER_ONE)) {
				this.otherTwo = new Door [] {Door.NUMBER_TWO, Door.NUMBER_THREE};
			} else if (this.equals(Door.NUMBER_TWO)) {
				this.otherTwo = new Door [] {Door.NUMBER_ONE, Door.NUMBER_THREE};
			} else {
				this.otherTwo = new Door [] {Door.NUMBER_ONE, Door.NUMBER_TWO};
			}
		}
		
		return this.otherTwo;
	}

	public static Door pickOneAtRandom() {
		// final SecureRandom r = randy.get();
		final Random r = ThreadLocalRandom.current();
		final int dex = r.nextInt(3);
		return 0 == dex ? Door.NUMBER_ONE : (1 == dex ? Door.NUMBER_TWO
				: Door.NUMBER_THREE);
	}	
}
