package com.mattcramblett.primenumbergenerator;

import java.util.List;

public class PrimeNumberGeneratorImpl implements PrimeNumberGenerator {

	@Override
	public List<Integer> generate(final int startingValue, final int endingValue) {
		// TODO provide implementation
		return null;
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
