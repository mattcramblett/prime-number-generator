package com.mattcramblett.primenumbergenerator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PrimeNumberGeneratorImpl implements PrimeNumberGenerator {

	public static final int FIRST_PRIME = 2;

	private int startingValue;

	private int endingValue;

	@Override
	public List<Integer> generate(final int startingValue, final int endingValue) {
		final int start = Math.max(0, startingValue);
		final int end = Math.max(0, endingValue);

		this.startingValue = Math.min(start, end);
		this.endingValue = Math.max(start, end);

		return this.getPrimeNumbersInRange();
	}

	@Override
	public boolean isPrime(final int value) {
		if (value <= FIRST_PRIME - 1) {
			return false;
		}
		final int upperBound = (int) Math.sqrt(value) + 1;
		return !IntStream.range(FIRST_PRIME, upperBound).anyMatch(x -> value % x == 0);
	}

	private List<Integer> getPrimeNumbersInRange() {
		final int segmentSize = Math.max(2, (int) Math.sqrt(this.endingValue) + 1);
		final Segment initialSegment = this.createSegmentOfPrimes(segmentSize);

		final List<Integer> result = new ArrayList<>();
		result.addAll(this.convertSegmentToList(initialSegment));

		final Iterator<Segment> segmentedRange = new SegmentedRangeImpl(this.endingValue, segmentSize);

		while (segmentedRange.hasNext()) {
			result.addAll(this.generateCompositesForNextSegment(segmentedRange, initialSegment));
		}

		return result;
	}

	private Segment createSegmentOfPrimes(final int maxBound) {
		final Segment segment = new SegmentImpl(FIRST_PRIME, maxBound);

		for (int i = FIRST_PRIME; i > 0 && i <= maxBound; i++) {
			if (segment.get(i)) {
				for (int j = i + i; j > 0 && j <= maxBound; j = j + i) {
					segment.set(j, false);
				}
			}
		}

		return segment;
	}

	private List<Integer> convertSegmentToList(final Segment segment) {
		return segment.streamFlagged().filter(x -> x >= this.startingValue && x <= this.endingValue).boxed()
				.collect(Collectors.toList());
	}

	private List<Integer> generateCompositesForNextSegment(final Iterator<Segment> segmentedRange,
			final Segment knownPrimes) {
		final Segment currentSegment = segmentedRange.next();

		knownPrimes.streamFlagged().forEach(knownPrime -> {

			final int startingComposite = this.getFirstCompositeInSegment(currentSegment, knownPrime);

			for (int j = startingComposite; j > 0 && j <= currentSegment.getUpperBound(); j += knownPrime) {
				currentSegment.set(j, false);
			}
		});

		return this.convertSegmentToList(currentSegment);
	}

	private int getFirstCompositeInSegment(final Segment segment, final int knownPrime) {
		int startingComposite = (segment.getLowerBound() / knownPrime) * knownPrime;

		if (startingComposite < segment.getLowerBound()) {
			startingComposite += knownPrime;
		}
		return startingComposite;
	}

}