package com.greenfoxacademy.fedex.services;

import com.greenfoxacademy.fedex.exceptions.MissingParametersException;
import com.greenfoxacademy.fedex.exceptions.UserNameAlreadyExistException;
import com.greenfoxacademy.fedex.models.ResponseDto;
import com.greenfoxacademy.fedex.models.User;
import com.greenfoxacademy.fedex.repositories.UserRepository;
import com.greenfoxacademy.fedex.security.CustomUserDetailsService;
import com.greenfoxacademy.fedex.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

  @Autowired
  UserRepository userRepository;

  @Autowired
  private CustomUserDetailsService customUserDetailsService;

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private AuthenticationManager authenticationManager;

  public ResponseDto validateLogin(User loginRequest) throws MissingParametersException {
    if (getMissingParams(loginRequest).size() != 0) {
      throw new MissingParametersException(getMissingParams(loginRequest).toString());
    }
    User currentUser = userRepository.findByUsername(loginRequest.getUsername())
        .orElseThrow(()-> new UsernameNotFoundException("No user found with the username "+ loginRequest.getUsername()));
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));
    final UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginRequest.getUsername());
    final String token = jwtUtil.generateToken(userDetails);
    return new ResponseDto("ok",null,token);
  }

  public List<String> getMissingParams(User user) {
    List<String> missingParams = new ArrayList<>();
    if (user.getUsername() == null || user.getUsername().equals("")) {
      missingParams.add("username");
    }
    if (user.getPassword() == null || user.getPassword().equals("")) {
      missingParams.add("password");
    }
    return missingParams;
  }

  public User getAuthenticatedUser () {
    return userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        .orElseThrow(()-> new UsernameNotFoundException("Username not found, please log in again"));
  }

  public ResponseDto register (User registrationRequest)
      throws MissingParametersException, UserNameAlreadyExistException {
    if (getMissingParams(registrationRequest).size() != 0) {
      throw new MissingParametersException(getMissingParams(registrationRequest).toString());
    }
    if(userRepository.findByUsername(registrationRequest.getUsername()).isPresent()) {
      throw new UserNameAlreadyExistException("Username already exists, please choose another one");
    }
    userRepository.save(new User(registrationRequest.getUsername(),hashPassword(registrationRequest.getPassword())));

    return new ResponseDto("ok",registrationRequest.getUsername() + " rockz! Welcome to the Meme creator. You can now log in.", null);
  }

  public String hashPassword (String plainTextPassword) {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    return encoder.encode(plainTextPassword);
  }

}
