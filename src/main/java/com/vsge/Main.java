// ========== Main.java ==========
package com.vsge;

import com.vsge.ui.ConsoleUI;
import com.vsge.audio.MidiService;
import com.vsge.engine.PlaybackEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main entry point for Virtual Stringless Guitar Engine.
 *
 * @author VSGE Team
 * @version 1.0.0
 */
public class Main {
  private static final Logger logger = LoggerFactory.getLogger(Main.class);

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
      logger.error("Fatal error during application startup", e);
      System.err.println("Failed to start VSGE: " + e.getMessage());
      System.exit(1);
    }
  }

  /**
   * Initializes application components.
   */
  private static void initializeApplication() throws Exception {
    logger.debug("Initializing MIDI service...");
    MidiService.getInstance().initialize();

    logger.debug("Loading default configurations...");
    // ConfigManager.loadDefaults();

    logger.info("Application initialized successfully");
  }
}
