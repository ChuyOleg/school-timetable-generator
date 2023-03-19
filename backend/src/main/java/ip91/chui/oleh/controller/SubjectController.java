package ip91.chui.oleh.controller;

import ip91.chui.oleh.model.dto.SubjectDto;
import ip91.chui.oleh.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/subjects")
public class SubjectController {

  private final SubjectService subjectService;

  @GetMapping
  public List<SubjectDto> getAll() {
    return subjectService.getAll();
  }

  @GetMapping("/{id}")
  public SubjectDto getById(@PathVariable Long id) {
    return subjectService.getById(id);
  }

  @PostMapping
  public SubjectDto add(@RequestBody SubjectDto subjectDto) {
    return subjectService.create(subjectDto);
  }

  @PutMapping
  public SubjectDto update(@RequestBody SubjectDto subjectDto) {
    return subjectService.update(subjectDto);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public void deleteById(@PathVariable Long id) {
    subjectService.delete(id);
  }

}
