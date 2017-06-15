package test;

import jmidi.Chord;
import jmidi.Melody;
import jmidi.Note;
import jmidi.Path;
import jmidi.Play;
import jmidi.Rhythm;

/**
 * 自动生成
 * 
 * @author vermisse
 */
public class Auto {

	public static void main(String[] args) throws Exception {
		Play play = new Play();

		int section = 1, prev = 9, type = rand(Rhythm.data.length);
		int[] rhythm = Rhythm.get(type), path = Path.get(rand(Path.data.length)); // 节奏型和走向

		while (true) {
			int chd = 0;
			for (int i = 0; i < rhythm.length; i++) {
				int key = rand(15); // 随机生成音符
				for (int j = 0; j < 2; j++) {
					if (!chk(key, prev, path[section - 1])) {
						key = rand(15); // 如果生成的音符不符合模板，重新生成，有2次机会
					} else {
						int range = key / 6, melody = melody(key); // 旋律
						prev = key;

						play.melody(Note.key(range, melody));
						break;
					}
				}

				int[][] chords = Chord.get(path[section - 1]); // 和弦
				int[] chord = chords[chd++ % chords.length];
				if (rhythm[i] == 1)
					play.chord(Note.key(chord[0], chord[1]));

				Thread.sleep(260);
			}
			section = (section == path.length) ? 1 : section + 1;
		}
	}

	private static boolean chk(int key, int prev, int path) {
		return key - prev < 5 && key - prev > -5 && Melody.get(path, melody(key));
	}

	private static int rand(int range) {
		return (int) (Math.random() * range);
	}

	private static int melody(int key) {
		key = key % 6;
		return key > 3 ? key + 2 : key + 1;
	}
}