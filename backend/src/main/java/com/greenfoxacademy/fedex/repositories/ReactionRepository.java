package com.greenfoxacademy.fedex.repositories;

import com.greenfoxacademy.fedex.models.reactions.Reaction;
import com.greenfoxacademy.fedex.models.reactions.ReactionType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReactionRepository extends CrudRepository<Reaction, ReactionType> {
    @Query(value = "SELECT * FROM reaction ORDER  BY id", nativeQuery = true)
    List<Reaction> findAllOriginalOrder();
}
