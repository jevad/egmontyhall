// default package

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.UUID;
import java.util.function.Supplier;

public class SecureThreadLocalRandom extends ThreadLocal<SecureRandom> {

	private Supplier<byte[]> seedGenerator = null;

	// Here is an example of using the Supplier functional interface:
	private Supplier<byte[]> getSeedGenerator() {
		if (null == this.seedGenerator) {
			this.seedGenerator = new Supplier<byte[]>() {

				@Override
				public byte[] get() {
					return UUID.randomUUID().toString().getBytes();
				}
			};
//			this.seedGenerator = new Supplier<byte[]>() {
//				private static final int OFFSET_1 = 0;
//				private static final int OFFSET_2 = OFFSET_1 + Long.BYTES;
//				private static final int OFFSET_3 = OFFSET_2 + Long.BYTES;
//				private static final int OFFSET_4 = OFFSET_3 + Long.BYTES;
//
//				public byte[] get() {
//					final String threadName = Thread.currentThread().getName();
//					final byte[] threadNameAsBytes = threadName.getBytes();
//					ByteBuffer buffy = ByteBuffer.allocate(OFFSET_4
//							+ threadNameAsBytes.length);
//					buffy.putLong(OFFSET_1, ThreadLocalRandom.current()
//							.nextLong());
//					buffy.putLong(OFFSET_2, System.currentTimeMillis());
//					buffy.putLong(OFFSET_3, System.nanoTime());
//					buffy.position(OFFSET_4);
//					buffy.put(threadNameAsBytes);
//					return buffy.array();
//				}
//
//			};
		}

		return this.seedGenerator;
	}

	@Override
	protected SecureRandom initialValue() {
		SecureRandom reply = null;

		try {
			reply = SecureRandom.getInstanceStrong();
		} catch (NoSuchAlgorithmException nsae) {
			reply = new SecureRandom(getSeedGenerator().get());
		}

		return reply;
	}
}
