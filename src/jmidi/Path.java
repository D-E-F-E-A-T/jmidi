package jmidi;

public class Path {

	public static int[] get(int seed) {
		if (seed == 0)
			return new int[] { 4, 5, 3, 6 };
		if (seed == 1)
			return new int[] { 6, 4, 1, 5 };
		if (seed == 2)
			return new int[] { 6, 4, 5, 3 };
		
		return new int[0];
	}
}