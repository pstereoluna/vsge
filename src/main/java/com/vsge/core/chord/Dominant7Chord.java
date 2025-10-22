package com.vsge.core.chord;

import com.vsge.core.theory.Note;
import com.vsge.core.theory.Interval;
import java.util.Arrays;
import java.util.List;

/**
 * Dominant 7th chord implementation.
 * 
 * @author VSGE Team
 * @version 1.0.0
 */
public class Dominant7Chord extends Chord {
    public Dominant7Chord(Note root) {
        super(root, "7");
    }

    @Override
    protected List<Interval> getIntervals() {
        return Arrays.asList(
            Interval.MAJOR_THIRD,
            Interval.PERFECT_FIFTH,
            Interval.MINOR_SEVENTH
        );
    }
}
