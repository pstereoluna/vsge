package com.vsge.engine.humanizer;

/**
 * Settings for humanization parameters in playback.
 * Controls timing, velocity, and duration variations to make playback more natural.
 * 
 * @author VSGE Team
 * @version 1.0.0
 */
public class HumanizationSettings {
    
    // Timing humanization
    private double timingOffsetRange = 0.05; // ±50ms variation
    private boolean enableTimingHumanization = true;
    
    // Velocity humanization
    private int velocityVariationRange = 10; // ±10 velocity variation
    private boolean enableVelocityHumanization = true;
    
    // Duration humanization
    private double durationVariationRange = 0.1; // ±10% duration variation
    private boolean enableDurationHumanization = true;
    
    // Strumming humanization
    private double strumDelay = 0.02; // 20ms delay between strings
    private boolean enableStrummingEffect = true;
    
    // Swing feel
    private double swingRatio = 0.0; // 0.0 = straight, 0.5 = full swing
    private boolean enableSwing = false;
    
    public HumanizationSettings() {
        // Default settings
    }
    
    // Getters and setters
    public double getTimingOffsetRange() { return timingOffsetRange; }
    public void setTimingOffsetRange(double timingOffsetRange) { 
        this.timingOffsetRange = Math.max(0.0, Math.min(0.2, timingOffsetRange)); 
    }
    
    public boolean isTimingHumanizationEnabled() { return enableTimingHumanization; }
    public void setTimingHumanizationEnabled(boolean enabled) { this.enableTimingHumanization = enabled; }
    
    public int getVelocityVariationRange() { return velocityVariationRange; }
    public void setVelocityVariationRange(int velocityVariationRange) { 
        this.velocityVariationRange = Math.max(0, Math.min(30, velocityVariationRange)); 
    }
    
    public boolean isVelocityHumanizationEnabled() { return enableVelocityHumanization; }
    public void setVelocityHumanizationEnabled(boolean enabled) { this.enableVelocityHumanization = enabled; }
    
    public double getDurationVariationRange() { return durationVariationRange; }
    public void setDurationVariationRange(double durationVariationRange) { 
        this.durationVariationRange = Math.max(0.0, Math.min(0.5, durationVariationRange)); 
    }
    
    public boolean isDurationHumanizationEnabled() { return enableDurationHumanization; }
    public void setDurationHumanizationEnabled(boolean enabled) { this.enableDurationHumanization = enabled; }
    
    public double getStrumDelay() { return strumDelay; }
    public void setStrumDelay(double strumDelay) { 
        this.strumDelay = Math.max(0.0, Math.min(0.1, strumDelay)); 
    }
    
    public boolean isStrummingEffectEnabled() { return enableStrummingEffect; }
    public void setStrummingEffectEnabled(boolean enabled) { this.enableStrummingEffect = enabled; }
    
    public double getSwingRatio() { return swingRatio; }
    public void setSwingRatio(double swingRatio) { 
        this.swingRatio = Math.max(0.0, Math.min(0.5, swingRatio)); 
    }
    
    public boolean isSwingEnabled() { return enableSwing; }
    public void setSwingEnabled(boolean enabled) { this.enableSwing = enabled; }
    
    /**
     * Creates a preset for folk style.
     */
    public static HumanizationSettings folkPreset() {
        HumanizationSettings settings = new HumanizationSettings();
        settings.setTimingOffsetRange(0.05);
        settings.setVelocityVariationRange(15);
        settings.setDurationVariationRange(0.15);
        settings.setStrummingEffectEnabled(false); // Fingerpicking doesn't use strumming
        settings.setSwingEnabled(false);
        return settings;
    }
    
    /**
     * Creates a preset for pop style.
     */
    public static HumanizationSettings popPreset() {
        HumanizationSettings settings = new HumanizationSettings();
        settings.setTimingOffsetRange(0.03);
        settings.setVelocityVariationRange(8);
        settings.setDurationVariationRange(0.1);
        settings.setStrumDelay(0.02);
        settings.setStrummingEffectEnabled(true);
        settings.setSwingEnabled(false);
        return settings;
    }
    
    /**
     * Creates a preset for jazz style.
     */
    public static HumanizationSettings jazzPreset() {
        HumanizationSettings settings = new HumanizationSettings();
        settings.setTimingOffsetRange(0.02);
        settings.setVelocityVariationRange(6);
        settings.setDurationVariationRange(0.08);
        settings.setStrummingEffectEnabled(false);
        settings.setSwingRatio(0.3);
        settings.setSwingEnabled(true);
        return settings;
    }
    
    /**
     * Creates a preset for rock style.
     */
    public static HumanizationSettings rockPreset() {
        HumanizationSettings settings = new HumanizationSettings();
        settings.setTimingOffsetRange(0.01);
        settings.setVelocityVariationRange(4);
        settings.setDurationVariationRange(0.05);
        settings.setStrumDelay(0.01);
        settings.setStrummingEffectEnabled(true);
        settings.setSwingEnabled(false);
        return settings;
    }
}
