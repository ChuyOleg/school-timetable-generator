package ip91.chui.oleh.service;

import ip91.chui.oleh.exception.RoomDtoValidationException;
import ip91.chui.oleh.exception.RoomProcessingException;
import ip91.chui.oleh.model.dto.room.RoomDto;
import ip91.chui.oleh.model.entity.room.Room;
import ip91.chui.oleh.model.entity.User;
import ip91.chui.oleh.model.mapping.RoomMapper;
import ip91.chui.oleh.repository.RoomRepository;
import ip91.chui.oleh.service.auth.AuthenticationService;
import ip91.chui.oleh.validator.DtoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomService {

  private static final String ROOM_NOT_FOUND_BY_ID_MSG = "Room hasn't been found with ID = (%d)";
  private static final String ROOM_ID_SHOULD_BE_NOT_NULL_MSG = "You have to specify ID of the room";

  private final AuthenticationService authService;
  private final RoomRepository roomRepository;
  private final RoomMapper roomMapper;
  private final DtoValidator<RoomDto> roomDtoDtoValidator;

  public List<RoomDto> getAll() {
    User user = authService.extractPrincipalFromSecurityContextHolder();

    return roomRepository.findAllByUserId(user.getId())
        .stream()
        .map(roomMapper::roomToDto)
        .collect(Collectors.toList());
  }

  public RoomDto getById(Long id) {
    return roomMapper.roomToDto(findRoomById(id));
  }

  public RoomDto create(RoomDto roomDto) {
    validateRoomDto(roomDto);

    User user = authService.extractPrincipalFromSecurityContextHolder();

    Room roomToSave = roomMapper.dtoToRoom(roomDto);
    roomToSave.setUser(user);
    Room savedRoom = roomRepository.save(roomToSave);

    return roomMapper.roomToDto(savedRoom);
  }

  public RoomDto update(RoomDto roomDto) {
    validateIdIsNotNull(roomDto);
    validateRoomDto(roomDto);

    if (roomRepository.existsById(roomDto.getId())) {
      Room roomToUpdate = roomMapper.dtoToRoom(roomDto);
      Room updatedRoom = roomRepository.save(roomToUpdate);
      return roomMapper.roomToDto(updatedRoom);
    } else {
      throw new RoomProcessingException(String.format(ROOM_NOT_FOUND_BY_ID_MSG, roomDto.getId()));
    }
  }

  public void delete(Long id) {
    if (roomRepository.existsById(id)) {
      roomRepository.deleteById(id);
    } else {
      throw new RoomProcessingException(String.format(ROOM_NOT_FOUND_BY_ID_MSG, id));
    }
  }

  private Room findRoomById(Long id) {
    return roomRepository.findById(id)
        .orElseThrow(() -> new RoomProcessingException(String.format(ROOM_NOT_FOUND_BY_ID_MSG, id)));
  }

  private void validateRoomDto(RoomDto roomDto) {
    Set<String> violations = roomDtoDtoValidator.validate(roomDto);
    if (!violations.isEmpty()) {
      throw new RoomDtoValidationException(violations.stream().collect(Collectors.joining(System.lineSeparator())));
    }
  }

  private void validateIdIsNotNull(RoomDto roomDto) {
    if (roomDto.getId() == null) {
      throw new RoomDtoValidationException(ROOM_ID_SHOULD_BE_NOT_NULL_MSG);
    }
  }

}
