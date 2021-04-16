package com.greenfoxacademy.fedex.controllers;

import com.greenfoxacademy.fedex.exceptions.InvalidParametersException;
import com.greenfoxacademy.fedex.exceptions.MissingParametersException;
import com.greenfoxacademy.fedex.exceptions.ParamAlreadyExistException;
import com.greenfoxacademy.fedex.models.ResponseDto;
import com.greenfoxacademy.fedex.models.User;
import com.greenfoxacademy.fedex.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@Controller
public class UserController {

  @Autowired
  UserService userService;

  @CrossOrigin
  @PostMapping("/login")
  public ResponseEntity<ResponseDto> login(@RequestBody User loginRequest) throws MissingParametersException {
    log.info("Successful login for user: " + loginRequest.getEmail());
    return ResponseEntity.status(200).body(userService.validateLogin(loginRequest));
  }

  @CrossOrigin
  @PostMapping("/register")
  public ResponseEntity<ResponseDto> register(@RequestBody User registrationRequest)
      throws MissingParametersException, ParamAlreadyExistException, InvalidParametersException {

    return ResponseEntity.status(200).body(userService.register(registrationRequest));
  }
}
