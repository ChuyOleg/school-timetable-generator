package ip91.chui.oleh.config.algorithm;

import ip91.chui.oleh.algorithm.fitnessFunction.FitnessFunction;
import ip91.chui.oleh.algorithm.fitnessFunction.TimetableFitnessFunction;
import ip91.chui.oleh.algorithm.fitnessFunction.step.FitnessFunctionStep;
import ip91.chui.oleh.algorithm.fitnessFunction.step.RoomReservationFitnessFunctionStep;
import ip91.chui.oleh.algorithm.fitnessFunction.step.RoomTimeslotCollisionsFitnessFunctionStep;
import ip91.chui.oleh.algorithm.fitnessFunction.step.SameSubjectsPerDayFitnessFunctionStep;
import ip91.chui.oleh.algorithm.fitnessFunction.step.TeacherDesiredPeriodsFitnessFunctionStep;
import ip91.chui.oleh.algorithm.fitnessFunction.step.TeacherFreeDaysFitnessFunctionStep;
import ip91.chui.oleh.algorithm.fitnessFunction.step.TeacherLessonsOrderFitnessFunction;
import ip91.chui.oleh.algorithm.fitnessFunction.step.TeacherMaxLessonsPerDayFitnessFunctionStep;
import ip91.chui.oleh.algorithm.fitnessFunction.step.TeacherTimeslotCollisionsFitnessFunctionStep;
import ip91.chui.oleh.algorithm.fitnessFunction.utils.SecondShiftLessonNumbersTransformer;
import ip91.chui.oleh.config.properties.algorithm.FitnessFunctionProperties;
import ip91.chui.oleh.repository.ShiftsIntersectionRepository;
import ip91.chui.oleh.service.auth.AuthenticationService;
import java.util.List;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({ FitnessFunctionProperties.class })
public class FitnessFunctionConfig {

  @Bean
  public FitnessFunction fitnessFunction(
      List<FitnessFunctionStep> steps, SecondShiftLessonNumbersTransformer transformer,
      AuthenticationService authService, ShiftsIntersectionRepository shiftsRepository) {

    return new TimetableFitnessFunction(steps, transformer, authService, shiftsRepository);
  }

  @Bean
  public FitnessFunctionStep teacherMaxLessonsPerDayStep(FitnessFunctionProperties props) {
    return new TeacherMaxLessonsPerDayFitnessFunctionStep(props.getTeacherMaxLessonsPerDayFine());
  }

  @Bean
  public FitnessFunctionStep teacherFreeDaysStep(FitnessFunctionProperties props) {
    return new TeacherFreeDaysFitnessFunctionStep(props.getTeacherFreeDayFine());
  }

  @Bean
  public FitnessFunctionStep reservedRoomsStep(FitnessFunctionProperties props) {
    return new RoomReservationFitnessFunctionStep(props.getRoomReservationFine());
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

  @Bean
  public FitnessFunctionStep teacherDesiredPeriodsStep(FitnessFunctionProperties props) {
    return new TeacherDesiredPeriodsFitnessFunctionStep(props.getTeacherDesiredPeriodsFine());
  }

  @Bean
  public FitnessFunctionStep teacherLessonsOrderStep(FitnessFunctionProperties props) {
    return new TeacherLessonsOrderFitnessFunction(
        props.getTeacherLessonsOrderMediumFine(), props.getTeacherLessonsOrderHighFine());
  }
}
