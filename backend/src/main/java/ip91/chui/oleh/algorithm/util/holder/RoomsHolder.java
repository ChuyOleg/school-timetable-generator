package ip91.chui.oleh.algorithm.util.holder;

import ip91.chui.oleh.model.dto.room.RoomDto;
import ip91.chui.oleh.model.entity.User;
import ip91.chui.oleh.model.mapping.RoomMapper;
import ip91.chui.oleh.repository.RoomRepository;
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
public class RoomsHolder {

  private final RoomRepository roomRepository;
  private final RoomMapper roomMapper;
  private final AuthenticationService authService;
  @Getter
  private Set<RoomDto> rooms;

  @PostConstruct
  void afterInit() {
    User user = authService.extractPrincipalFromSecurityContextHolder();

    this.rooms = roomRepository.findAllByUserId(user.getId())
        .stream()
        .map(roomMapper::roomToDto)
        .collect(Collectors.toSet());
  }

  public RoomDto getRoomById(Long id) {
    return rooms.stream()
        .filter(room -> room.getId().equals(id))
        .findFirst()
        .orElseThrow(() -> new RuntimeException("Non-existent room."));
  }
}
