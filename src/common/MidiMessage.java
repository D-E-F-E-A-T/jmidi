/**
 * Copyright 2010 Leeor Engel.
 *
 * @author Leeor Engel
 */
package common;

public interface MidiMessage {

	byte[] getData();

	byte[] toBytes();

	int getLength();
}