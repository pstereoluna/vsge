package com.vsge.music.chord;

import com.vsge.music.theory.Note;
import com.vsge.music.theory.Interval;
import java.util.Arrays;
import java.util.List;

/**
 * Minor triad chord implementation.
 * 
 * @author VSGE Team
 * @version 1.0.0
 */
public class MinorChord extends Chord {
    public MinorChord(Note root) {
        super(root, "m");
    }

    @Override
    protected List<Interval> getIntervals() {
        return Arrays.asList(
            Interval.MINOR_THIRD,
            Interval.PERFECT_FIFTH
        );
    }
}
