package com.vsge.music.chord;

import com.vsge.music.theory.Note;
import com.vsge.music.theory.Interval;
import java.util.Arrays;
import java.util.List;

/**
 * Minor 7th chord implementation.
 * 
 * @author VSGE Team
 * @version 1.0.0
 */
public class Minor7Chord extends Chord {
    public Minor7Chord(Note root) {
        super(root, "m7");
    }

    @Override
    protected List<Interval> getIntervals() {
        return Arrays.asList(
            Interval.MINOR_THIRD,
            Interval.PERFECT_FIFTH,
            Interval.MINOR_SEVENTH
        );
    }
}
