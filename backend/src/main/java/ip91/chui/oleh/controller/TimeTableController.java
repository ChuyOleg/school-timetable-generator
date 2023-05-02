package ip91.chui.oleh.controller;

import ip91.chui.oleh.model.dto.lightweigth.TimeTableDtoLightWeight;
import ip91.chui.oleh.service.TimeTableService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/timetables")
public class TimeTableController {

  private final TimeTableService service;

  @GetMapping
  public TimeTableDtoLightWeight getForUser() {
    return service.getForUser();
  }

  @GetMapping("/generate")
  public TimeTableDtoLightWeight generate() {
    return service.generate();
  }

  @PostMapping
  public TimeTableDtoLightWeight add(@RequestBody TimeTableDtoLightWeight timeTableDtoLightWeight) {
    return service.create(timeTableDtoLightWeight);
  }

  @PutMapping
  public TimeTableDtoLightWeight update(@RequestBody TimeTableDtoLightWeight timeTableDtoLightWeight) {
    return service.update(timeTableDtoLightWeight);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public void deleteById(@PathVariable Long id) {
    service.delete(id);
  }

}
