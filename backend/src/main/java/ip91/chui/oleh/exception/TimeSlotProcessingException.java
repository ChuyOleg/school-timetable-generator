package ip91.chui.oleh.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class TimeSlotProcessingException extends RuntimeException {

  public TimeSlotProcessingException(String message) {
    super(message);
  }

}
