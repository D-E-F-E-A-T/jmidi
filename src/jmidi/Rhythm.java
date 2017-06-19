package jmidi;

public class Rhythm {

	private static final int[][] data = new int[][] {
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
	
	public static int size(){
		return data.length;
	}
}