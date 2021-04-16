package com.greenfoxacademy.fedex.repositories;

import com.greenfoxacademy.fedex.models.ReactionGivers;
import com.greenfoxacademy.fedex.models.ReactionGiversKey;
import com.greenfoxacademy.fedex.models.ReactionType;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ReactionGiversRepository extends CrudRepository<ReactionGivers, ReactionGiversKey> {
    Optional<ReactionGivers> findById_MemeIdAndId_ReactionId(Long memeId, ReactionType reactionId);
}
