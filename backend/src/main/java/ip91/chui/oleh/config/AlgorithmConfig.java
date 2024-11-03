package ip91.chui.oleh.config;

import ip91.chui.oleh.algorithm.fitnessFunction.FitnessFunction;
import ip91.chui.oleh.algorithm.fitnessFunction.TimetableFitnessFunction;
import ip91.chui.oleh.algorithm.fitnessFunction.step.FitnessFunctionStep;
import ip91.chui.oleh.algorithm.fitnessFunction.step.RoomTimeslotCollisionsFitnessFunctionStep;
import ip91.chui.oleh.algorithm.fitnessFunction.step.SameSubjectsPerDayFitnessFunctionStep;
import ip91.chui.oleh.algorithm.fitnessFunction.step.TeacherTimeslotCollisionsFitnessFunctionStep;
import ip91.chui.oleh.config.properties.fitnessFunction.FitnessFunctionProperties;
import java.util.List;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({ FitnessFunctionProperties.class })
public class AlgorithmConfig {

  @Bean
  public FitnessFunction fitnessFunction(List<FitnessFunctionStep> steps) {
    return new TimetableFitnessFunction(steps);
  }

  @Bean
  public FitnessFunctionStep sameSubjectsStep(FitnessFunctionProperties props) {
    return new SameSubjectsPerDayFitnessFunctionStep(
        props.getSameSubjectsPerDayLimit(), props.getSameSubjectPerDayFine());
  }

  @Bean
  public FitnessFunctionStep teacherParallelLessons(FitnessFunctionProperties props) {
    return new TeacherTimeslotCollisionsFitnessFunctionStep(
        props.getTeacherMaxLessonsAtSameTimeLimit(), props.getTeacherMaxLessonsAtSameTimeFine());
  }

  @Bean
  public FitnessFunctionStep overloadedRoomsStep(FitnessFunctionProperties props) {
    return new RoomTimeslotCollisionsFitnessFunctionStep(props.getRoomMaxLessonsAtSameTimeFine());
  }
}
