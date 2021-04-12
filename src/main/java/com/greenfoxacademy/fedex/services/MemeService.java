package com.greenfoxacademy.fedex.services;

import com.greenfoxacademy.fedex.exceptions.InvalidMemeException;
import com.greenfoxacademy.fedex.exceptions.InvalidReactionException;
import com.greenfoxacademy.fedex.models.*;
import com.greenfoxacademy.fedex.repositories.MemeRepository;
import com.greenfoxacademy.fedex.repositories.ReactionGiversRepository;
import com.greenfoxacademy.fedex.repositories.ReactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemeService {
    private MemeRepository memeRepository;
    private ReactionRepository reactionRepository;
    private ReactionGiversRepository reactionGiversRepository;

    @Autowired
    public MemeService(MemeRepository memeRepository, ReactionRepository reactionRepository,
                       ReactionGiversRepository reactionGiversRepository) {
        this.memeRepository = memeRepository;
        this.reactionRepository = reactionRepository;
        this.reactionGiversRepository = reactionGiversRepository;
    }

    public ReactionResponseDTO giveReaction(User user, Long memeId, ReactionRequestDTO reactionRequest)
            throws InvalidMemeException, InvalidReactionException {
        Meme meme = checkIfValidMemeId(memeId);
        Reaction reaction = checkReactionType(reactionRequest);
        return new ReactionResponseDTO(saveReaction(user, meme, reaction));
    }

    private ReactionGivers saveReaction(User user, Meme meme, Reaction reaction)
            throws InvalidReactionException {
        Optional<ReactionGivers> optionalReactionGivers =
                reactionGiversRepository.findById_MemeIdAndId_ReactionId(meme.getId(), reaction.getId());
        ReactionGivers reactionGivers;
        if (!optionalReactionGivers.isPresent()) {
            reactionGivers = createNewReactionGivers(user, meme, reaction);
        } else {
            reactionGivers = updateReactionGivers(optionalReactionGivers, user);
        }
        return reactionGivers;
    }

    private ReactionGivers createNewReactionGivers(User user, Meme meme, Reaction reaction) {
        return reactionGiversRepository.save(new ReactionGivers(meme, reaction, user));
    }

    private ReactionGivers updateReactionGivers(Optional<ReactionGivers> optionalReactionGivers, User user)
            throws InvalidReactionException {
        ReactionGivers reactionGivers = optionalReactionGivers.get();
        reactionGivers.addUser(user);
        return reactionGiversRepository.save(reactionGivers);
    }

    private Reaction checkReactionType(ReactionRequestDTO reactionRequest)
            throws InvalidReactionException {
        if (reactionRequest == null || reactionRequest.getReactionType() == null) {
            throw new InvalidReactionException("Missing parameter: reaction type");
        }
        return reactionRepository.findById(
                reactionRequest.getReactionType()).orElseThrow(() -> new InvalidReactionException("Invalid Reaction Type"));
    }

    private Meme checkIfValidMemeId(Long memeId)
            throws InvalidMemeException {
        return memeRepository.findById(memeId).orElseThrow(() -> new InvalidMemeException("Invalid Meme ID"));
    }
}
