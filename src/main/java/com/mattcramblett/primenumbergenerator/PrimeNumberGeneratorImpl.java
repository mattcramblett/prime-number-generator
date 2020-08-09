package com.mattcramblett.primenumbergenerator;

import java.util.BitSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PrimeNumberGeneratorImpl implements PrimeNumberGenerator {

	private final BitSet flags = new BitSet();

	@Override
	public List<Integer> generate(final int startingValue, final int endingValue) {
		final int start = Math.max(0, startingValue);
		final int end = Math.max(2, endingValue);
		final IntStream intStream = IntStream.rangeClosed(Math.min(start, end), Math.max(start, end));

		this.updateFlagsWithSieve(Math.max(start, end));

		return intStream.filter(this.flags::get).boxed().collect(Collectors.toList());
	}

	@Override
	public boolean isPrime(final int value) {
		if (value <= 1) {
			return false;
		}
		final int upperBound = (int) Math.sqrt(value) + 1;
		return !IntStream.range(2, upperBound).anyMatch(x -> value % x == 0);
	}

	private void updateFlagsWithSieve(final int n) {
		if (n == Integer.MAX_VALUE) {
			this.flags.set(2, n, true);
			this.flags.set(n, true);
		} else {
			this.flags.set(2, n, true);
		}

		for (int i = 2; i > 0 && i <= n; i++) {
			if (this.flags.get(i)) {
				for (int j = i + i; j > 0 && j <= n; j = j + i) {
					this.flags.set(j, false);
				}
			}
		}
	}

}