package com.vsge;

import javax.sound.midi.*;

public class MidiInit {
    public static void main(String[] args) throws Exception {
        System.out.println("=== MIDI Soundbank Check ===");
        
        Synthesizer synth = MidiSystem.getSynthesizer();
        synth.open();

        System.out.println("Synthesizer opened: " + synth.getDeviceInfo());
        System.out.println("Max polyphony: " + synth.getMaxPolyphony());
        System.out.println("Latency: " + synth.getLatency());

        // 检查默认 Soundbank
        Soundbank sb = synth.getDefaultSoundbank();
        if (sb == null) {
            System.out.println("❌ No default soundbank found!");
            System.out.println("This means your system is using basic MIDI sounds only.");
        } else {
            System.out.println("✅ Default soundbank found: " + sb.getName());
            System.out.println("Soundbank description: " + sb.getDescription());
            
            // 载入所有乐器
            synth.loadAllInstruments(sb);
            System.out.println("✅ Loaded instruments from soundbank.");
            
            // 检查吉他乐器
            System.out.println("\n🎸 Checking guitar instruments:");
            int[] guitarInstruments = {24, 25, 26, 27, 28, 29, 30, 31};
            String[] guitarNames = {
                "Acoustic Guitar (nylon)",
                "Acoustic Guitar (steel)", 
                "Electric Guitar (jazz)",
                "Electric Guitar (clean)",
                "Electric Guitar (muted)",
                "Overdriven Guitar",
                "Distortion Guitar",
                "Guitar Harmonics"
            };
            
            for (int i = 0; i < guitarInstruments.length; i++) {
                Instrument instrument = sb.getInstrument(new Patch(0, guitarInstruments[i]));
                if (instrument != null) {
                    System.out.println("✅ " + guitarNames[i] + " (Program " + guitarInstruments[i] + "): " + instrument.getName());
                } else {
                    System.out.println("❌ " + guitarNames[i] + " (Program " + guitarInstruments[i] + "): NOT FOUND");
                }
            }
        }
        
        // 检查可用的乐器
        Instrument[] instruments = synth.getLoadedInstruments();
        System.out.println("\n📊 Total loaded instruments: " + instruments.length);
        
        if (instruments.length < 50) {
            System.out.println("⚠️  Very few instruments loaded - this might explain why sounds are similar");
        }
        
        // 测试一个简单的音符
        System.out.println("\n🎵 Testing a simple note...");
        MidiChannel[] channels = synth.getChannels();
        if (channels[0] != null) {
            channels[0].programChange(25); // Steel string guitar
            channels[0].noteOn(60, 80); // C4
            Thread.sleep(1000);
            channels[0].noteOff(60);
            System.out.println("✅ Note test completed");
        }

        synth.close();
        System.out.println("\n=== MIDI Check Complete ===");
    }
}
