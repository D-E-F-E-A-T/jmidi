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
	ShortMessage chMsg = new ShortMessage();
	SysexMessage sysMsg = new SysexMessage();

	public Play() {
		try {
			receiver = MidiSystem.getReceiver();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void melody(int key) {
		try {
			byte[] data = new byte[] { (byte) 0xF0, 0x7F, 0x7F, 4, 1, 0, 127, (byte) 0xF7 };
			sysMsg.setMessage(data, data.length);
			receiver.send(sysMsg, -1);

			chMsg.setMessage(ShortMessage.NOTE_ON, key, 127);
			receiver.send(chMsg, -1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void chord(int key) {
		try {
			byte[] data = new byte[] { (byte) 0xF0, 0x7F, 0x7F, 4, 1, 0, 90, (byte) 0xF7 };
			sysMsg.setMessage(data, data.length);
			receiver.send(sysMsg, -1);

			chMsg.setMessage(ShortMessage.NOTE_ON, key, 127);
			receiver.send(chMsg, -1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}