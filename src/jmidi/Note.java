package jmidi;

/**
 * 音符操作
 * 
 * @author vermisse
 */
public class Note {

	private static final int CENTER = 59;

	public static int key(int area, int note) {
		int result = CENTER;
		result += 12 * area;
		for (int i = 0; i < note; i++) {
			switch ((i % 7) + 1) {
			case 1:
			case 4:
				result++;
				break;
			default:
				result += 2;
			}
		}
		return result;
	}
}