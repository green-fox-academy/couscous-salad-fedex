package com.greenfoxacademy.fedex.repositories;

import com.greenfoxacademy.fedex.models.reactions.Reaction;
import com.greenfoxacademy.fedex.models.reactions.ReactionType;
import org.springframework.data.repository.CrudRepository;

public interface ReactionRepository extends CrudRepository<Reaction, ReactionType> {
}
