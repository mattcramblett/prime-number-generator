package com.mattcramblett.primenumbergenerator;

import java.util.List;

public interface PrimeNumberGenerator {

	public static PrimeNumberGenerator of() {
		return new PrimeNumberGeneratorImpl();
	}

	/**
	 * Generates an ordered list of prime numbers within the specified range.
	 * 
	 * @param startingValue the lower bound (inclusive)
	 * @param endingValue   the upper bound (inclusive)
	 * @return the list of prime numbers within range
	 */
	List<Integer> generate(int startingValue, int endingValue);

	/**
	 * Determines whether or not a number is prime.
	 * 
	 * @param value the number to test
	 * @return true if the value is prime
	 */
	boolean isPrime(int value);

}
