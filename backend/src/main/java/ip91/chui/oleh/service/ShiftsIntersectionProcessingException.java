package ip91.chui.oleh.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ShiftsIntersectionProcessingException extends RuntimeException {

  public ShiftsIntersectionProcessingException(String message) {
    super(message);
  }
}
