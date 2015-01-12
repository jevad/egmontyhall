import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

public enum DealSetup {
	ALPHA(Prize.CAR, Prize.DONKEY, Prize.DONKEY),
	BETA(Prize.CAR, Prize.DONKEY, Prize.DONKEY),
	GAMMA(Prize.DONKEY, Prize.DONKEY, Prize.CAR);

	// private static final SecureThreadLocalRandom randy = new SecureThreadLocalRandom();

	private HashMap<Door, Prize> setup = new HashMap<Door, Prize>(3);

	DealSetup(final Prize doorOnePrize, final Prize doorTwoPrize,
			final Prize doorThreePrize) {
		setup.put(Door.NUMBER_ONE, doorOnePrize);
		setup.put(Door.NUMBER_TWO, doorTwoPrize);
		setup.put(Door.NUMBER_THREE, doorThreePrize);
	}
	
	public boolean isWinner(Door d) {
		return setup.get(d).isWinner();
	}

	public static DealSetup pickOneAtRandom() {
		// final Random r = randy.get();
		final Random r = ThreadLocalRandom.current();
		final int dex = r.nextInt(3);
		return 0 == dex ? DealSetup.ALPHA : (1 == dex ? DealSetup.BETA
				: DealSetup.GAMMA);
	}
	
	public static Supplier<DealSetup> supplier() {
		return new Supplier<DealSetup>() {
			public DealSetup get() {
				return DealSetup.pickOneAtRandom();
			}
			
		};
	}

}
