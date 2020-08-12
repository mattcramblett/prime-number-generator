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

	protected PrimeNumberGeneratorImpl() {
	}

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
		if (value < FIRST_PRIME) {
			return false;
		}
		final int upperBound = (int) Math.sqrt(value) + 1;
		return !IntStream.range(FIRST_PRIME, upperBound).anyMatch(x -> value % x == 0);
	}

	private List<Integer> getPrimeNumbersInRange() {
		final int segmentSize = Math.max(FIRST_PRIME, (int) Math.sqrt(this.endingValue) + 1);
		final Segment initialPrimes = this.createSegmentOfPrimes(segmentSize);

		final List<Integer> result = new ArrayList<>();
		result.addAll(this.convertSegmentToList(initialPrimes));

		final Iterator<Segment> segmentedRange = SegmentedRange.of(this.endingValue, segmentSize);

		while (segmentedRange.hasNext()) {
			result.addAll(this.getPrimesFromSegment(segmentedRange.next(), initialPrimes));
		}

		return result;
	}

	private Segment createSegmentOfPrimes(final int maxBound) {
		final Segment segment = Segment.of(FIRST_PRIME, maxBound);

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

	private List<Integer> getPrimesFromSegment(final Segment segment, final Segment knownPrimes) {

		knownPrimes.streamFlagged().forEach(knownPrime -> {

			final int startingComposite = this.getFirstCompositeInSegment(segment, knownPrime);

			for (int j = startingComposite; j > 0 && j <= segment.getUpperBound(); j += knownPrime) {
				segment.set(j, false);
			}
		});

		return this.convertSegmentToList(segment);
	}

	private int getFirstCompositeInSegment(final Segment segment, final int knownPrime) {
		int startingComposite = (segment.getLowerBound() / knownPrime) * knownPrime;

		if (startingComposite < segment.getLowerBound()) {
			startingComposite += knownPrime;
		}
		return startingComposite;
	}

}