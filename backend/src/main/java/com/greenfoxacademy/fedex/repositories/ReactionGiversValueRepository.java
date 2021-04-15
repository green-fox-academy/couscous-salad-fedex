package com.greenfoxacademy.fedex.repositories;

import com.greenfoxacademy.fedex.models.User;
import com.greenfoxacademy.fedex.models.reactions.ReactionGiversValue;
import com.greenfoxacademy.fedex.models.reactions.ReactionGiversValueKey;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReactionGiversValueRepository extends CrudRepository<ReactionGiversValue, ReactionGiversValueKey> {
    List<ReactionGiversValue> findByUser(User user);
}
