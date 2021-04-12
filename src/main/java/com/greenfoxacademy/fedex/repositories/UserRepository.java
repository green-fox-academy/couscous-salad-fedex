package com.greenfoxacademy.fedex.repositories;

import com.greenfoxacademy.fedex.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
