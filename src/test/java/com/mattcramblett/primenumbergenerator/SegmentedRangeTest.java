package com.mattcramblett.primenumbergenerator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.NoSuchElementException;
import java.util.stream.IntStream;

import org.junit.Test;

public class SegmentedRangeTest {

	@Test
	public void testSegmentRangeStartsAtSegmentSize() {
		final SegmentedRangeImpl classUnderTest = new SegmentedRangeImpl(100, 50);
		assertEquals(50, classUnderTest.next().getLowerBound());
	}

	@Test
	public void testHasNextFalseWhenNoElementExists() {
		final SegmentedRangeImpl classUnderTest = new SegmentedRangeImpl(100, 50);
		classUnderTest.next();
		assertFalse(classUnderTest.hasNext());
	}

	@Test(expected = NoSuchElementException.class)
	public void testNextThrowsExceptionWhenNoElementExists() {
		final SegmentedRangeImpl classUnderTest = new SegmentedRangeImpl(100, 100);
		classUnderTest.next();
	}

	@Test
	public void testHasNextTrueWithSegmentAvailableAfterMultipleCalls() {
		final SegmentedRangeImpl classUnderTest = new SegmentedRangeImpl(100, 50);
		IntStream.range(0, 2).forEach(i -> assertTrue(classUnderTest.hasNext()));
	}

	@Test
	public void testIteratingOverOneSegment() {
		final SegmentedRangeImpl classUnderTest = new SegmentedRangeImpl(8, 4);
		assertTrue(classUnderTest.hasNext());
		final Segment segment = classUnderTest.next();
		assertEquals(4, segment.getLowerBound());
		assertEquals(8, segment.getUpperBound());
		assertFalse(classUnderTest.hasNext());
		assertFalse(classUnderTest.hasNext());
	}

	@Test
	public void testIteratingOverManySegments() {
		final int segmentSize = 2;
		final SegmentedRangeImpl classUnderTest = new SegmentedRangeImpl(10, segmentSize);
		assertTrue(classUnderTest.hasNext());
		int segmentCounter = 0;

		while (classUnderTest.hasNext()) {
			final Segment segment = classUnderTest.next();
			segmentCounter++;
			assertEquals(segmentCounter * segmentSize, segment.getLowerBound());
			assertEquals((segmentCounter * segmentSize) + segmentSize, segment.getUpperBound());
		}

		assertEquals(4, segmentCounter);
	}

}
