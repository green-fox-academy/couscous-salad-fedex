package com.greenfoxacademy.fedex.security;

import com.greenfoxacademy.fedex.models.User;
import com.greenfoxacademy.fedex.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
  @Autowired
  UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> optionalUser = userRepository.findByUsername(username);
    optionalUser
        .orElseThrow(() -> new UsernameNotFoundException("No user with username " + username + "has been found."));
    return optionalUser.map(CustomUserDetails::new).get();
  }
}
