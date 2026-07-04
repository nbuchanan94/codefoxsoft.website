// Nicholas Buchanan
// 2011

import java.io.File;
import javax.sound.sampled.*;

public class AudioPlayerExample {
	public static void main(String[] args) {
		System.out.println("Attempting to play: Blip.wav");

		try {
			// Load the audio file (relative path)
			File soundFile = new File("Blip.wav");

			if (!soundFile.exists()) {
				System.out.println("Error: File 'Blip.wav' not found in current directory.");
				return;
			}

			AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);

			// Get a sound clip resource
			Clip clip = AudioSystem.getClip();

			// Open audio clip and load samples from the audio input stream
			clip.open(audioIn);

			// Play
			clip.start();
			System.out.println("Playing...");

			// Wait for the sound to finish
			// (Clip runs in a separate daemon thread, so main thread must wait)
			long durationMs = clip.getMicrosecondLength() / 1000;
			Thread.sleep(durationMs + 100);

			System.out.println("Done.");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

/*
 * PROGRAM DESCRIPTION:
 *
 * This program demonstrates how to play a .wav audio file using the Java Sound
 * API.
 */