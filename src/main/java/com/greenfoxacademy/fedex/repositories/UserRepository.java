package com.greenfoxacademy.fedex.repositories;

import com.greenfoxacademy.fedex.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {

  Optional<User> findByUsername(String username);
}
