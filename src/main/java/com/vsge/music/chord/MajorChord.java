package com.vsge.music.chord;

import com.vsge.music.theory.Note;
import com.vsge.music.theory.Interval;
import java.util.Arrays;
import java.util.List;

/**
 * Major triad chord implementation.
 * 
 * @author VSGE Team
 * @version 1.0.0
 */
public class MajorChord extends Chord {
    public MajorChord(Note root) {
        super(root, "");
    }

    @Override
    protected List<Interval> getIntervals() {
        return Arrays.asList(
            Interval.MAJOR_THIRD,
            Interval.PERFECT_FIFTH
        );
    }
}
