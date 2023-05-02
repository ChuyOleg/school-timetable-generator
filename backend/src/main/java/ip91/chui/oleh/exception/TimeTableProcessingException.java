package ip91.chui.oleh.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class TimeTableProcessingException extends RuntimeException {

  public TimeTableProcessingException(String message) {
    super(message);
  }

}
