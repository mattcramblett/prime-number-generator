package com.mattcramblett.primenumbergenerator;

import java.util.stream.IntStream;

/**
 * A mapping of integers within a range to boolean values.
 *
 */
public interface Segment {

	/**
	 * Returns the status of the number in this segment.
	 * 
	 * @param value the number to query
	 * @return status of the given number
	 */
	boolean get(int value);

	/**
	 * Updates the status of the given number in this segment.
	 * 
	 * @param value the number to update
	 * @param flag  the new status
	 */
	void set(int value, boolean flag);

	/**
	 * Creates an ordered stream of numbers within the segment where status is true
	 * 
	 * @return the stream of numbers
	 */
	IntStream streamFlagged();

	/**
	 * Returns the lower bound of this segment
	 * 
	 * @return the lower bound
	 */
	int getLowerBound();

	/**
	 * Returns the upper bound of this segment
	 * 
	 * @return the upper bound
	 */
	int getUpperBound();

}