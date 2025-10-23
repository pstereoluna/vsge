package com.vsge.app;

import com.vsge.ui.console.ConsoleUI;
import com.vsge.audio.ImprovedMidiService;
import java.util.logging.Logger;

/**
 * Main entry point for Virtual Stringless Guitar Engine.
 *
 * @author VSGE Team
 * @version 1.0.0
 */
public class Main {
  private static final Logger logger = Logger.getLogger(Main.class.getName());

  /**
   * Application entry point.
   *
   * @param args command line arguments
   */
  public static void main(String[] args) {
    logger.info("Starting Virtual Stringless Guitar Engine v1.0.0");

    try {
      // Initialize core components
      initializeApplication();

      // Launch UI
      ConsoleUI ui = new ConsoleUI();
      ui.start();

    } catch (Exception e) {
      logger.severe("Fatal error during application startup: " + e.getMessage());
      System.err.println("Failed to start VSGE: " + e.getMessage());
      System.exit(1);
    }
  }

  /**
   * Initializes application components.
   */
  private static void initializeApplication() throws Exception {
    logger.fine("Initializing improved MIDI service...");
    ImprovedMidiService.getInstance().initialize();

    logger.fine("Loading default configurations...");
    // ConfigManager.loadDefaults();

    logger.info("Application initialized successfully");
  }
}
