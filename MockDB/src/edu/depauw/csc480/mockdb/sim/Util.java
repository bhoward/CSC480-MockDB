package edu.depauw.csc480.mockdb.sim;

import java.util.Random;

public class Util {
	private static Random rng = new Random();

	public static String randomName() {
		// TODO Auto-generated method stub
		return null;
	}

	public static double randomExp(double mean) {
		return -mean * Math.log(rng.nextDouble());
	}

}
