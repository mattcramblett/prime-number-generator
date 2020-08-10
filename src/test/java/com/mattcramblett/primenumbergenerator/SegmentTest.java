package com.mattcramblett.primenumbergenerator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Test;

public class SegmentTest extends AbstractTest {

	@Test(expected = IllegalArgumentException.class)
	public void testSegmentCannotHaveNegativeLowerBound() {
		new SegmentImpl(-1, 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSegmentCannotHaveNegativeUpperBound() {
		new SegmentImpl(0, -1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSegmentCannotHaveUpperBoundLessThanLowerBound() {
		new SegmentImpl(1, 0);
	}

	@Test
	public void testSegmentInitiallyAllTrue() {
		final SegmentImpl segment = new SegmentImpl(20, 50);
		assertEquals(IntStream.rangeClosed(20, 50).boxed().collect(Collectors.toList()),
				segment.streamFlagged().boxed().collect(Collectors.toList()));
	}

	@Test
	public void testGetWithUnFlaggedNumber() {
		final SegmentImpl segment = new SegmentImpl(5, 25);
		segment.set(10, false);
		assertFalse(segment.get(10));
	}

	@Test
	public void testSegmentRetainsNumbersAndOrderWithUnFlaggedNumbers() {
		final SegmentImpl segment = new SegmentImpl(0, 45);
		final List<Integer> falseNumbers = Arrays.asList(4, 8, 15, 16, 23, 42);
		falseNumbers.forEach(n -> segment.set(n, false));

		final List<Integer> expected = new ArrayList<>();
		expected.addAll(IntStream.rangeClosed(0, 45).boxed().collect(Collectors.toList()));
		expected.removeAll(falseNumbers);

		assertEquals(expected, segment.streamFlagged().boxed().collect(Collectors.toList()));
	}

	@Test
	public void testSegmentValueWithMaxUpperBound() {
		final SegmentImpl segment = new SegmentImpl(Integer.MAX_VALUE - 100, Integer.MAX_VALUE);
		final List<Integer> expected = IntStream.rangeClosed(Integer.MAX_VALUE - 100, Integer.MAX_VALUE).boxed()
				.collect(Collectors.toList());

		assertTrue(segment.get(Integer.MAX_VALUE - 1));
		assertEquals(expected, segment.streamFlagged().boxed().collect(Collectors.toList()));
	}

	@Test
	public void testBoundsRetainValuesInArbitraryrangeClosed() {
		final SegmentImpl segment = new SegmentImpl(100, 250);
		assertEquals(100, segment.getLowerBound());
		assertEquals(250, segment.getUpperBound());
	}

}
