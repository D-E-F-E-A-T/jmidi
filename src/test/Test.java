package test;

import javax.sound.midi.*;

/**
 * 演示音乐
 * 
 * @author vermisse
 */
public class Test {
	
	public static void main(String[] args) throws Exception {
		Receiver receiver = MidiSystem.getReceiver();
		ShortMessage chMsg = new ShortMessage();
		SysexMessage sysMsg = new SysexMessage();
		
		for (int i = 0; i < MELODY.length; i++) {
			byte[] data = new byte[] { (byte) 0xF0, 0x7F, 0x7F, 4, 1, 0, 127, (byte) 0xF7 };
			sysMsg.setMessage(data, data.length);
			receiver.send(sysMsg, -1);
			
			int[] note = MELODY[i];
			if(note[1] != 0){
				// chMsg.setMessage()第二个参数是音符
				chMsg.setMessage(ShortMessage.NOTE_ON, key(note[0], note[1]), 127);
				receiver.send(chMsg, -1);
			}
			
			note = CHORD[i];
			if (note[1] != 0) {
				// data倒数第二个参数是音量
				data = new byte[] { (byte) 0xF0, 0x7F, 0x7F, 4, 1, 0, 90, (byte) 0xF7 };
				sysMsg.setMessage(data, data.length);
				receiver.send(sysMsg, -1);
				
				chMsg.setMessage(ShortMessage.NOTE_ON, key(note[0], note[1]), 127);
				receiver.send(chMsg, -1);
			}
			
			Thread.sleep(300);
		}
	}

	private static final int CENTER = 59;
	
	// 旋律
	private static final int[][] MELODY = new int[][] {
		{2,3},{0,0},{2,3},{2,4},
		{2,3},{0,0},{2,2},{2,1},
		{2,2},{0,0},{2,5},{2,2},
		{2,2},{0,0},{0,0},{0,0},

		{2,1},{0,0},{2,1},{2,2},
		{2,1},{0,0},{1,7},{1,6},
		{1,7},{0,0},{2,3},{1,7},
		{1,7},{0,0},{0,0},{0,0},

		{1,6},{0,0},{2,2},{2,3},
		{2,2},{2,1},{1,6},{0,0},
		{1,5},{0,0},{2,2},{2,3},
		{2,2},{2,1},{1,6},{0,0},

		{1,6},{0,0},{2,2},{2,3},
		{2,2},{2,1},{2,3},{2,3},
		{0,0},{2,2},{0,0},{0,0},
		{0,0},{0,0},{0,0},{0,0},
		{2,3},{0,0},{2,3},{2,4},
		{2,3},{0,0},{2,2},{2,1},
		{2,2},{0,0},{2,5},{2,2},
		{2,2},{0,0},{0,0},{0,0},

		{2,1},{0,0},{2,1},{2,2},
		{2,1},{0,0},{1,7},{1,6},
		{1,7},{0,0},{2,3},{1,7},
		{1,7},{0,0},{0,0},{0,0},

		{1,6},{0,0},{2,2},{2,3},
		{2,2},{2,1},{1,6},{0,0},
		{1,5},{0,0},{2,2},{2,3},
		{2,2},{2,1},{1,6},{0,0},

		{1,6},{0,0},{2,2},{2,3},
		{2,2},{2,1},{1,6},{1,7},
		{0,0},{2,1},{0,0},{0,0},
		{0,0},{0,0},{0,0},{0,0},
	};

	// 和弦
	private static final int[][] CHORD = new int[][] {
		{0,1},{0,5},{1,1},{1,2},
		{1,3},{0,0},{1,5},{0,0},
		{-1,5},{0,2},{0,5},{0,7},
		{1,2},{0,7},{0,5},{0,2},

		{-1,6},{0,3},{0,6},{0,7},
		{1,1},{0,0},{1,3},{0,0},
		{-1,3},{-1,7},{0,3},{0,5},
		{0,7},{1,2},{0,7},{0,3},

		{-1,4},{0,1},{0,4},{0,6},
		{1,1},{0,0},{0,4},{0,0},
		{0,1},{0,5},{1,1},{1,2},
		{1,3},{0,0},{1,5},{0,0},

		{-1,4},{0,1},{0,4},{0,6},
		{1,1},{0,0},{0,4},{0,0},
		{-1,5},{0,2},{0,5},{0,6},
		{0,7},{1,2},{1,3},{1,5},

		{0,1},{0,5},{1,1},{1,2},
		{1,3},{0,0},{1,5},{0,0},
		{-1,5},{0,2},{0,5},{0,7},
		{1,2},{0,7},{0,5},{0,2},

		{-1,6},{0,3},{0,6},{0,7},
		{1,1},{0,0},{1,3},{0,0},
		{-1,3},{-1,7},{0,3},{0,5},
		{0,7},{1,5},{1,3},{0,7},

		{-1,4},{0,1},{0,4},{0,6},
		{1,1},{0,0},{0,4},{0,0},
		{0,1},{0,5},{1,1},{1,2},
		{1,3},{0,0},{1,5},{0,0},

		{-1,4},{0,1},{0,4},{0,6},
		{-1,5},{0,2},{0,7},{1,2},
		{0,1},{0,5},{1,1},{1,3},
		{1,5},{1,3},{1,1},{0,5},
	};

	// area第几组，note是音符，1234567，过滤掉了半音
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