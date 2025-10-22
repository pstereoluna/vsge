package com.vsge.core.chord;

import com.vsge.core.theory.Note;
import com.vsge.core.theory.Interval;
import java.util.Arrays;
import java.util.List;

/**
 * Major 7th chord implementation.
 * 
 * @author VSGE Team
 * @version 1.0.0
 */
public class Major7Chord extends Chord {
    public Major7Chord(Note root) {
        super(root, "maj7");
    }

    @Override
    protected List<Interval> getIntervals() {
        return Arrays.asList(
            Interval.MAJOR_THIRD,
            Interval.PERFECT_FIFTH,
            Interval.MAJOR_SEVENTH
        );
    }
}
