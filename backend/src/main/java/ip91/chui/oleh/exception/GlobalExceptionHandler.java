package ip91.chui.oleh.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(value = {SubjectDtoValidationException.class})
  public ResponseEntity<String> handleSubjectDtoValidationException(SubjectDtoValidationException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
  }

  @ExceptionHandler(value = {SubjectProcessingException.class})
  public ResponseEntity<String> handleSubjectProcessingException(SubjectProcessingException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
  }

  @ExceptionHandler(value = {TeacherDtoValidationException.class})
  public ResponseEntity<String> handleTeacherDtoValidationException(TeacherDtoValidationException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
  }

  @ExceptionHandler(value = {TeacherProcessingException.class})
  public ResponseEntity<String> handleTeacherProcessingException(TeacherProcessingException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
  }

  @ExceptionHandler(value = {RoomDtoValidationException.class})
  public ResponseEntity<String> handleRoomDtoValidationException(RoomDtoValidationException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
  }

  @ExceptionHandler(value = {RoomProcessingException.class})
  public ResponseEntity<String> handleRoomProcessingException(RoomProcessingException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
  }

}
