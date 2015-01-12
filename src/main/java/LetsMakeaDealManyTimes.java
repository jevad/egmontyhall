import java.util.function.IntUnaryOperator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class LetsMakeaDealManyTimes {

	public static class Rslt {
		public long time = 0;
		public int count = 0;

		public Rslt(final long time, final int count) {
			this.time = time;
			this.count = count;
		}
	}

	public static Rslt timeIt(final IntUnaryOperator func,
			final int howManyTimes) {
		final long start = System.currentTimeMillis();

		int count = func.applyAsInt(howManyTimes);

		final long end = System.currentTimeMillis();
		return new Rslt(end - start, count);
	}

	public static int manyTimes(final int howManyTimes) {
		return Stream.generate(DealSetup.supplier()).limit((long) howManyTimes)
				.parallel().map(DealResults::getNewInstance)
				.peek(DealResults::pickDoor)
				.mapToInt(a -> a.countIsWinnerIfSwitch()).sum();
	}

	public static int manyTimesWithCustomSpliterator(final int howManyTimes) {
		return StreamSupport
				.stream(new DealSetupSpliterator(howManyTimes), true)
				.map(DealResults::getNewInstance).parallel()
				.peek(DealResults::pickDoor).parallel()
				.mapToInt(a -> a.countIsWinnerIfSwitch()).sum();
	}

	public static int manyTimesTheOldWay(final int howManyTimes) {
		int winCount = 0;

		for (int i = 0; i < howManyTimes; ++i) {
			final DealSetup s = DealSetup.pickOneAtRandom();
			final DealResults r = DealResults.getNewInstance(s);
			r.pickDoor();
			winCount += r.countIsWinnerIfSwitch();
		}

		return winCount;
	}

	public static void main(final String[] argv) {
		int howManyTimes = Integer.valueOf(argv[0]);
		System.out.println("Playing Let's Make a Deal " + howManyTimes
				+ " times.");

		// run through a few times to give the hotspot compiler a chance
		// to optimize things ...
		final int opttries = 200;
		timeIt(LetsMakeaDealManyTimes::manyTimes, opttries);
		timeIt(LetsMakeaDealManyTimes::manyTimesWithCustomSpliterator, opttries);
		timeIt(LetsMakeaDealManyTimes::manyTimesTheOldWay, opttries);

		Rslt result = timeIt(LetsMakeaDealManyTimes::manyTimes, howManyTimes);
		Rslt resultcs = timeIt(
				LetsMakeaDealManyTimes::manyTimesWithCustomSpliterator,
				howManyTimes);
		Rslt resultold = timeIt(LetsMakeaDealManyTimes::manyTimesTheOldWay,
				howManyTimes);

		System.out.println("     Won by switching the parallel way "
				+ result.count + " times in relative time, " + result.time
				+ ".");
		System.out.println("     Won by switching the custom way "
				+ resultcs.count + " times in relative time, " + resultcs.time
				+ ".");
		System.out.println("     Won by switching the old way "
				+ resultold.count + " times in relative time, "
				+ resultold.time + ".");
	}

}
