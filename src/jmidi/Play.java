package jmidi;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.SysexMessage;

/**
 * 播放
 * 
 * @author vermisse
 */
public class Play {

	Receiver receiver;
	ShortMessage msg = new ShortMessage();
	SysexMessage sys = new SysexMessage();

	public Play() {
		try {
			receiver = MidiSystem.getReceiver();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Play(int timbre) {
		try {
			receiver = MidiSystem.getReceiver();
			
			msg.setMessage(ShortMessage.PROGRAM_CHANGE, timbre, 0);
	        receiver.send(msg, -1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void melody(int key) {
		base((byte) 127, key);
	}

	public void chord(int key) {
		base((byte) 90, key);
	}
	
	private void base(byte volume, int key){
		try {
			byte[] data = new byte[] { (byte) 0xF0, 0x7F, 0x7F, 4, 1, 0, volume, (byte) 0xF7 };
			sys.setMessage(data, data.length);
			receiver.send(sys, -1);

			msg.setMessage(ShortMessage.NOTE_ON, key, 127);
			receiver.send(msg, -1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}