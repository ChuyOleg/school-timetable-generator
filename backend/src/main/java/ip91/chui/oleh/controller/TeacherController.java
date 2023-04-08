package ip91.chui.oleh.controller;

import ip91.chui.oleh.model.dto.TeacherDto;
import ip91.chui.oleh.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/teachers")
public class TeacherController {

  private final TeacherService teacherService;

  @GetMapping
  public List<TeacherDto> getAll() {
    return teacherService.getAll();
  }

  @GetMapping("/freeClassTeachers")
  public List<TeacherDto> getAllWhoAreNotClassTeacher() {
    return teacherService.getAllWhoAreNotClassTeacher();
  }

  @GetMapping("/{id}")
  public TeacherDto getById(@PathVariable Long id) {
    return teacherService.getById(id);
  }

  @PostMapping
  public TeacherDto add(@RequestBody TeacherDto teacherDto) {
    return teacherService.create(teacherDto);
  }

  @PutMapping
  public TeacherDto update(@RequestBody TeacherDto teacherDto) {
    return teacherService.update(teacherDto);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public void deleteById(@PathVariable Long id) {
    teacherService.delete(id);
  }

}
