package ip91.chui.oleh.controller;

import ip91.chui.oleh.model.dto.LessonDto;
import ip91.chui.oleh.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lessons")
public class LessonController {

  private final LessonService lessonService;

  @GetMapping
  public List<LessonDto> getAll() {
    return lessonService.getAll();
  }

  @GetMapping("/{id}")
  public LessonDto getById(@PathVariable Long id) {
    return lessonService.getById(id);
  }

  @PostMapping
  public LessonDto add(@RequestBody LessonDto lessonDto) {
    return lessonService.create(lessonDto);
  }

  @PutMapping
  public LessonDto update(@RequestBody LessonDto lessonDto) {
    return lessonService.update(lessonDto);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public void deleteById(@PathVariable Long id) {
    lessonService.delete(id);
  }

}
