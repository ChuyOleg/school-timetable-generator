package ip91.chui.oleh.config.properties.algorithm;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "algorithm.fitness-function")
public class FitnessFunctionProperties {

  private int sameSubjectsPerDayLimit;
  private int sameSubjectPerDayFine;

  private int teacherMaxLessonsAtSameTimeLimit;
  private int teacherMaxLessonsAtSameTimeFine;

  private int teacherFreeDayFine;
  private int teacherMaxLessonsPerDayFine;
  private int teacherDesiredPeriodsFine;
  private int teacherLessonsOrderMediumFine;
  private int teacherLessonsOrderHighFine;

  private int roomMaxLessonsAtSameTimeFine;
  private int roomReservationFine;
}
