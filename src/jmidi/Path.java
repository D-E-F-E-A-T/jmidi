package jmidi;

/**
 * 和弦走向
 * 
 * @author vermisse
 */
public class Path {
	
	public static int[][] data = new int[][]{
		{ 4, 5, 3, 6 },
		{ 6, 4, 1, 5 },
		{ 6, 5, 4, 3, 2, 3, 4, 5 },
		{ 1, 5, 6, 3, 4, 1, 4, 5 },
		{ 1, 6, 4, 5, 3, 6, 4, 5 }
	};
	
	public static int[] get(int seed) {
		if(seed < data.length)
			return data[seed];
		
		return new int[0];
	}
}