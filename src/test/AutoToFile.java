package test;

import java.io.*;

import common.*;
import jmidi.*;

public class AutoToFile {
	
	public static void main(String... args) throws Exception {
        File file = new File("d://test.mid");
        
		int section = 1; // 当前第几小节
		int prev = 9; // 上一个音符，初始化设置为9
		int range = 15; // 随机生成的音符范围
		int[] rhythm = Rhythm.get(rand(Rhythm.data.length)); // 随机选择节奏型
		int[] path = Path.get(rand(Path.data.length)); // 随机选择走向
		
		int bpm = 120;
		int max = 64; //生成多少小节
		
        Sequence sequence = new Sequence();
        Track main = sequence.createTrack();
        main.add(new MidiEvent(MetaMessage.tempoMessage(bpm), 0));
        
        Track trackMelody = sequence.createTrack();
        Track trackChord = sequence.createTrack();
        
        int index = 0;
		while (index++ < max) {
			for (int i = 0, chd = 0; i < rhythm.length; i++) {
				int tmp = (bpm - 30) * (index * rhythm.length + i);
				
				// 旋律区
				{
					for (int count = 0, key = rand(range); count < 3 || section == 1; count++) {
						if (chk(key, prev, path[section - 1])) {
							int area = (prev = key) / 6; // 转换成区域
							int melody = melody(key); // 转换成音符
							
							trackMelody.add(Note.key(area, melody), tmp, 127);
							break;
						}
						key = rand(range); // 生成的音符不符合规范，重新生成，有3次机会，如果是第一小节，必须全部生成
					}
				}
				// 和弦区
				{
					if (rhythm[i] == 1) {
						int[][] chords = Chord.get(path[section - 1]);
						int[] chord = chords[chd++ % chords.length];

						trackChord.add(Note.key(chord[0], chord[1]), tmp, 95);
					}
				}
			}
			section = (section == path.length) ? 1 : section + 1;
		}
        
        FileOutputStream out = new FileOutputStream(file);
        MidiFileWriter.write(sequence, out);
        
        new Play(file);
	}

	private static int melody(int key) {
		return (key %= 6) > 2 ? key + 2 : key + 1;
	}

	private static boolean chk(int key, int prev, int path) {
		return key - prev < 5 && key - prev > -5 && key != prev && Melody.get(path, melody(key));
	}

	private static int rand(int range) {
		return (int) (Math.random() * range);
	}
}