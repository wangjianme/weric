package com.topsen.financial.util.support.format;


public class TestRMB {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		RMB.isDebug = true;
		double d = 5000.34;
		System.err.println(RMB.number2UpperCase( d));
	}

}
