package jmidi;

import javax.sound.midi.*;

public class Test {

	public static void main(String[] args) throws Exception {
		Receiver receiver = MidiSystem.getReceiver();
		ShortMessage chMsg = new ShortMessage();
		SysexMessage sysMsg = new SysexMessage();

		int section = 1;
		int prev = 9;

		int type = rand(3);
		int[] rhythm = Rhythm.get(type); // 节奏型

		type = rand(3);
		int[] path = Path.get(type); // 走向

		while (true) {
			for (int i = 0; i < rhythm.length; i++) {
				int[][] chord = Chord.get(path[section - 1]); // 和弦

				int key = rand(15); // 随机生成音符
				for (int j = 0; j < 5; j++)
					if (!(key - prev < 5 && key - prev > -5) || !Melody.get(type, melody(key)))
						key = rand(15); // 如果生成的音符不符合模板，重新生成，有5次机会
					else
						break;

				byte[] data = new byte[] { (byte) 0xF0, 0x7F, 0x7F, 4, 1, 0, 127, (byte) 0xF7 };
				sysMsg.setMessage(data, data.length);
				receiver.send(sysMsg, -1);

				if (key - prev < 5 && key - prev > -5) {
					int range = key / 6;
					int melody = melody(key); // 旋律
					
					if (Melody.get(path[section - 1], melody)) {
						prev = key;
						
						chMsg.setMessage(ShortMessage.NOTE_ON, Note.key(range, melody), 127);
						receiver.send(chMsg, -1);
					}
				}
				
				if (rhythm[i] == 1) {
					data = new byte[] { (byte) 0xF0, 0x7F, 0x7F, 4, 1, 0, 100, (byte) 0xF7 };
					sysMsg.setMessage(data, data.length);
					receiver.send(sysMsg, -1);

					chMsg.setMessage(ShortMessage.NOTE_ON,
							Note.key(chord[i % chord.length][0], chord[i % chord.length][1]), 127);
					receiver.send(chMsg, -1);
				}
				Thread.sleep(240);
			}
			section = (section == 4) ? 1 : section + 1;
		}
	}

	private static int rand(int range) {
		return (int) (Math.random() * range);
	}
	
	private static int melody(int key){
		key = key % 6;
		return key > 3 ? key + 2 : key + 1;
	}
}