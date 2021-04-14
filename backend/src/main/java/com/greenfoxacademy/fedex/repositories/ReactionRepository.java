package com.greenfoxacademy.fedex.repositories;

import com.greenfoxacademy.fedex.models.Reaction;
import com.greenfoxacademy.fedex.models.ReactionType;
import org.springframework.data.repository.CrudRepository;

public interface ReactionRepository extends CrudRepository<Reaction, ReactionType> {
}
