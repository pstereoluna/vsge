package com.vsge.ui;

import com.vsge.audio.ImprovedMidiService;
import com.vsge.core.chord.Chord;
import com.vsge.core.chord.ChordFactory;
import com.vsge.core.progression.ChordProgression;
import com.vsge.core.theory.Note;
import com.vsge.engine.PlaybackEngine;
import com.vsge.style.PlayStyle;
import com.vsge.style.StyleFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CompletableFuture;

/**
 * Swing GUI application for the Virtual Stringless Guitar Engine.
 * Provides an interactive interface for playing chords.
 * 
 * @author VSGE Team
 * @version 1.0.0
 */
public class VSGEGui extends JFrame {
    
    private PlaybackEngine playbackEngine;
    private Note currentKey = new Note(Note.PitchClass.C, 4);
    private PlayStyle currentStyle = StyleFactory.create("folk");
    private int currentTempo = 120;
    
    // UI Components
    private JButton[] chordButtons = new JButton[7];
    private JLabel statusLabel;
    private JSlider strumSpeedSlider;
    private JButton stopButton;
    private JComboBox<String> styleComboBox;
    private JComboBox<String> keyComboBox;
    
    public VSGEGui() {
        initializeGUI();
        initializeAudio();
    }
    
    private void initializeGUI() {
        setTitle("Virtual Stringless Guitar Engine");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
        
        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(240, 240, 240));
        
        // Title
        JLabel titleLabel = new JLabel("Virtual Stringless Guitar Engine");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0, 0, 139));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);
        
        mainPanel.add(Box.createVerticalStrut(10));
        
        // Subtitle
        JLabel subtitleLabel = new JLabel("🎸 Click a chord to play!");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(0, 100, 0));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(subtitleLabel);
        
        mainPanel.add(Box.createVerticalStrut(20));
        
        // Chord buttons
        JPanel chordPanel = createChordButtons();
        mainPanel.add(chordPanel);
        
        mainPanel.add(Box.createVerticalStrut(20));
        
        // Control panel
        JPanel controlPanel = createControlPanel();
        mainPanel.add(controlPanel);
        
        mainPanel.add(Box.createVerticalStrut(15));
        
        // Status label
        statusLabel = new JLabel("Ready to play!");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        statusLabel.setForeground(new Color(51, 51, 51));
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(statusLabel);
        
        add(mainPanel, BorderLayout.CENTER);
        
        // Set size and center
        setSize(500, 400);
        setLocationRelativeTo(null);
    }
    
    private JPanel createChordButtons() {
        JPanel chordPanel = new JPanel();
        chordPanel.setLayout(new BoxLayout(chordPanel, BoxLayout.Y_AXIS));
        chordPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        chordPanel.setOpaque(false);
        
        // First row: I, ii, iii, IV
        JPanel firstRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        firstRow.setOpaque(false);
        
        String[] chordLabels = {"I", "ii", "iii", "IV", "V", "vi", "vii°"};
        ChordProgression.Degree[] degrees = {
            ChordProgression.Degree.I,
            ChordProgression.Degree.ii,
            ChordProgression.Degree.iii,
            ChordProgression.Degree.IV,
            ChordProgression.Degree.V,
            ChordProgression.Degree.vi,
            ChordProgression.Degree.vii
        };
        
        for (int i = 0; i < 4; i++) {
            JButton button = createChordButton(chordLabels[i], degrees[i]);
            chordButtons[i] = button;
            firstRow.add(button);
        }
        
        // Second row: V, vi, vii°
        JPanel secondRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        secondRow.setOpaque(false);
        
        for (int i = 4; i < 7; i++) {
            JButton button = createChordButton(chordLabels[i], degrees[i]);
            chordButtons[i] = button;
            secondRow.add(button);
        }
        
        chordPanel.add(firstRow);
        chordPanel.add(secondRow);
        return chordPanel;
    }
    
    private JButton createChordButton(String label, ChordProgression.Degree degree) {
        JButton button = new JButton(label);
        button.setPreferredSize(new Dimension(80, 50));
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(76, 175, 80));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        
        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (!button.getBackground().equals(new Color(255, 107, 107))) {
                    button.setBackground(new Color(69, 160, 73));
                }
            }
            
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (!button.getBackground().equals(new Color(255, 107, 107))) {
                    button.setBackground(new Color(76, 175, 80));
                }
            }
        });
        
        button.addActionListener(e -> playChord(degree, button));
        
        return button;
    }
    
    private JPanel createControlPanel() {
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        controlPanel.setOpaque(false);
        
        // Style selection
        JLabel styleLabel = new JLabel("Style:");
        styleComboBox = new JComboBox<>(new String[]{"Folk", "Pop", "Jazz", "Rock"});
        styleComboBox.setSelectedItem("Folk");
        styleComboBox.addActionListener(e -> {
            String style = (String) styleComboBox.getSelectedItem();
            currentStyle = StyleFactory.create(style.toLowerCase());
            updateStatus("Style changed to: " + style);
        });
        
        // Key selection
        JLabel keyLabel = new JLabel("Key:");
        keyComboBox = new JComboBox<>(new String[]{"C", "D", "E", "F", "G", "A", "B"});
        keyComboBox.setSelectedItem("C");
        keyComboBox.addActionListener(e -> {
            String keyName = (String) keyComboBox.getSelectedItem();
            Note.PitchClass pitch = Note.PitchClass.valueOf(keyName);
            currentKey = new Note(pitch, 4);
            updateStatus("Key changed to: " + keyName);
        });
        
        // Strum speed slider
        JLabel speedLabel = new JLabel("Strum Speed:");
        strumSpeedSlider = new JSlider(50, 200, 120);
        strumSpeedSlider.setMajorTickSpacing(50);
        strumSpeedSlider.setMinorTickSpacing(25);
        strumSpeedSlider.setPaintTicks(true);
        strumSpeedSlider.setPaintLabels(true);
        strumSpeedSlider.addChangeListener(e -> {
            currentTempo = strumSpeedSlider.getValue();
            updateStatus("Tempo: " + currentTempo + " BPM");
        });
        
        // Stop button
        stopButton = new JButton("Stop All");
        stopButton.setBackground(new Color(255, 107, 107));
        stopButton.setForeground(Color.WHITE);
        stopButton.setFocusPainted(false);
        stopButton.setBorderPainted(false);
        stopButton.setOpaque(true);
        stopButton.addActionListener(e -> stopAllSounds());
        
        controlPanel.add(styleLabel);
        controlPanel.add(styleComboBox);
        controlPanel.add(keyLabel);
        controlPanel.add(keyComboBox);
        controlPanel.add(speedLabel);
        controlPanel.add(strumSpeedSlider);
        controlPanel.add(stopButton);
        
        return controlPanel;
    }
    
    private void playChord(ChordProgression.Degree degree, JButton button) {
        try {
            // Visual feedback
            button.setBackground(new Color(255, 107, 107));
            updateStatus("Playing " + degree.getSymbol() + " chord...");
            
            // Create chord
            Note chordRoot = currentKey.transpose(degree.getSemitones());
            Chord chord = ChordFactory.create(chordRoot, degree.getDefaultType());
            
            // Play chord
            playbackEngine.playChord(chord, currentStyle, currentTempo);
            
            // Reset button color after a delay
            CompletableFuture.delayedExecutor(500, java.util.concurrent.TimeUnit.MILLISECONDS)
                .execute(() -> SwingUtilities.invokeLater(() -> {
                    button.setBackground(new Color(76, 175, 80));
                }));
            
        } catch (Exception e) {
            updateStatus("Error playing chord: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void stopAllSounds() {
        try {
            playbackEngine.stop();
            updateStatus("All sounds stopped");
        } catch (Exception e) {
            updateStatus("Error stopping sounds: " + e.getMessage());
        }
    }
    
    private void updateStatus(String message) {
        SwingUtilities.invokeLater(() -> {
            statusLabel.setText(message);
        });
    }
    
    private void initializeAudio() {
        try {
            ImprovedMidiService.getInstance().initialize();
            playbackEngine = new PlaybackEngine();
            updateStatus("Audio system initialized successfully");
        } catch (Exception e) {
            updateStatus("Error initializing audio: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void cleanup() {
        try {
            if (playbackEngine != null) {
                playbackEngine.close();
            }
            ImprovedMidiService.getInstance().close();
        } catch (Exception e) {
            System.err.println("Error during cleanup: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        // Use default look and feel
        
        SwingUtilities.invokeLater(() -> {
            VSGEGui app = new VSGEGui();
            app.setVisible(true);
            
            // Add window listener for cleanup
            app.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    app.cleanup();
                    System.exit(0);
                }
            });
        });
    }
}
