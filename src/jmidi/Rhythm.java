package jmidi;

public class Rhythm {

	public static int[] get(int type) {
		if (type == 0)
			return new int[] { 1, 0, 1, 0, 1, 0, 1, 0 };
		if (type == 1)
			return new int[] { 1, 0, 1, 1, 0, 1, 0, 1 };
		if (type == 2)
			return new int[] { 1, 1, 1, 1, 0, 0, 1, 0 };

		return new int[0];
	}
}