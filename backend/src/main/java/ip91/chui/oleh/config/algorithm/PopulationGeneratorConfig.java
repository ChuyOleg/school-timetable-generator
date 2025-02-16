package ip91.chui.oleh.config.algorithm;

import ip91.chui.oleh.algorithm.fitnessFunction.FitnessFunction;
import ip91.chui.oleh.algorithm.populationGenerator.PopulationGenerator;
import ip91.chui.oleh.algorithm.populationGenerator.TimetablePopulationGenerator;
import ip91.chui.oleh.algorithm.util.holder.RoomsHolder;
import ip91.chui.oleh.algorithm.util.holder.TeacherHolder;
import ip91.chui.oleh.algorithm.util.holder.TimeSlotsHolder;
import ip91.chui.oleh.model.mapping.GroupMapper;
import ip91.chui.oleh.repository.GroupRepository;
import ip91.chui.oleh.service.auth.AuthenticationService;
import java.util.Random;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PopulationGeneratorConfig {

  @Bean
  public PopulationGenerator timetablePopulationGenerator(
      AuthenticationService authService, GroupRepository groupRepository, GroupMapper groupMapper,
      FitnessFunction fitnessFunction, TimeSlotsHolder timeSlotsHolder, TeacherHolder teacherHolder,
      RoomsHolder roomsHolder, Random random) {

    return new TimetablePopulationGenerator(
        authService, groupRepository, groupMapper, fitnessFunction,
        timeSlotsHolder, teacherHolder, roomsHolder, random);
  }
}
