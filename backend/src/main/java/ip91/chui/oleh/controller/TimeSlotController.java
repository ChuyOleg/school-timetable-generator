package ip91.chui.oleh.controller;

import ip91.chui.oleh.model.dto.TimeSlotDto;
import ip91.chui.oleh.service.TimeSlotService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/timeslots")
public class TimeSlotController {

  private final TimeSlotService timeSlotService;

  @GetMapping
  public List<TimeSlotDto> getAll(HttpServletResponse response) {
    setCacheHeader(response);
    return timeSlotService.getAll();
  }

  private void setCacheHeader(HttpServletResponse response) {
    response.setHeader("Cache-Control", "max-age=86400, must-revalidate, no-transform");
  }

}
