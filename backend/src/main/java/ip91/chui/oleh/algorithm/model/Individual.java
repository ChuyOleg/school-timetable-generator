package ip91.chui.oleh.algorithm.model;

import lombok.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class Individual {

  private final Object[] chromosome;
  private int fitness;

}
