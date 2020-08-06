package com.mattcramblett.primenumbergenerator;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Test;

import junit.framework.TestCase;

public class PrimeNumberGeneratorTest extends TestCase {

	private final PrimeNumberGeneratorImpl classUnderTest = new PrimeNumberGeneratorImpl();

	private static final List<Integer> SMALL_PRIMES = Arrays.asList(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43,
			47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101);

	@Test
	public void testIsPrimeFalseWithNonNaturalNumbers() {
		Stream.of(-1, -2, -4, -13, -29, -7901, -100000, Integer.MIN_VALUE)
				.forEach(nonNaturalNum -> assertFalse(this.classUnderTest.isPrime(nonNaturalNum)));
	}

	@Test
	public void testIsPrimeFalseWith0() {
		assertFalse(this.classUnderTest.isPrime(0));
	}

	@Test
	public void testIsPrimeFalseWith1() {
		assertFalse(this.classUnderTest.isPrime(1));
	}

	@Test
	public void testIsPrimeTrueWithSmallPrimes() {
		SMALL_PRIMES.forEach(smallPrime -> assertTrue(this.classUnderTest.isPrime(smallPrime)));
	}

	@Test
	public void testIsPrimeFalseWithSmallComposites() {
		Stream.of(4, 6, 12, 20, 24, 30, 42, 56, 64, 78, 82, 100)
				.forEach(smallComp -> assertFalse(this.classUnderTest.isPrime(smallComp)));
	}

	@Test
	public void testIsPrimeTrueWithLargePrime() {
		assertTrue(this.classUnderTest.isPrime(Integer.MAX_VALUE));
	}

	@Test
	public void testIsPrimeFalseWithLargeComposite() {
		assertFalse(this.classUnderTest.isPrime(Integer.MAX_VALUE - 1));
	}

	@Test
	public void testGeneratePrimesEmptyWithOutOfBoundsRange() {
		assertEquals(Arrays.asList(), this.classUnderTest.generate(Integer.MIN_VALUE, -1));
	}

	@Test
	public void testGenerateSmallPrimes() {
		assertEquals(SMALL_PRIMES, this.classUnderTest.generate(0, 102));
	}

}
