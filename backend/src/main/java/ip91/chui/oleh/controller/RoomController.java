package ip91.chui.oleh.controller;

import ip91.chui.oleh.model.dto.RoomDto;
import ip91.chui.oleh.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms")
public class RoomController {

  private final RoomService roomService;

  @GetMapping
  public List<RoomDto> getAll() {
    return roomService.getAll();
  }

  @GetMapping("/{id}")
  public RoomDto getById(@PathVariable Long id) {
    return roomService.getById(id);
  }

  @PostMapping
  public RoomDto add(@RequestBody RoomDto roomDto) {
    return roomService.create(roomDto);
  }

  @PutMapping
  public RoomDto update(@RequestBody RoomDto roomDto) {
    return roomService.update(roomDto);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public void deleteById(@PathVariable Long id) {
    roomService.delete(id);
  }

}
