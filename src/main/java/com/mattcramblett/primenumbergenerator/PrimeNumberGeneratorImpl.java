package com.mattcramblett.primenumbergenerator;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PrimeNumberGeneratorImpl implements PrimeNumberGenerator {

	@Override
	public List<Integer> generate(final int startingValue, final int endingValue) {
		final int start = startingValue < 0 ? 0 : startingValue;
		final int end = endingValue < 0 ? 0 : endingValue;
		final IntStream intStream = start > end ? IntStream.rangeClosed(end, start) : IntStream.rangeClosed(start, end);
		return intStream.filter(this::isPrime).boxed().collect(Collectors.toList());
	}

	@Override
	public boolean isPrime(final int value) {
		if (value <= 1) {
			return false;
		}
		final int upperBound = (int) Math.sqrt(value) + 1;
		for (int i = 2; i < upperBound; i++) {
			if (value % i == 0) {
				return false;
			}
		}

		return true;
	}

}
