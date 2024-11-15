package ip91.chui.oleh.controller;

import ip91.chui.oleh.model.dto.ShiftsIntersectionDto;
import ip91.chui.oleh.service.ShiftsIntersectionService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shifts")
public class ShiftsIntersectionController {

  private final ShiftsIntersectionService service;

  @GetMapping
  public List<ShiftsIntersectionDto> getAll() {
    return service.getAll();
  }

  @PostMapping
  public ShiftsIntersectionDto add(@RequestBody ShiftsIntersectionDto dto) {
    return service.create(dto);
  }

  @PutMapping
  public ShiftsIntersectionDto update(@RequestBody ShiftsIntersectionDto dto) {
    return service.update(dto);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public void deleteById(@PathVariable Long id) {
    service.delete(id);
  }
}
