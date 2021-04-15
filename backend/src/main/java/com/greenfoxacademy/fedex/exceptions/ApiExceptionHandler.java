package com.greenfoxacademy.fedex.exceptions;

import com.greenfoxacademy.fedex.models.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ApiExceptionHandler {
  private final String errorStr = "error";

  @ExceptionHandler(value = MissingParametersException.class)
  public ResponseEntity<ResponseDto> handleMissingParametersException(
      MissingParametersException e) {
    log.warn(e.getClass().getName() + " " + e.getMessage());
    return ResponseEntity.status(400)
        .body(new ResponseDto(errorStr,"Missing parameters found, please amend your input: " + e.getMessage(),null));
  }

  @ExceptionHandler(value = {UsernameNotFoundException.class})
  public ResponseEntity<ResponseDto> handleUsernameNotFoundException(Exception e) {
    log.warn(e.getClass().getName() + " " + e.getMessage());
    return ResponseEntity.status(401).body(new ResponseDto(errorStr,e.getMessage(),null));
  }

  @ExceptionHandler(value = BadCredentialsException.class)
  public ResponseEntity<ResponseDto> handleBadCredentialsException(BadCredentialsException e) {
    log.warn(e.getClass().getName() + " " + e.getMessage());
    return ResponseEntity.status(403).body(new ResponseDto(errorStr,"Wrong password",null));
  }

  @ExceptionHandler(value = {InvalidParametersException.class, ParamAlreadyExistException.class})
  public ResponseEntity<ResponseDto> handle4xxExceptionGeneral(Exception e) {
    log.warn(e.getClass().getName() + " " + e.getMessage());
    return ResponseEntity.status(400).body(new ResponseDto(errorStr,e.getMessage(),null));
  }

  @ExceptionHandler(InvalidMemeException.class)
  public ResponseEntity<ResponseDto> handleInvalidMemeException(InvalidMemeException ex) {
    log.warn(ex.getClass().getName() + " " + ex.getMessage());
    return ResponseEntity.status(400).body(new ResponseDto(errorStr, ex.getMessage(), null));
  }

  @ExceptionHandler(InvalidReactionException.class)
  public ResponseEntity<ResponseDto> handleInvalidReactionException(InvalidReactionException ex) {
    log.warn(ex.getClass().getName() + " " + ex.getMessage());
    return ResponseEntity.status(400).body(new ResponseDto(errorStr, ex.getMessage(), null));
  }

  @ExceptionHandler(value = RuntimeException.class)
  public ResponseEntity<Object> handleUncaughtException(RuntimeException e) {
    log.error("Uncaught " + e.getClass().getName() + " at " + e.getStackTrace()[0].toString());
    return ResponseEntity.status(400)
        .body("Something bad happened. Blame it on the Democrats or the Republicans, as you like.");
  }


}
