package demo;

import javax.sound.midi.*;

/**
 * 演示音乐：D大调卡农(删减版)
 * 
 * @author vermisse
 */
public class Canon {
	
	public static void main(String[] args) throws Exception {
		int volume = 60, delay = 50;

		for (int i = 0; i < MELODY.length; i++) {
			volume = volume < 100 ? volume + 4 : volume;
			play(MELODY[i], volume, 1);
			play(CHORD[i], volume, 2);

			Thread.sleep(delay * 4);
		}

		for(int i = 0; i < LAST.length * 4 * 8; i++) {
			byte[] note = LAST[i / (4 * 8)];
			play(key(note[0], note[1]), volume + (i % (4 * 8) == 0 ? 27 : -10));

			if(i % 4 == 0)
				play(CHORD[i / 4 + MELODY.length], volume, 2);
			Thread.sleep(delay);
		}

		for(byte[] end : END)
			play(key(end[0], end[1]), 95);

		Thread.sleep(4000);
	}

	static Receiver receiver;
	static ShortMessage msg = new ShortMessage();
	static {try {receiver = MidiSystem.getReceiver(); msg.setMessage(ShortMessage.PROGRAM_CHANGE, 2, 0); receiver.send(msg, -1);} catch (Exception e) {}}

	static final byte CENTER = 62;
	
	/**
	 * 旋律
	 */
	private static final byte[][] MELODY = {
		{1,5},{},{1,3},{1,4},{1,5},{},{1,3},{1,4},{1,5},{0,5},{0,6},{0,7},{1,1},{1,2},{1,3},{1,4},
		{1,3},{},{1,1},{1,2},{1,3},{},{0,3},{0,4},{0,5},{0,6},{0,5},{0,4},{0,5},{1,1},{0,7},{1,1},
		{0,6},{},{1,1},{0,7},{0,6},{},{0,5},{0,4},{0,5},{0,4},{0,3},{0,4},{0,5},{0,6},{0,7},{1,1},
		{0,6},{},{1,1},{0,7},{1,1},{},{0,7},{1,1},{0,7},{0,6},{0,7},{1,1},{1,2},{1,3,1,1},{1,4,1,2},{1,5,1,3},

		{1,5,1,3},{},{1,3,1,1},{1,4,1,2},{1,5,1,3},{},{1,3,1,1},{1,4,1,2},{1,5,1,3},{0,5,0,7},{0,6},{0,7},{1,1},{1,2},{1,3},{1,4},
		{1,3,1,1},{},{1,1,0,6},{1,2,0,7},{1,3,1,1},{},{0,3},{0,4},{0,5},{0,6},{0,5},{0,4},{0,5},{1,1},{0,7},{1,1},
		{0,6,0,4},{},{1,1,0,6},{0,7,0,5},{0,6,0,4},{},{0,5,0,3},{0,4,0,2},{0,5,0,3},{0,4,0,2},{0,3,0,1},{0,4,0,2},{0,5,0,3},{0,6,0,4},{0,7,0,5},{1,1,0,6},
		{0,6,0,4},{},{1,1,0,6},{0,7,0,5},{1,1,0,6},{},{0,7,0,5},{1,1,0,6},{0,7,0,5},{0,6,0,4},{0,7,0,5},{1,1,0,6},{1,2,0,7},{1,3,1,1},{1,4,1,2},{1,5,1,3},

		{1,3},{},{1,1},{1,2},{1,3},{},{1,2},{1,1},{1,2},{0,7},{1,1},{1,2},{1,3},{1,2},{1,1},{0,7},
		{1,1},{},{0,6},{0,7},{1,1},{},{0,1},{0,2},{0,3},{0,4},{0,3},{0,2},{0,3},{1,1},{0,7},{1,1},
		{0,6},{},{1,1},{0,7},{0,6},{},{0,5},{0,4},{0,5},{0,4},{0,3},{0,4},{0,5},{0,6},{0,7},{1,1},
		{0,6},{},{1,1},{0,7},{1,1},{},{0,7},{0,6},{0,7},{1,1},{1,2},{1,1},{0,7},{1,1},{0,6},{0,7},
		
		{1,1},{},{-1,5},{},{0,1},{},{0,3},{},{},{},{-1,2},{},{-1,5},{},{-1,7},{},
		{},{},{-1,3},{},{-1,6},{},{0,1},{},{},{},{-1,5},{},{-1,7},{},{0,3},{},
		{},{},{-1,1},{},{-1,4},{},{-1,6},{},{},{},{-1,3},{},{-1,5},{},{0,1},{},
		{},{},{-1,2},{},{-1,6},{},{0,1},{},{},{},{-1,2},{},{-1,5},{},{-1,7},{},
		
		{},{},{1,3,1,1},{1,4,1,2},{1,5,1,3},{},{1,3,1,1},{},{},{},{0,7,0,5},{1,1,0,6},{1,2,0,7},{},{0,7,0,5},{},
		{},{0,6},{1,1,0,6},{1,2,0,7},{1,3,1,1},{},{1,1},{},{},{0,6,1,1},{1,3,0,7},{1,2,0,6},{1,1,0,6},{},{0,7,0,5},{},
		{},{0,4},{0,6,0,4},{0,7,0,5},{1,1,0,6},{},{0,6,0,4},{},{},{0,3},{0,5,0,3},{0,6,0,4},{1,1,0,4},{},{0,5,0,3},{},
		{},{0,6,0,4},{0,7,0,5},{0,7,0,5},{1,1,0,6},{},{0,6,0,4},{0,5},{},{0,5},{0,7,0,5},{1,1,0,6},{1,2,0,7},{},{0,7,0,5},{},

		{},{0,5},{1,3,1,1},{1,4,1,2},{1,5,1,3},{},{1,3,1,1},{},{},{},{1,2,0,7},{1,3,1,1},{1,4,1,2},{0,7},{1,2},{0,6},
		{},{0,6},{1,1,0,6},{1,2,0,7},{1,3,1,1},{},{1,1},{0,7},{},{0,7},{1,5,1,3},{1,4,1,2},{1,3,0,7},{0,7},{1,5,1,3},{1,1},
		{1,6,1,4},{1,1},{1,6,1,4},{1,5,1,3},{1,4},{},{1,6,1,4},{1,1},{1,5,1,3},{1,1},{1,5,1,3},{1,4},{1,3},{1,1},{1,5,1,3},{1,1},
		{1,6,1,4},{1,5,1,3},{1,4,1,1},{1,6,1,4},{1,5,1,3},{1,4,1,1},{1,6,1,4},{1,2},{1,7,1,5},{1,6,1,4},{1,2},{0,5},{0,7,0,5},{1,1,0,5},{1,2},{0,5},
		
		{1,3},{0,5},{},{0,5},{1,3,0,5},{1,2,0,5},{1,1,0,5},{1,2,0,5},{},{0,5},{},{1,3,0,5},{1,4,0,5},{1,3},{1,2},{0,3},
		{1,1,0,3},{},{},{1,1,0,3},{},{},{0,7,0,3},{1,1,0,3},{0,7,0,3},{},{0,5},{0,3},{},{0,5},{},{0,1},
		{0,6,0,4},{0,1},{},{0,7,0,5},{},{},{1,1},{0,1},{0,5},{},{},{1,1,0,5},{},{1,1,0,5},{1,1,0,5},{1,1,0,5},
		{0,6,0,4},{0,1},{},{0,1},{0,4},{0,6},{1,1},{0,2},{0,7},{0,2},{},{1,1,0,2},{},{0,2},{1,2},{0,5},
		
		{1,3},{0,5},{},{0,5},{1,3,0,5},{1,2,0,5},{1,1,0,5},{1,2,0,5},{},{0,5},{},{1,3,0,5},{1,4,0,5},{1,3},{1,2},{0,3},
		{1,2},{},{},{1,1},{},{},{0,7},{1,1},{0,3},{0,5},{0,7},{1,1},{1,3},{1,5},{1,7},{2,1},
		{1,7},{1,6},{1,5},{1,4},{1,5},{1,4},{1,3},{1,2},{1,3},{1,2},{1,1},{0,7},{1,1},{0,7},{0,6},{0,5},
		{0,6},{0,5},{0,4},{0,5},{0,6},{0,4},{1,1},{0,5},{0,7},{0,6},{0,5},{1,1},{0,5},{0,5},{1,2},{0,5},
		
		{1,3},{0,5},{},{0,5},{1,3},{0,5},{1,4},{0,5},{1,5,0,7},{0,5},{1,6,0,6},{0,5},{1,5,1,1},{0,5},{1,4,0,5},{0,3},
		{1,3,1,1},{0,3},{},{0,3},{1,1,0,3},{1,1,0,3},{1,2},{0,3},{1,3,0,5},{0,4},{1,4},{0,3},{1,3},{0,3},{1,2},{0,1},
		{0,6,0,4},{0,1},{},{0,1},{0,6},{0,7},{1,1},{0,5,0,1},{},{1,1},{},{1,1},{1,1,0,5},{1,1,0,5},{},{1,1},
		{0,6,0,4},{0,1},{},{0,1},{0,4},{0,6},{1,1},{0,2},{1,1},{0,2},{},{0,7,0,2},{},{1,1},{1,2},{0,5},
		
		{1,3,1,1},{0,5},{},{0,5},{1,3,1,1},{0,5},{1,4,1,2},{0,5},{1,5,0,7},{0,6},{1,6,1,1},{0,5},{1,5,0,7},{0,5},{1,4},{0,3},
		{1,3,1,1},{0,3},{},{0,3},{1,1,0,3},{1,1,0,3},{1,2},{0,3},{1,3,0,5},{0,4},{1,4},{0,3},{1,3,0,5},{0,3},{1,2},{0,1},
		{0,6,0,4},{0,1},{},{0,1},{0,6},{0,7},{1,1},{0,5,0,1},{},{1,1},{0,7},{1,1},{0,5,0,3},{1,1},{0,7},{1,1},
		{0,6,0,4},{1,1},{0,7},{1,1},{0,6,0,4},{1,1},{0,7},{1,1},{0,7,0,2},{0,5},{0,2},{1,1,0,2},{},{0,2},{1,2,0,7},{0,5},
	};

	/**
	 * 和弦
	 */
	private static final byte[][] CHORD = {
		{-1,1,-2,1},{},{-1,5},{},{0,1},{},{0,3},{},{-2,5},{},{-1,5},{},{-1,7},{},{0,2},{},
		{-2,6},{},{-1,3},{},{-1,6},{},{0,1},{},{-2,3},{},{-1,3},{},{-1,5},{},{-1,7},{},
		{-2,4},{},{-1,1},{},{-1,4},{},{-1,6},{},{-1,1,-2,1},{},{-1,3},{},{-1,5},{},{0,1},{},
		{-2,4},{},{-1,2},{},{-1,6},{},{0,1},{},{-2,5},{},{-1,2},{},{-1,5},{},{-1,7},{},

		{-1,1,-2,1},{},{-1,5},{},{0,1},{},{0,3},{},{-2,5},{},{-1,5},{},{-1,7},{},{0,2},{},
		{-2,6},{},{-1,3},{},{-1,6},{},{0,1},{},{-2,3},{},{-1,3},{},{-1,5},{},{-1,7},{},
		{-2,4},{},{-1,1},{},{-1,4},{},{-1,6},{},{-1,1,-2,1},{},{-1,3},{},{-1,5},{},{0,1},{},
		{-2,4},{},{-1,2},{},{-1,6},{},{0,1},{},{-2,5},{},{-1,2},{},{-1,5},{},{-1,7},{},

		{-1,1,-2,1},{},{-1,5},{},{0,1},{},{0,3},{},{-2,5},{},{-1,5},{},{-1,7},{},{0,2},{},
		{-2,6},{},{-1,3},{},{-1,6},{},{0,1},{},{-2,3},{},{-1,3},{},{-1,5},{},{-1,7},{},
		{-2,4},{},{-1,1},{},{-1,4},{},{-1,6},{},{-1,1,-2,1},{},{-1,3},{},{-1,5},{},{0,1},{},
		{-2,4},{},{-1,2},{},{-1,6},{},{0,1},{},{-2,5},{},{-1,2},{},{-1,5},{},{-1,7},{},
		
		{-1,1,-2,1},{},{},{},{},{},{},{},{-2,5,-3,5},{},{},{},{},{},{},{},
		{-2,6,-3,6},{},{},{},{},{},{},{},{-2,3,-3,3},{},{},{},{},{},{},{},
		{-2,4,-3,4},{},{},{},{},{},{},{},{-1,1,-2,1},{},{},{},{},{},{},{},
		{-2,4,-3,4},{},{},{},{},{},{},{},{-2,5,-3,5},{},{},{},{},{},{},{},
		
		{-1,1,-2,1},{},{-1,5},{},{0,1},{},{0,3},{},{-2,5,-3,5},{},{-1,2},{},{-1,5},{},{-1,7},{},
		{-2,6,-3,6},{},{-1,3},{},{-1,6},{},{0,1},{},{-1,3,-2,3},{},{-1,5},{},{-1,7},{},{0,3},{},
		{-2,4,-3,4},{},{-1,1},{},{-1,4},{},{-1,6},{},{-1,1,-2,1},{},{-1,3},{},{-1,5},{},{0,1},{},
		{-2,4,-3,4},{},{-1,2},{},{-1,6},{},{0,1},{},{-2,5,-3,5},{},{-1,2},{},{-1,5},{},{-1,7},{},
		
		{-1,1,-2,1},{},{-1,5},{},{0,1},{},{0,3},{},{-2,5,-3,5},{},{-1,2},{},{-1,5},{},{-1,7},{},
		{-2,6,-3,6},{},{-1,3},{},{-1,6},{},{0,1},{},{-1,3,-2,3},{},{-1,5},{},{-1,7},{},{0,3},{},
		{-2,4,-3,4},{},{-1,1},{},{-1,4},{},{-1,6},{},{-1,1,-2,1},{},{-1,3},{},{-1,5},{},{0,1},{},
		{-2,4,-3,4},{},{-1,2},{},{-1,6},{},{0,1},{},{-2,5,-3,5},{},{-1,2},{},{-1,5},{},{-1,7},{},
		
		{-1,1,-2,1},{},{-1,5},{},{0,1},{},{0,3},{},{-2,5,-3,5},{},{-1,2},{},{-1,5},{},{-1,7},{},
		{-2,6,-3,6},{},{-1,3},{},{-1,6},{},{0,1},{},{-1,3,-2,3},{},{-1,5},{},{-1,7},{},{0,3},{},
		{-2,4,-3,4},{},{-1,1},{},{-1,4},{},{-1,6},{},{-1,1,-2,1},{},{-1,3},{},{-1,5},{},{0,1},{},
		{-2,4,-3,4},{},{-1,2},{},{-1,6},{},{0,1},{},{-2,5,-3,5},{},{-1,2},{},{-1,5},{},{-1,7},{},
		
		{-1,1,-2,1},{},{-1,5},{},{0,1},{},{0,3},{},{-2,5,-3,5},{},{-1,2},{},{-1,5},{},{-1,7},{},
		{-2,6,-3,6},{},{-1,3},{},{-1,6},{},{0,1},{},{-1,3,-2,3},{},{-1,5},{},{-1,7},{},{0,3},{},
		{-2,4,-3,4},{},{-1,1},{},{-1,4},{},{-1,6},{},{-1,1,-2,1},{},{-1,3},{},{-1,5},{},{0,1},{},
		{-2,4,-3,4},{},{-1,2},{},{-1,6},{},{0,1},{},{-2,5,-3,5},{},{-1,2},{},{-1,5},{},{-1,7},{},
		
		{-1,1,-2,1},{},{-1,5},{},{0,1},{},{0,3},{},{-2,5,-3,5},{},{-1,2},{},{-1,5},{},{-1,7},{},
		{-2,6,-3,6},{},{-1,3},{},{-1,6},{},{0,1},{},{-1,3,-2,3},{},{-1,5},{},{-1,7},{},{0,3},{},
		{-2,4,-3,4},{},{-1,1},{},{-1,4},{},{-1,6},{},{-1,1,-2,1},{},{-1,3},{},{-1,5},{},{0,1},{},
		{-2,4,-3,4},{},{-1,2},{},{-1,6},{},{0,1},{},{-2,5,-3,5},{},{-1,2},{},{-1,5},{},{-1,7},{},
		
		{-1,1,-2,1},{},{-1,5},{},{0,1},{},{0,3},{},{-2,5,-3,5},{},{-1,2},{},{-1,5},{},{-1,7},{},
		{-2,6,-3,6},{},{-1,3},{},{-1,6},{},{0,1},{},{-1,3,-2,3},{},{-1,5},{},{-1,7},{},{0,3},{},
		{-2,4,-3,4},{},{-1,1},{},{-1,4},{},{-1,6},{},{-1,1,-2,1},{},{-1,3},{},{-1,5},{},{0,1},{},
		{-2,4,-3,4},{},{-1,2},{},{-1,6},{},{0,1},{},{-2,5,-3,5},{},{-1,2},{},{-1,5},{},{-1,7},{},
		
		{-1,1,-2,1},{},{-1,5},{},{0,1},{},{0,3},{},{-2,5,-3,5},{},{-1,2},{},{-1,5},{},{-1,7},{},
		{-2,6,-3,6},{},{-1,3},{},{-1,6},{},{0,1},{},{-1,3,-2,3},{},{-1,5},{},{-1,7},{},{0,3},{},
		{-2,4,-3,4},{},{-1,1},{},{-1,4},{},{-1,6},{},{-1,1,-2,1},{},{-1,3},{},{-1,5},{},{0,1},{},
		{-2,4,-3,4},{},{-1,2},{},{-1,6},{},{0,1},{},{-2,5,-3,5},{},{-1,2},{},{-1,5},{},{-1,7},{},
		
		{-1,1,-2,1},{},{-1,5},{},{0,1},{},{0,3},{},{-2,5,-3,5},{},{-1,2},{},{-1,5},{},{-1,7},{},
		{-2,6,-3,6},{},{-1,3},{},{-1,6},{},{0,1},{},{-1,3,-2,3},{},{-1,5},{},{-1,7},{},{0,3},{},
		{-2,4,-3,4},{},{-1,1},{},{-1,4},{},{-1,6},{},{-1,1,-2,1},{},{-1,3},{},{-1,5},{},{0,1},{},
		{-2,4,-3,4},{},{-1,2},{},{-1,6},{},{0,1},{},{-2,5,-3,5},{},{-1,2},{},{-1,5},{},{-1,7},{},
	};

	/**
	 * 32分音符区
	 */
	private static final byte[][] LAST = {
		{1,3},{1,2},{1,1},{0,7},{0,6},{0,5},{0,6},{0,7},
		{1,3},{1,2},{1,1},{0,7},{0,6},{0,5},{0,6},{0,7},
	};

	/**
	 * 结束
	 */
	private static final byte[][] END = {
		{1,1},{0,3},{-1,1},{-1,5}
	};

	static void play(byte key, int volume) throws Exception {
		msg.setMessage(ShortMessage.NOTE_ON, key, volume);
		receiver.send(msg, -1);
	}

	static void play(byte[] note, int volume, int type) throws Exception {
		if (note.length > 0) play(key(note[0], note[1]), type == 1 ? volume + 27 : volume);
		if (note.length > 2) play(key(note[2], note[3]), type == 1 ? volume + 10 : volume);
	}

	/**
	 * area第几组，note是音符：1 2 3 4 5 6 7 / do re mi fa so la si，过滤掉了半音
	 */
	static byte key(byte area, byte note) {
		byte result = CENTER - 1;
		result += 12 * area;
		for (byte i = 0; i < note; i++) {
			switch (i % 7 + 1) {
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