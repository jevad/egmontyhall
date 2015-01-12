// default package

import java.util.Spliterator;
import java.util.function.Consumer;


public class DealSetupSpliterator implements Spliterator<DealSetup> {

	private final int characteristics = IMMUTABLE & NONNULL & SIZED & SUBSIZED;
	
	private int remaining = 0;
	
	public DealSetupSpliterator(int size) {
		this.remaining = size;
	}

	@Override
	public int characteristics() {
		return this.characteristics;
	}

	@Override
	public boolean hasCharacteristics(final int characteristics) {
		return 0 != (characteristics & this.characteristics);
	}

	@Override
	public long estimateSize() {
		return this.remaining;
	}

	@Override
	public long getExactSizeIfKnown() {
		return this.remaining;
	}
	
	private boolean work(final Consumer<? super DealSetup> action) {
		action.accept(DealSetup.pickOneAtRandom());
		--remaining;
		return true;
	}

	@Override
	public boolean tryAdvance(final Consumer<? super DealSetup> action) {
		boolean reply = false;
		
		if (0 < remaining) {
			reply = work(action);
		}
		
		return reply;
	}
	
	@Override
	public void forEachRemaining(final Consumer<? super DealSetup> action) {
		while (0 < remaining) {
			work(action);
		}
	}


	@Override
	public Spliterator<DealSetup> trySplit() {
		Spliterator<DealSetup> reply = null;
		
		if (2 < remaining) {
			int calcRemain = remaining / 2;
			reply = new DealSetupSpliterator(calcRemain);
			remaining -= calcRemain;
		}
		
		return reply;
	}
}
