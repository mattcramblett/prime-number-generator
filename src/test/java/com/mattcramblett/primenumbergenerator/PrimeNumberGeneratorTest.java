package com.mattcramblett.primenumbergenerator;

import java.util.stream.Stream;

import org.junit.Test;

import junit.framework.TestCase;

public class PrimeNumberGeneratorTest extends TestCase {

	private final PrimeNumberGeneratorImpl classUnderTest = new PrimeNumberGeneratorImpl();

	@Test
	public void testIsPrimeFalseWithNonNaturalNumbers() {
		Stream.of(-1, -2, -4, -13, -29, -7901, -100000, Integer.MIN_VALUE)
				.forEach(nonNaturalNum -> assertFalse(this.classUnderTest.isPrime(nonNaturalNum)));
	}

}
