package com.mattcramblett.primenumbergenerator;

import java.util.List;

/**
 * Entry point for using the system
 */
public class Main {

	private Main() {
	}

	public static void main(final String[] args) {
		final List<Integer> primes = PrimeNumberGenerator.of().generate(Integer.parseInt(args[0]),
				Integer.parseInt(args[1]));
		System.out.println(primes); // NOSONAR
	}
}
