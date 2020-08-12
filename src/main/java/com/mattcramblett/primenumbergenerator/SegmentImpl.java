package com.mattcramblett.primenumbergenerator;

import java.util.BitSet;
import java.util.stream.IntStream;

public class SegmentImpl implements Segment {

	private final BitSet flags;

	private final int offset;

	private final int maxBitSetValue;

	/**
	 * 
	 * Creates a memory efficient mapping of numbers in a given range to boolean
	 * values. Default value is true.
	 * 
	 * @param low  inclusive lower bound
	 * @param high inclusive upper bound
	 */
	protected SegmentImpl(final int low, final int high) {
		if (high < low) {
			throw new IllegalArgumentException("low must be less than high");
		}
		if (high < 0 || low < 0) {
			throw new IllegalArgumentException("bounds must be 0 or greater");
		}
		this.offset = low;
		this.maxBitSetValue = high - low;
		this.flags = new BitSet();

		if (this.maxBitSetValue == Integer.MAX_VALUE) {
			this.flags.set(0, this.maxBitSetValue, true);
		} else {
			this.flags.set(0, this.maxBitSetValue + 1, true);
		}
	}

	@Override
	public boolean get(final int value) {
		return this.flags.get(value - this.offset);
	}

	@Override
	public void set(final int value, final boolean flag) {
		this.flags.set(value - this.offset, flag);
	}

	@Override
	public IntStream streamFlagged() {
		return this.flags.stream().filter(this.flags::get).map(x -> x < Integer.MAX_VALUE ? x + this.offset : x);
	}

	@Override
	public int getLowerBound() {
		return this.offset;
	}

	@Override
	public int getUpperBound() {
		return this.maxBitSetValue + this.offset;
	}
}
