package ip91.chui.oleh.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class LessonDtoValidationException extends RuntimeException {

  public LessonDtoValidationException(String message) {
    super(message);
  }

}
