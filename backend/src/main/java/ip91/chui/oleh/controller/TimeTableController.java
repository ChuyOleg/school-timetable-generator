package ip91.chui.oleh.controller;

import ip91.chui.oleh.model.dto.lightweigth.TimeTableDtoLightWeight;
import ip91.chui.oleh.service.TimeTableService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/timetables")
public class TimeTableController {

  private final TimeTableService service;

  @GetMapping("/generate")
  public TimeTableDtoLightWeight generate() {
    return service.generate();
  }

  @PostMapping
  public TimeTableDtoLightWeight add(@RequestBody TimeTableDtoLightWeight timeTableDtoLightWeight) {
    return service.create(timeTableDtoLightWeight);
  }

}
