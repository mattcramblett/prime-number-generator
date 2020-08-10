package com.mattcramblett.primenumbergenerator;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SegmentedRangeImpl implements Iterator<Segment> {

	private final int endingValue;

	private final int segmentSize;

	private int nextSegmentBoundLow;

	private int nextSegmentBoundHigh;

	public SegmentedRangeImpl(final int endingValue, final int segmentSize) {
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

		final Segment nextSegment = new SegmentImpl(this.nextSegmentBoundLow, this.nextSegmentBoundHigh);

		this.nextSegmentBoundLow += this.segmentSize;
		this.nextSegmentBoundHigh += this.segmentSize;

		return nextSegment;
	}

}
