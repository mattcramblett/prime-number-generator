package com.mattcramblett.primenumbergenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PrimeNumberGeneratorImpl implements PrimeNumberGenerator {

	public static final int FIRST_PRIME = 2;

	@Override
	public List<Integer> generate(final int startingValue, final int endingValue) {
		final int start = Math.max(0, startingValue);
		final int end = Math.max(FIRST_PRIME, endingValue);

		return this.getPrimeNumbersInRange(Math.min(start, end), Math.max(start, end));
	}

	@Override
	public boolean isPrime(final int value) {
		if (value <= 1) {
			return false;
		}
		final int upperBound = (int) Math.sqrt(value) + 1;
		return !IntStream.range(FIRST_PRIME, upperBound).anyMatch(x -> value % x == 0);
	}

	private List<Integer> getPrimeNumbersInRange(final int startingValue, final int endingValue) {

		final int segmentSize = (int) Math.sqrt(endingValue) + 1;
		final Segment initialSegment = this.createSegmentOfPrimes(segmentSize);

		final AtomicInteger segmentBoundLow = new AtomicInteger(segmentSize);
		final AtomicInteger segmentBoundHigh = new AtomicInteger(2 * segmentSize);

		final List<Integer> result = new ArrayList<>();
		result.addAll(initialSegment.streamFlagged().filter(x -> x >= startingValue && x <= endingValue).boxed()
				.collect(Collectors.toList()));

		while (segmentBoundLow.get() < endingValue && segmentBoundLow.get() > 0) {

			if (segmentBoundHigh.get() > 0) {
				segmentBoundHigh.set(Math.min(endingValue, segmentBoundHigh.get()));
			} else {
				segmentBoundHigh.set(endingValue);
			}

			final Segment currentSegment = new SegmentImpl(segmentBoundLow.get(), segmentBoundHigh.get());

			initialSegment.streamFlagged().forEach(knownPrime -> {

				int startingComposite = (segmentBoundLow.get() / knownPrime) * knownPrime;

				if (startingComposite < segmentBoundLow.get()) {
					startingComposite += knownPrime;
				}

				for (int j = startingComposite; j > 0 && j < segmentBoundHigh.get(); j += knownPrime) {
					currentSegment.set(j, false);
				}
			});

			result.addAll(currentSegment.streamFlagged().filter(x -> x >= 0)
					.filter(x -> x >= startingValue && x <= endingValue).boxed().collect(Collectors.toList()));
			segmentBoundLow.getAndUpdate(l -> l += segmentSize);
			segmentBoundHigh.getAndUpdate(h -> h += segmentSize);
		}

		if (endingValue == Integer.MAX_VALUE) {
			result.add(endingValue);
		}
		return result;
	}

	private Segment createSegmentOfPrimes(final int endingValue) {
		final Segment segment = new SegmentImpl(FIRST_PRIME, endingValue);

		for (int i = FIRST_PRIME; i > 0 && i <= endingValue; i++) {
			if (segment.get(i)) {
				for (int j = i + i; j > 0 && j <= endingValue; j = j + i) {
					segment.set(j, false);
				}
			}
		}

		return segment;
	}

}