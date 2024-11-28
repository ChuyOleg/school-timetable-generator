package ip91.chui.oleh.algorithm.util.holder;

import ip91.chui.oleh.model.dto.teacher.TeacherDto;
import ip91.chui.oleh.model.entity.User;
import ip91.chui.oleh.model.entity.teacher.TeacherLimits;
import ip91.chui.oleh.model.mapping.teacher.TeacherMapper;
import ip91.chui.oleh.repository.teacher.TeacherRepository;
import ip91.chui.oleh.service.auth.AuthenticationService;
import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
@RequiredArgsConstructor
public class TeacherHolder {

  private final TeacherRepository teacherRepository;
  private final TeacherMapper teacherMapper;
  private final AuthenticationService authService;
  @Getter
  private Set<TeacherDto> teachers;

  @PostConstruct
  void afterInit() {
    User user = authService.extractPrincipalFromSecurityContextHolder();

    this.teachers = teacherRepository.findAllByUserId(user.getId())
        .stream()
        .map(teacherMapper::teacherToDto)
        .collect(Collectors.toSet());
  }

  public TeacherDto getTeacherById(Long id) {
    return teachers.stream()
        .filter(teacher -> teacher.getId().equals(id))
        .findFirst()
        .orElseThrow(() -> new RuntimeException("Non-existent teacher"));
  }
}
