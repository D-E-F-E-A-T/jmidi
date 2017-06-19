package jmidi;

/**
 * 音符操作
 * 
 * @author vermisse
 */
public class Note {

	private static final int CENTER = 60;

	/**
	 * 音阶
	 */
	public static int key(int area, int note, int... tone) {
		if (tone.length == 0)
			return naturalTone(area, note);

		if (tone[0] == 1)
			return andTone(area, note);

		return 0;
	}

	/**
	 * 自然调音阶
	 */
	public static int naturalTone(int area, int note) {
		int result = CENTER - 1;
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

	/**
	 * 和声调音阶
	 */
	public static int andTone(int area, int note) {
		int result = CENTER - 1;
		result += 12 * area;
		for (int i = 0; i < note; i++) {
			switch ((i % 7) + 1) {
			case 1:
			case 4:
				result++;
				break;
			case 5:
				result += 3;
				break;
			default:
				result += 2;
			}
		}
		return result;
	}

	/**
	 * 随机数转换音符
	 */
	public static int melody(int key) {
		return (key %= 6) > 2 ? key + 2 : key + 1;
	}

	/**
	 * 生成随机数
	 */
	public static int rand(int range) {
		return (int) (Math.random() * range);
	}
}