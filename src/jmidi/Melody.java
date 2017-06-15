package jmidi;

/**
 * 旋律可用音符
 * 
 * @author vermisse
 */
public class Melody {

	public static boolean get(int chord, int key) {
		int[] i = new int[0];
		if (chord == 6)
			i = new int[] { 1, 2, 3, 5, 6, 7 };
		if (chord == 5)
			i = new int[] { 1, 2, 3, 5, 7 };
		if (chord == 4)
			i = new int[] { 1, 3, 5, 6 };
		if (chord == 3)
			i = new int[] { 2, 3, 5, 7 };
		if (chord == 2)
			i = new int[] { 2, 3, 5, 6 };
		if (chord == 1)
			i = new int[] { 1, 2, 3, 5 };

		for (int item : i)
			if (item == key)
				return true;

		return false;
	}
}