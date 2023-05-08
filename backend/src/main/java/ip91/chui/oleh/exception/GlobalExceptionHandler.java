package ip91.chui.oleh.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  private static final String UNEXPECTED_ERROR_MSG = "Something went wrong";

  @ExceptionHandler(value = {Throwable.class})
  public ResponseEntity<String> handleAllExceptions() {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(UNEXPECTED_ERROR_MSG);
  }

  @ExceptionHandler(value = {AuthenticationException.class})
  public ResponseEntity<String> handleAuthenticationException(AuthenticationException ex) {
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
  }

  @ExceptionHandler(value = {UsernameNotFoundException.class})
  public ResponseEntity<String> handleUsernameNotFoundException(UsernameNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
  }

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

  @ExceptionHandler(value = {LessonDtoValidationException.class})
  public ResponseEntity<String> handleLessonDtoValidationException(LessonDtoValidationException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
  }

  @ExceptionHandler(value = {LessonProcessingException.class})
  public ResponseEntity<String> handleLessonProcessingException(LessonProcessingException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
  }

  @ExceptionHandler(value = {GroupDtoValidationException.class})
  public ResponseEntity<String> handleGroupDtoValidationException(GroupDtoValidationException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
  }

  @ExceptionHandler(value = {GroupProcessingException.class})
  public ResponseEntity<String> handleGroupProcessingException(GroupProcessingException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
  }

  @ExceptionHandler(value = {TimeSlotProcessingException.class})
  public ResponseEntity<String> handleTimeSlotProcessingException(TimeSlotProcessingException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
  }

  @ExceptionHandler(value = {TimeTableProcessingException.class})
  public ResponseEntity<String> handleTimeTableProcessingException(TimeTableProcessingException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
  }

  @ExceptionHandler(value = {TimeTableDtoLightWeightValidationException.class})
  public ResponseEntity<String> handleTimeTableDtoValidationException(TimeTableDtoLightWeightValidationException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
  }

}
