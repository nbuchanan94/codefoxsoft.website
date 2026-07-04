// Nicholas Buchanan
// August 2012
// MidiMusic.java

import javax.sound.midi.*;
import javax.swing.*;
import java.awt.*;

public class MidiMusic {

    private Sequencer sequencer;
    private Sequence startMenuSequence;
    private Sequence gameSequence;

    public MidiMusic() {
        try {
            sequencer = MidiSystem.getSequencer();
            sequencer.open();

            startMenuSequence = createStartMenuSequence();
            gameSequence = createGameSequence();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Sequence createStartMenuSequence() throws Exception {
        Sequence sequence = new Sequence(Sequence.PPQ, 4);
        Track track = sequence.createTrack();

        // Program change - set instrument to Synth Pad (e.g., 89)
        ShortMessage programChange = new ShortMessage();
        programChange.setMessage(ShortMessage.PROGRAM_CHANGE, 0, 89, 0);
        track.add(new MidiEvent(programChange, 0));

        // Add some notes - calm synth melody
        addNote(track, 60, 100, 0, 4); // C4
        addNote(track, 62, 100, 4, 4); // D4
        addNote(track, 64, 100, 8, 4); // E4
        addNote(track, 65, 100, 12, 4); // F4
        addNote(track, 67, 100, 16, 8); // G4

        // End of track
        track.add(createEndOfTrackEvent(24));

        return sequence;
    }

    private Sequence createGameSequence() throws Exception {
        Sequence sequence = new Sequence(Sequence.PPQ, 4);
        Track track = sequence.createTrack();

        // Program change - set instrument to Synth Lead (e.g., 80)
        ShortMessage programChange = new ShortMessage();
        programChange.setMessage(ShortMessage.PROGRAM_CHANGE, 0, 80, 0);
        track.add(new MidiEvent(programChange, 0));

        // Add some notes - more dramatic 8-bit space melody
        addNote(track, 67, 120, 0, 2); // G4
        addNote(track, 70, 120, 2, 2); // A#4
        addNote(track, 74, 120, 4, 2); // D5
        addNote(track, 76, 120, 6, 4); // E5
        addNote(track, 74, 120, 10, 2); // D5
        addNote(track, 70, 120, 12, 4); // A#4
        addNote(track, 67, 120, 16, 4); // G4

        // End of track
        track.add(createEndOfTrackEvent(20));

        return sequence;
    }

    private void addNote(Track track, int note, int velocity, long startTick, long duration) throws Exception {
        ShortMessage on = new ShortMessage();
        on.setMessage(ShortMessage.NOTE_ON, 0, note, velocity);
        MidiEvent noteOn = new MidiEvent(on, startTick);
        track.add(noteOn);

        ShortMessage off = new ShortMessage();
        off.setMessage(ShortMessage.NOTE_OFF, 0, note, 0);
        MidiEvent noteOff = new MidiEvent(off, startTick + duration);
        track.add(noteOff);
    }

    private MidiEvent createEndOfTrackEvent(long tick) throws Exception {
        MetaMessage metaMessage = new MetaMessage();
        metaMessage.setMessage(0x2F, new byte[0], 0);
        return new MidiEvent(metaMessage, tick);
    }

    public void playStartMenuMusic() {
        try {
            if (sequencer.isRunning())
                sequencer.stop();
            sequencer.setSequence(startMenuSequence);
            sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
            sequencer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playGameMusic() {
        try {
            if (sequencer.isRunning())
                sequencer.stop();
            sequencer.setSequence(gameSequence);
            sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
            sequencer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopMusic() {
        if (sequencer != null && sequencer.isRunning()) {
            sequencer.stop();
        }
    }

    public void close() {
        stopMusic();
        if (sequencer != null)
            sequencer.close();
    }

    // Interactive GUI Tester
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Midi Music Tester");
            frame.setSize(400, 150);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

            MidiMusic music = new MidiMusic();

            JButton menuBtn = new JButton("Play Menu Music");
            menuBtn.addActionListener(e -> music.playStartMenuMusic());

            JButton gameBtn = new JButton("Play Game Music");
            gameBtn.addActionListener(e -> music.playGameMusic());

            JButton stopBtn = new JButton("Stop");
            stopBtn.addActionListener(e -> music.stopMusic());

            frame.add(menuBtn);
            frame.add(gameBtn);
            frame.add(stopBtn);

            // Close sequencer on exit
            frame.addWindowListener(new java.awt.event.WindowAdapter() {
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    music.close();
                }
            });

            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}

/*
 * This class handles MIDI audio playback for a game or application.
 * It programmatically creates musical sequences (tracks) with specific
 * instruments
 * and notes, and provides methods to play them in a continuous loop.
 *
 * The main method provides a graphical interface to test the music tracks.
 */
