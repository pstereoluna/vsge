package com.vsge.ui;

import com.vsge.audio.ImprovedMidiService;
import com.vsge.core.chord.Chord;
import com.vsge.core.chord.ChordFactory;
import com.vsge.core.progression.ChordProgression;
import com.vsge.core.theory.Note;
import com.vsge.engine.PlaybackEngine;
import com.vsge.style.PlayStyle;
import com.vsge.style.StyleFactory;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.concurrent.CompletableFuture;

/**
 * JavaFX GUI application for the Virtual Stringless Guitar Engine.
 * Provides an interactive interface for playing chords.
 * 
 * @author VSGE Team
 * @version 1.0.0
 */
public class VSGEApp extends Application {
    
    private PlaybackEngine playbackEngine;
    private Note currentKey = new Note(Note.PitchClass.C, 4);
    private PlayStyle currentStyle = StyleFactory.create("folk");
    private int currentTempo = 120;
    
    // UI Components
    private Button[] chordButtons = new Button[7];
    private Label statusLabel;
    private Slider strumSpeedSlider;
    private Button stopButton;
    private ComboBox<String> styleComboBox;
    private ComboBox<String> keyComboBox;
    
    @Override
    public void start(Stage primaryStage) {
        try {
            // Initialize MIDI service
            initializeAudio();
            
            // Create UI
            VBox root = createMainUI();
            
            // Setup scene
            Scene scene = new Scene(root, 500, 400);
            scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
            
            primaryStage.setTitle("Virtual Stringless Guitar Engine");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.setOnCloseRequest(e -> {
                cleanup();
                Platform.exit();
            });
            
            primaryStage.show();
            
        } catch (Exception e) {
            e.printStackTrace();
            showError("Failed to start VSGE GUI: " + e.getMessage());
        }
    }
    
    private void initializeAudio() throws Exception {
        ImprovedMidiService.getInstance().initialize();
        playbackEngine = new PlaybackEngine();
    }
    
    private VBox createMainUI() {
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #f0f0f0;");
        
        // Title
        Text title = new Text("Virtual Stringless Guitar Engine");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        title.setFill(Color.DARKBLUE);
        root.getChildren().add(title);
        
        // Subtitle
        Text subtitle = new Text("🎸 Click a chord to play!");
        subtitle.setFont(Font.font("Arial", 16));
        subtitle.setFill(Color.DARKGREEN);
        root.getChildren().add(subtitle);
        
        // Chord buttons
        VBox chordSection = createChordButtons();
        root.getChildren().add(chordSection);
        
        // Control panel
        HBox controlPanel = createControlPanel();
        root.getChildren().add(controlPanel);
        
        // Status label
        statusLabel = new Label("Ready to play!");
        statusLabel.setFont(Font.font("Arial", 14));
        statusLabel.setStyle("-fx-text-fill: #333333;");
        root.getChildren().add(statusLabel);
        
        return root;
    }
    
    private VBox createChordButtons() {
        VBox chordSection = new VBox(10);
        chordSection.setAlignment(Pos.CENTER);
        
        // First row: I, ii, iii, IV
        HBox firstRow = new HBox(10);
        firstRow.setAlignment(Pos.CENTER);
        
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
            Button button = createChordButton(chordLabels[i], degrees[i]);
            chordButtons[i] = button;
            firstRow.getChildren().add(button);
        }
        
        // Second row: V, vi, vii°
        HBox secondRow = new HBox(10);
        secondRow.setAlignment(Pos.CENTER);
        
        for (int i = 4; i < 7; i++) {
            Button button = createChordButton(chordLabels[i], degrees[i]);
            chordButtons[i] = button;
            secondRow.getChildren().add(button);
        }
        
        chordSection.getChildren().addAll(firstRow, secondRow);
        return chordSection;
    }
    
    private Button createChordButton(String label, ChordProgression.Degree degree) {
        Button button = new Button(label);
        button.setPrefSize(80, 50);
        button.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        button.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-border-radius: 5; -fx-background-radius: 5;");
        
        button.setOnAction(e -> playChord(degree, button));
        
        // Hover effects
        button.setOnMouseEntered(e -> {
            if (!button.getStyle().contains("#45a049")) {
                button.setStyle("-fx-background-color: #45a049; -fx-text-fill: white; -fx-border-radius: 5; -fx-background-radius: 5;");
            }
        });
        
        button.setOnMouseExited(e -> {
            if (!button.getStyle().contains("#FF6B6B")) {
                button.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-border-radius: 5; -fx-background-radius: 5;");
            }
        });
        
        return button;
    }
    
    private HBox createControlPanel() {
        HBox controlPanel = new HBox(15);
        controlPanel.setAlignment(Pos.CENTER);
        
        // Style selection
        Label styleLabel = new Label("Style:");
        styleComboBox = new ComboBox<>();
        styleComboBox.getItems().addAll("Folk", "Pop", "Jazz", "Rock");
        styleComboBox.setValue("Folk");
        styleComboBox.setOnAction(e -> {
            String style = styleComboBox.getValue().toLowerCase();
            currentStyle = StyleFactory.create(style);
            updateStatus("Style changed to: " + styleComboBox.getValue());
        });
        
        // Key selection
        Label keyLabel = new Label("Key:");
        keyComboBox = new ComboBox<>();
        keyComboBox.getItems().addAll("C", "D", "E", "F", "G", "A", "B");
        keyComboBox.setValue("C");
        keyComboBox.setOnAction(e -> {
            String keyName = keyComboBox.getValue();
            Note.PitchClass pitch = Note.PitchClass.valueOf(keyName);
            currentKey = new Note(pitch, 4);
            updateStatus("Key changed to: " + keyName);
        });
        
        // Strum speed slider
        Label speedLabel = new Label("Strum Speed:");
        strumSpeedSlider = new Slider(50, 200, 120);
        strumSpeedSlider.setShowTickLabels(true);
        strumSpeedSlider.setShowTickMarks(true);
        strumSpeedSlider.setMajorTickUnit(50);
        strumSpeedSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            currentTempo = newVal.intValue();
            updateStatus("Tempo: " + currentTempo + " BPM");
        });
        
        // Stop button
        stopButton = new Button("Stop All");
        stopButton.setStyle("-fx-background-color: #FF6B6B; -fx-text-fill: white; -fx-border-radius: 5; -fx-background-radius: 5;");
        stopButton.setOnAction(e -> stopAllSounds());
        
        controlPanel.getChildren().addAll(
            styleLabel, styleComboBox,
            keyLabel, keyComboBox,
            speedLabel, strumSpeedSlider,
            stopButton
        );
        
        return controlPanel;
    }
    
    private void playChord(ChordProgression.Degree degree, Button button) {
        try {
            // Visual feedback
            button.setStyle("-fx-background-color: #FF6B6B; -fx-text-fill: white; -fx-border-radius: 5; -fx-background-radius: 5;");
            updateStatus("Playing " + degree.getSymbol() + " chord...");
            
            // Create chord
            Note chordRoot = currentKey.transpose(degree.getSemitones());
            Chord chord = ChordFactory.create(chordRoot, degree.getDefaultType());
            
            // Play chord
            playbackEngine.playChord(chord, currentStyle, currentTempo);
            
            // Reset button color after a delay
            CompletableFuture.delayedExecutor(500, java.util.concurrent.TimeUnit.MILLISECONDS)
                .execute(() -> Platform.runLater(() -> {
                    button.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-border-radius: 5; -fx-background-radius: 5;");
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
        Platform.runLater(() -> {
            statusLabel.setText(message);
        });
    }
    
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("VSGE Error");
        alert.setHeaderText("Error");
        alert.setContentText(message);
        alert.showAndWait();
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
        launch(args);
    }
}
