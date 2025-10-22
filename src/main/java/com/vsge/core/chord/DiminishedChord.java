package com.vsge.core.chord;

import com.vsge.core.theory.Note;
import com.vsge.core.theory.Interval;
import java.util.Arrays;
import java.util.List;

/**
 * Diminished triad chord implementation.
 * 
 * @author VSGE Team
 * @version 1.0.0
 */
public class DiminishedChord extends Chord {
    public DiminishedChord(Note root) {
        super(root, "°");
    }

    @Override
    protected List<Interval> getIntervals() {
        return Arrays.asList(
            Interval.MINOR_THIRD,
            Interval.TRITONE
        );
    }
}
