package ip91.chui.oleh.controller;

import ip91.chui.oleh.model.dto.GroupDto;
import ip91.chui.oleh.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/groups")
public class GroupController {

  private final GroupService groupService;

  @GetMapping
  public List<GroupDto> getAll() {
    return groupService.getAll();
  }

  @GetMapping("/{id}")
  public GroupDto getById(@PathVariable Long id) {
    return groupService.getById(id);
  }

  @PostMapping
  public GroupDto add(@RequestBody GroupDto groupDto) {
    return groupService.create(groupDto);
  }

  @PutMapping
  public GroupDto update(@RequestBody GroupDto groupDto) {
    return groupService.update(groupDto);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public void deleteById(@PathVariable Long id) {
    groupService.delete(id);
  }

}
