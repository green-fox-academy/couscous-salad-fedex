package com.greenfoxacademy.fedex.controllers;

import com.greenfoxacademy.fedex.exceptions.MissingParametersException;
import com.greenfoxacademy.fedex.exceptions.UserNameAlreadyExistException;
import com.greenfoxacademy.fedex.models.ResponseDto;
import com.greenfoxacademy.fedex.models.User;
import com.greenfoxacademy.fedex.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class UserController {

  @Autowired
  UserService userService;

  @PostMapping("/login")
  public ResponseEntity<ResponseDto> login(@RequestBody User loginRequest) throws MissingParametersException {

    return ResponseEntity.status(200).body(userService.validateLogin(loginRequest));
  }

  @PostMapping("/register")
  public ResponseEntity<ResponseDto> register(@RequestBody User registrationRequest)
      throws MissingParametersException, UserNameAlreadyExistException {

    return ResponseEntity.status(200).body(userService.register(registrationRequest));
  }
}
