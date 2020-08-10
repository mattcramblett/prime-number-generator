package com.mattcramblett.primenumbergenerator;

import java.util.stream.IntStream;

public interface Segment {

	boolean get(int value);

	void set(int value, boolean flag);

	IntStream streamFlagged();

	int getLowerBound();

	int getUpperBound();

}