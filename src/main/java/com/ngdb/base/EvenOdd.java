package com.ngdb.base;

public class EvenOdd {

	private int evenOdd = 0;

	public String next() {
		evenOdd++;
		if (evenOdd % 2 == 0) {
			return "odd";
		}
		return "even";
	}

}
