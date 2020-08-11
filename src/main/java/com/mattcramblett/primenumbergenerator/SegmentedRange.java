package com.mattcramblett.primenumbergenerator;

import java.util.Iterator;

/**
 * An iterative utility for traversing many segments in a range of numbers.
 *
 */
public interface SegmentedRange extends Iterator<Segment> {

	/**
	 * Creates a utility for iterating over segments.
	 * 
	 * @param endingValue the last value for segments
	 * @param segmentSize the size for each segment
	 */
	public static SegmentedRange of(final int endingValue, final int segmentSize) {
		return new SegmentedRangeImpl(endingValue, segmentSize);
	}
}
