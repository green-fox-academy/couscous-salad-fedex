package com.greenfoxacademy.fedex.repositories;

import com.greenfoxacademy.fedex.models.reactions.ReactionGivers;
import com.greenfoxacademy.fedex.models.reactions.ReactionGiversKey;
import com.greenfoxacademy.fedex.models.reactions.ReactionType;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ReactionGiversRepository extends CrudRepository<ReactionGivers, ReactionGiversKey> {
    Optional<ReactionGivers> findById_MemeIdAndId_ReactionId(Long memeId, ReactionType reactionId);
}
