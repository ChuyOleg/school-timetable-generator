package ip91.chui.oleh.algorithm.util;

import ip91.chui.oleh.model.dto.GroupDto;
import ip91.chui.oleh.model.entity.User;
import ip91.chui.oleh.model.mapping.GroupMapper;
import ip91.chui.oleh.repository.GroupRepository;
import ip91.chui.oleh.service.auth.AuthenticationService;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequestScope
@RequiredArgsConstructor
public class GroupsHolder {

  private final GroupRepository groupRepository;
  private final GroupMapper groupMapper;
  private final AuthenticationService authService;
  @Getter
  private Set<GroupDto> groups;

  @PostConstruct
  void afterInit() {
    User user = authService.extractPrincipalFromSecurityContextHolder();

    this.groups = groupRepository.findAllByUserId(user.getId())
        .stream()
        .map(groupMapper::groupToDtoLimitedInfo)
        .collect(Collectors.toSet());
  }

}
