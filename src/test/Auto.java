package test;

import jmidi.*;

/**
 * 自动作曲
 * 
 * @author vermisse
 */
public class Auto {

	public static void main(String[] args) throws Exception {
		Play play = new Play(2);

		int section = 1, prev = 9, type = rand(Rhythm.data.length);
		int[] rhythm = Rhythm.get(type), path = Path.get(rand(Path.data.length)); // 节奏型和走向

		while (true) {
			for (int i = 0, chd = 0; i < rhythm.length; i++) {
				for (int count = 0, key = rand(15); count < 3 || section == 1; count++) {
					if (chk(key, prev, path[section - 1])) {
						int range = (prev = key) / 6, melody = melody(key); // 旋律

						play.melody(Note.key(range, melody));
						break;
					}
					key = rand(15); // 生成的音符不符合规范，重新生成，有3次机会，如果是第一小节，必须全部生成
				}
				if (rhythm[i] == 1) {
					int[][] chords = Chord.get(path[section - 1]); // 和弦
					int[] chord = chords[chd++ % chords.length];
					play.chord(Note.key(chord[0], chord[1]));
				}
				Thread.sleep(260);
			}
			section = (section == path.length) ? 1 : section + 1;
		}
	}

	private static int melody(int key) {
		return (key %= 6) > 3 ? key + 2 : key + 1;
	}

	private static boolean chk(int key, int prev, int path) {
		return key - prev < 5 && key - prev > -5 && key != prev && Melody.get(path, melody(key));
	}

	private static int rand(int range) {
		return (int) (Math.random() * range);
	}
}