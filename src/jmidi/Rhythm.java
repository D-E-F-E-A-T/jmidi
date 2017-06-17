package jmidi;

public class Rhythm {

	public static int[][] data = new int[][] {
		{ 1, 0, 1, 0, 1, 0, 1, 0 },
		{ 1, 0, 1, 1, 0, 1, 0, 1 },
		{ 1, 1, 1, 1, 0, 0, 1, 0 },
		{ 1, 0, 1, 1, 0, 1, 1, 1 },
		{ 1, 1, 1, 1, 0, 1, 1, 0 },
		{ 1, 0, 1, 1, 1, 1 },
	};

	public static int[] get(int type) {
		if(type < data.length)
			return data[type];
		
		return new int[0];
	}
}