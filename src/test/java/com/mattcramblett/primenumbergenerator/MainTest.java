package com.mattcramblett.primenumbergenerator;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MainTest extends AbstractTest {

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	private final PrintStream originalErr = System.err;

	@Before
	public void redirectStandardOutput() throws Exception {
		System.setOut(new PrintStream(this.outContent));
		System.setErr(new PrintStream(this.errContent));
	}

	@After
	public void restoreStandardOutput() throws Exception {
		System.setOut(this.originalOut);
		System.setErr(this.originalErr);
	}

	@Test(expected = NumberFormatException.class)
	public void testOutputWithInvalidRangeParameters() {
		final String args[] = { "1,00", "a" };
		Main.main(args);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testOutputWithoutRangeParameters() {
		final String args[] = {};
		Main.main(args);
	}

	@Test
	public void testOutputResultWithValidRangeParameters() {
		final String args[] = { "-100", "102" };
		Main.main(args);
		assertEquals(SMALL_PRIMES.toString(), this.outContent.toString().trim());
	}

	@Test
	public void testOutputWhenResultIsEmptyWithValidRangeParameters() {
		final String args[] = { "0", "0" };
		Main.main(args);
		assertEquals(Arrays.asList().toString(), this.outContent.toString().trim());
	}

}
