package test;

import java.io.*;

import common.*;
import jmidi.*;

/**
 * 自动作曲，生成到文件并播放
 * 
 * @author vermisse
 */
public class AutoToFile {

	public static void main(String... args) throws Exception {
		File file = new File("d://test.mid");

		int section = 1; // 当前第几小节，用于选择走向
		int prev = 9; // 上一个音符，初始化设置为9
		int range = 15; // 随机生成的音符范围
		int[] rhythm = Rhythm.rand(); // 随机选择节奏型
		int[] path = Path.rand(); // 随机选择走向

		int bpm = 120; // 速度，类库要求，不清楚具体用途
		int velocity = 25; // 这个参数决定速度，不能大于bpm
		int max = 64; // 生成多少小节，最好是8的倍数

		Sequence seq = new Sequence();
		Track main = seq.createTrack();
		main.add(new MidiEvent(MetaMessage.tempoMessage(bpm), 0));

		Track trackMelody = seq.createTrack();
		Track trackChord = seq.createTrack();

		prev = must(trackMelody, range, prev, path[section - 1], velocity);
		prev = must(trackMelody, range, prev, path[section - 1], bpm);
		
		int index = 0;
		do {
			for (int i = 0, chd = 0; i < rhythm.length; i++) {
				int pos = (bpm - velocity) * ((index * rhythm.length + i) + 2) + velocity; // 计算音符时间
				
				// 旋律区
				{
					int[] count = (section == 1) ? new int[0] : new int[] { 3 };
					prev = must(trackMelody, range, prev, path[section - 1], pos, count);
				}
				// 和弦区
				{
					if (rhythm[i] == 1) {
						int[][] chords = Chord.get(path[section - 1]);
						int[] chord = chords[chd++ % chords.length];

						trackChord.add(Note.key(chord[0], chord[1]), pos, 95);
					}
				}
			}
			section = (section == path.length) ? 1 : section + 1;
		} while (index++ < max);

		FileOutputStream out = new FileOutputStream(file);
		MidiFileWriter.write(seq, out);

		new Play(file);
	}
	
	private static boolean chk(int key, int prev, int path) {
		return key - prev < 5 && key - prev > -5 && key != prev && Melody.get(path, Note.melody(key));
	}
	
	/**
	 * 生成音符
	 * 
	 * @param track 音轨
	 * @param range 区域
	 * @param prev 上一个key
	 * @param path 根音
	 * @param pos 时间轴上的位置
	 * @param count 重新生成次数，如果不传，一直生成，直到合法为止
	 * @return
	 * @throws Exception
	 */
	private static int must(Track track, int range, int prev, int path, int pos, int... count) throws Exception {
		int i = 0;
		int max = (count.length > 0) ? count[0] : 0;
		
		do {
			int key = Note.rand(range);
			if (chk(key, prev, path)) {
				int area = key / 6; // 转换成区域
				int melody = Note.melody(key); // 转换成音符

				track.add(Note.key(area, melody), pos, 127);
				return key;
			}
		} while (i++ < max || count.length == 0);
		
		return prev;
	}
}