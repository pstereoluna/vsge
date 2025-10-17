// ========== MajorChord.java ==========
package com.vsge.core.chord;

import java.util.Arrays;
import java.util.List;

/**
 * Major triad chord implementation.
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

// ========== MinorChord.java ==========
package com.vsge.theory;

import java.util.Arrays;
import java.util.List;

/**
 * Minor triad chord implementation.
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
