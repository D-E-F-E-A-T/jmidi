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
		ShortMessage msg = new ShortMessage();
		SysexMessage sys = new SysexMessage();
		
		msg.setMessage(ShortMessage.PROGRAM_CHANGE, 8, 0);
		receiver.send(msg, -1);
		
		for (int i = 0; i < MELODY.length; i++) {
			byte[] data = new byte[] { (byte) 0xF0, 0x7F, 0x7F, 4, 1, 0, 127, (byte) 0xF7 };
			
			int[] note = MELODY[i];
			if(note.length != 0){
				sys.setMessage(data, data.length);
				receiver.send(sys, -1);
				
				msg.setMessage(ShortMessage.NOTE_ON, key(note[0], note[1]), 127);
				receiver.send(msg, -1);
			}
			
			note = CHORD[i];
			if (note.length != 0) {
				data[6] = 90;
				sys.setMessage(data, data.length);
				receiver.send(sys, -1);
				
				msg.setMessage(ShortMessage.NOTE_ON, key(note[0], note[1]), 127);
				receiver.send(msg, -1);
			}
			
			Thread.sleep(300);
		}
	}

	private static final int CENTER = 60;
	
	/**
	 * 旋律
	 */
	private static final int[][] MELODY = new int[][] {
		{2,3},{},{2,3},{2,4},{2,3},{},{2,2},{2,1},
		{2,2},{},{2,5},{2,2},{2,2},{},{},{},
		{2,1},{},{2,1},{2,2},{2,1},{},{1,7},{1,6},
		{1,7},{},{2,3},{1,7},{1,7},{},{},{},
		{1,6},{},{2,2},{2,3},{2,2},{2,1},{1,6},{},
		{1,5},{},{2,2},{2,3},{2,2},{2,1},{1,6},{},
		{1,6},{},{2,2},{2,3},{2,2},{2,1},{2,3},{2,3},
		{},{2,2},{},{},{},{},{},{},
		
		{2,3},{},{2,3},{2,4},{2,3},{},{2,2},{2,1},
		{2,2},{},{2,5},{2,2},{2,2},{},{},{},
		{2,1},{},{2,1},{2,2},{2,1},{},{1,7},{1,6},
		{1,7},{},{2,3},{1,7},{1,7},{},{},{},
		{1,6},{},{2,2},{2,3},{2,2},{2,1},{1,6},{},
		{1,5},{},{2,2},{2,3},{2,2},{2,1},{1,6},{},
		{1,6},{},{2,2},{2,3},{2,2},{2,1},{1,6},{1,7},
		{},{2,1},{},{},{},{},{},{},
	};

	/**
	 * 和弦
	 */
	private static final int[][] CHORD = new int[][] {
		{0,1},{0,5},{1,1},{1,2},{1,3},{},{1,5},{},
		{-1,5},{0,2},{0,5},{0,7},{1,2},{0,7},{0,5},{0,2},
		{-1,6},{0,3},{0,6},{0,7},{1,1},{},{1,3},{},
		{-1,3},{-1,7},{0,3},{0,5},{0,7},{1,2},{0,7},{0,3},
		{-1,4},{0,1},{0,4},{0,6},{1,1},{},{0,4},{},
		{0,1},{0,5},{1,1},{1,2},{1,3},{},{1,1},{},
		{-1,4},{0,1},{0,4},{0,6},{1,1},{},{0,4},{},
		{-1,5},{0,2},{0,5},{0,6},{0,7},{1,2},{1,3},{1,5},

		{0,1},{0,5},{1,1},{1,2},{1,3},{},{1,5},{},
		{-1,5},{0,2},{0,5},{0,7},{1,2},{0,7},{0,5},{0,2},
		{-1,6},{0,3},{0,6},{0,7},{1,1},{},{1,3},{},
		{-1,3},{-1,7},{0,3},{0,5},{0,7},{1,5},{1,3},{0,7},
		{-1,4},{0,1},{0,4},{0,6},{1,1},{},{0,4},{},
		{0,1},{0,5},{1,1},{1,2},{1,3},{},{1,1},{},
		{-1,4},{0,1},{0,4},{0,6},{-1,5},{0,2},{0,7},{1,2},
		{0,1},{0,5},{1,1},{1,3},{1,5},{1,3},{1,1},{0,5},
	};

	// area第几组，note是音符：1 2 3 4 5 6 7 / do re mi fa so la si，过滤掉了半音
	public static int key(int area, int note) {
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
}