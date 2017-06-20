package jmidi;

import java.io.*;

import javax.sound.midi.*;

/**
 * 播放
 * 
 * @author vermisse
 */
public class Play {
	
	private static Sequence sequence;
	
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
	
	public Play(File file){
		try {
			// 从本地文件加载midi
			sequence = MidiSystem.getSequence(file);
			// 从url加载midi
			// sequence = MidiSystem.getSequence(new URL("http://hostname/midifile"));
			// Create a sequencer for the sequence
			final Sequencer sequencer = MidiSystem.getSequencer();
			sequencer.open();
			sequencer.setSequence(sequence);
			
			Thread.sleep(500);
			// Start playing
			sequencer.start();
			sequencer.addMetaEventListener(new MetaEventListener() {
				public void meta(MetaMessage event) {
					// 播放完毕
					if (event.getType() == 47) {
						System.exit(0);
					}
				}
			});
		} catch (Exception e) {}
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