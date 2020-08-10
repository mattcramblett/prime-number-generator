package com.mattcramblett.primenumbergenerator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.stream.Stream;

import org.junit.Ignore;
import org.junit.Test;

public class PrimeNumberGeneratorTest extends AbstractTest {

	private final PrimeNumberGeneratorImpl classUnderTest = new PrimeNumberGeneratorImpl();

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

	@Test
	public void testGenerateSmallPrimesWithInverseRange() {
		assertEquals(SMALL_PRIMES, this.classUnderTest.generate(102, -102));
	}

	@Ignore("Covers edge case but is too slow for unit test")
	@Test
	public void testGenerateMaxPrimes() {
		assertEquals(Arrays.asList(2147483549, 2147483563, 2147483579, 2147483587, 2147483629, Integer.MAX_VALUE),
				this.classUnderTest.generate(Integer.MAX_VALUE - 100, Integer.MAX_VALUE));
	}

	@Test
	public void testGenerateLargePrimes() {
		assertEquals(Arrays.asList(99999931, 99999941, 99999959, 99999971, 99999989),
				this.classUnderTest.generate(99999900, 100000001));
	}

	@Test
	public void testGeneratePrimesWithNarrowRange() {
		assertEquals(Arrays.asList(7901, 7907, 7919), this.classUnderTest.generate(7900, 7920));
	}

	@Test
	public void testGeneratePrimesWhereTwoIsOnlyResult() {
		assertEquals(Arrays.asList(2), this.classUnderTest.generate(-1, 2));
	}

	@Test
	public void testGeneratePrimesWherePrimeIsUpperBound() {
		assertEquals(Arrays.asList(2, 3, 5, 7, 11), this.classUnderTest.generate(0, 11));
	}

}
