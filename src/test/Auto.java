package test;

import jmidi.*;

/**
 * 自动作曲
 * 
 * @author vermisse
 */
@Deprecated
public class Auto {

	public static void main(String[] args) throws Exception {
		Play play = new Play(2); // 设置音色

		int section = 1; // 当前第几小节
		int prev = 9; // 上一个音符，初始化设置为9
		int range = 15; // 随机生成的音符范围
		int[] rhythm = Rhythm.get(Note.rand(Rhythm.size())); // 随机选择节奏型
		int[] path = Path.get(Note.rand(Path.size())); // 随机选择走向

		while (true) {
			for (int i = 0, chd = 0; i < rhythm.length; i++) {
				// 旋律区
				{
					for (int count = 0, key = Note.rand(range); count < 3 || section == 1; count++) {
						if (chk(key, prev, path[section - 1])) {
							int area = (prev = key) / 6; // 转换成区域
							int melody = Note.melody(key); // 转换成音符
							
							play.melody(Note.key(area, melody)); // 播放旋律
							break;
						}
						key = Note.rand(range); // 生成的音符不符合规范，重新生成，有3次机会，如果是第一小节，必须全部生成
					}
				}
				// 和弦区
				{
					if (rhythm[i] == 1) {
						int[][] chords = Chord.get(path[section - 1]);
						int[] chord = chords[chd++ % chords.length];
						
						play.chord(Note.key(chord[0], chord[1])); // 播放和弦
					}
				}
				Thread.sleep(260);
			}
			section = (section == path.length) ? 1 : section + 1;
		}
	}

	public static boolean chk(int key, int prev, int path) {
		return key - prev < 5 && key - prev > -5 && key != prev && Melody.get(path, Note.melody(key));
	}
}