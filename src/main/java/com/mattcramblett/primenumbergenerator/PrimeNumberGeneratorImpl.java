package com.mattcramblett.primenumbergenerator;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PrimeNumberGeneratorImpl implements PrimeNumberGenerator {

	@Override
	public List<Integer> generate(final int startingValue, final int endingValue) {
		final int start = Math.max(0, startingValue);
		final int end = Math.max(0, endingValue);
		final IntStream intStream = IntStream.rangeClosed(Math.min(start, end), Math.max(start, end));
		return intStream.filter(this::isPrime).boxed().collect(Collectors.toList());
	}

	@Override
	public boolean isPrime(final int value) {
		if (value <= 1) {
			return false;
		}
		final int upperBound = (int) Math.sqrt(value) + 1;
		return !IntStream.range(2, upperBound).anyMatch(x -> value % x == 0);
	}

}
