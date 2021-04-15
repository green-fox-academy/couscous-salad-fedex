package com.greenfoxacademy.fedex.repositories;

import com.greenfoxacademy.fedex.models.reactions.ReactionGiversValue;
import com.greenfoxacademy.fedex.models.reactions.ReactionGiversValueKey;
import org.springframework.data.repository.CrudRepository;

public interface ReactionGiversValueRepository extends CrudRepository<ReactionGiversValue, ReactionGiversValueKey> {
}
