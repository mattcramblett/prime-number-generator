package com.mattcramblett.primenumbergenerator;

import java.util.NoSuchElementException;

public class SegmentedRangeImpl implements SegmentedRange {

	private final int endingValue;

	private final int segmentSize;

	private int nextSegmentBoundLow;

	private int nextSegmentBoundHigh;

	/**
	 * Creates a utility for iterating over segments.
	 * 
	 * @param endingValue the last value for segments
	 * @param segmentSize the size for each segment
	 */
	protected SegmentedRangeImpl(final int endingValue, final int segmentSize) {
		this.endingValue = endingValue;

		this.segmentSize = segmentSize;

		this.nextSegmentBoundLow = this.segmentSize;
		this.nextSegmentBoundHigh = 2 * this.segmentSize;
	}

	@Override
	public boolean hasNext() {
		return this.nextSegmentBoundLow < this.endingValue && this.nextSegmentBoundLow > 0;
	}

	@Override
	public Segment next() {
		if (!this.hasNext()) {
			throw new NoSuchElementException();
		}

		if (this.nextSegmentBoundHigh > 0) {
			this.nextSegmentBoundHigh = Math.min(this.endingValue, this.nextSegmentBoundHigh);
		} else {
			// In case of overflow
			this.nextSegmentBoundHigh = this.endingValue;
		}

		final Segment nextSegment = Segment.of(this.nextSegmentBoundLow, this.nextSegmentBoundHigh);

		this.nextSegmentBoundLow += this.segmentSize;
		this.nextSegmentBoundHigh += this.segmentSize;

		return nextSegment;
	}

}
