package ip91.chui.oleh.algorithm.populationGenerator;

import ip91.chui.oleh.algorithm.config.Config;
import ip91.chui.oleh.algorithm.fitnessFunction.FitnessFunction;
import ip91.chui.oleh.algorithm.util.TimeSlotsHolder;
import ip91.chui.oleh.model.dto.GroupDto;
import ip91.chui.oleh.model.entity.Group;
import ip91.chui.oleh.model.entity.User;
import ip91.chui.oleh.model.enumeration.Role;
import ip91.chui.oleh.model.mapping.GroupMapper;
import ip91.chui.oleh.repository.GroupRepository;
import ip91.chui.oleh.service.auth.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Random;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TimetablePopulationGeneratorTest {

  @Mock
  private AuthenticationService authService;
  @Mock
  private GroupRepository groupRepository;
  @Mock
  private GroupMapper groupMapper;
  @Mock
  private FitnessFunction fitnessFunction;
  @Mock
  private TimeSlotsHolder timeSlotsHolder;
  @Mock
  private Random random;
  @InjectMocks
  private TimetablePopulationGenerator populationGenerator;

  @Test
  void Should_InvokeAllDependency() {
    when(authService.extractPrincipalFromSecurityContextHolder()).thenReturn(new User(1L, null, null, Role.USER));
    when(groupRepository.findAllByUserId(1L)).thenReturn(List.of(new Group()));
    when(groupMapper.groupToDto(any())).thenReturn(new GroupDto());

    populationGenerator.generate();

    verify(authService).extractPrincipalFromSecurityContextHolder();
    verify(groupRepository).findAllByUserId(1L);
    verify(groupMapper).groupToDto(any());
    verify(fitnessFunction, times(Config.POPULATION_SIZE)).calculate(any());
  }

}
