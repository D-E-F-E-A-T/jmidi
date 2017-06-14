package jmidi;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Synthesizer;

public class Demo {
	private static String midiFile = "F:\\BaiduNetdiskDownload\\test.mid"; //mid文件路径
	private static String midiURI = "http://hostname/midifile";
	private static Sequence sequence = null;

	public static void main(String[] args) {
		try {
			// 从本地文件加载midi
			sequence = MidiSystem.getSequence(new File(midiFile));
			// 从url加载midi
			// sequence = MidiSystem.getSequence(new URL("http://hostname/midifile"));
			// Create a sequencer for the sequence
			Sequencer sequencer = MidiSystem.getSequencer();
			sequencer.open();
			sequencer.setSequence(sequence);
			// Determining the Duration of a Midi Audio File
			double durationInSecs = sequencer.getMicrosecondLength() / 1000000.0;
			System.out.println("the duration of this audio is " + durationInSecs + "secs.");
			// Determining the Position of a Midi Sequencer
			double seconds = sequencer.getMicrosecondPosition() / 1000000.0;
			System.out.println("the Position of this audio is " + seconds + "secs.");
			// Setting the Volume of Playing Midi Audio
			if (sequencer instanceof Synthesizer) {
				Synthesizer synthesizer = (Synthesizer) sequencer;
				MidiChannel[] channels = synthesizer.getChannels();
				// gain is a value between 0 and 1 (loudest)
				double gain = 0.9D;
			}
			// Start playing
			sequencer.start();
			// Determining the Position of a Midi Sequencer
			Thread.currentThread().sleep(5000);
			seconds = sequencer.getMicrosecondPosition() / 1000000.0;
			System.out.println("the Position of this audio is " + seconds + "secs.");
			// Add a listener for meta. message events
			sequencer.addMetaEventListener(new MetaEventListener() {
				public void meta(MetaMessage event) {
					// Sequencer is done playing
					if (event.getType() == 47) {
						System.out.println("Sequencer is done playing.");
					}
				}
			});
		} catch (MalformedURLException e) {
		} catch (IOException e) {
		} catch (MidiUnavailableException e) {
		} catch (InvalidMidiDataException e) {
		} catch (InterruptedException e) {
		}
	}
}