package ip91.chui.oleh.model.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LessonNumber {
  FIRST(1),
  SECOND(2),
  THIRD(3),
  FOURTH(4),
  FIFTH(5),
  SIXTH(6),
  SEVENTH(7),
  EIGHTH(8);

  private final int num;
}
