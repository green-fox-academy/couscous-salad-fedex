package com.greenfoxacademy.fedex.services;

import com.greenfoxacademy.fedex.exceptions.InvalidParametersException;
import com.greenfoxacademy.fedex.exceptions.MissingParametersException;
import com.greenfoxacademy.fedex.exceptions.ParamAlreadyExistException;
import com.greenfoxacademy.fedex.models.ResponseDto;
import com.greenfoxacademy.fedex.models.User;
import com.greenfoxacademy.fedex.models.reactions.ReactionGiversValue;
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
import java.util.regex.Pattern;

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
    List<String> missingParams = getMissingParams(loginRequest,false);
    if (missingParams.size() != 0) {
      throw new MissingParametersException(missingParams.toString());
    }
    User currentUser = userRepository.findByEmail(loginRequest.getEmail())
        .orElseThrow(()-> new UsernameNotFoundException("No user found with the email address"+ loginRequest.getEmail()));
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(currentUser.getUsername(),loginRequest.getPassword()));
    final UserDetails userDetails = customUserDetailsService.loadUserByUsername(currentUser.getUsername());
    final String token = jwtUtil.generateToken(userDetails);
    return new ResponseDto("ok",null,token);
  }

  public List<String> getMissingParams(User user, boolean isRegistration) {
    List<String> missingParams = new ArrayList<>();
    if ((user.getUsername() == null || user.getUsername().equals("")) && isRegistration) {
      missingParams.add("username");
    }
    if (user.getEmail() == null || user.getEmail().equals("")) {
      missingParams.add("email");
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
      throws MissingParametersException, ParamAlreadyExistException, InvalidParametersException {
    List<String> missingParams = getMissingParams(registrationRequest,true);
    if (missingParams.size() != 0) {
      throw new MissingParametersException(missingParams.toString());
    }
    if(userRepository.findByUsername(registrationRequest.getUsername()).isPresent()) {
      throw new ParamAlreadyExistException("Username already exists, please choose another one");
    }
    if(!isEmailValid(registrationRequest.getEmail())) {
      throw new InvalidParametersException("The format of the email address is not valid");
    }
    if(userRepository.findByEmail(registrationRequest.getEmail()).isPresent()) {
      throw new ParamAlreadyExistException("User with this email address already exists");
    }

    userRepository.save(new User(registrationRequest.getUsername(), registrationRequest.getEmail(), hashPassword(registrationRequest.getPassword())));

    return new ResponseDto("ok",registrationRequest.getUsername() + " rockz! Welcome to the Meme creator. You can now log in.", null);
  }

  public String hashPassword (String plainTextPassword) {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    return encoder.encode(plainTextPassword);
  }

  public boolean isEmailValid (String email) {
    String emailRegex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    Pattern pattern = Pattern.compile(emailRegex);
    return pattern.matcher(email).matches();
  }
}
