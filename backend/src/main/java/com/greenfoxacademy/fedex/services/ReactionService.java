package com.greenfoxacademy.fedex.services;

import com.greenfoxacademy.fedex.exceptions.InvalidReactionException;
import com.greenfoxacademy.fedex.models.*;
import com.greenfoxacademy.fedex.models.reactions.*;
import com.greenfoxacademy.fedex.repositories.ReactionGiversRepository;
import com.greenfoxacademy.fedex.repositories.ReactionGiversValueRepository;
import com.greenfoxacademy.fedex.repositories.ReactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReactionService {
    private ReactionRepository reactionRepository;
    private ReactionGiversRepository reactionGiversRepository;
    private ReactionGiversValueRepository reactionGiversValueRepository;

    @Autowired
    public ReactionService(ReactionRepository reactionRepository,
                           ReactionGiversRepository reactionGiversRepository,
                           ReactionGiversValueRepository reactionGiversValueRepository) {
        this.reactionRepository = reactionRepository;
        this.reactionGiversRepository = reactionGiversRepository;
        this.reactionGiversValueRepository = reactionGiversValueRepository;
    }

    public ReactionResponseDTO giveReaction(User user, Meme meme, ReactionRequestDTO reactionRequest)
            throws InvalidReactionException {
        Reaction reaction = checkIfValidReactionRequest(reactionRequest);
        saveReaction(user, meme, reaction, reactionRequest.getAmount());
        ReactionGivers reactionGivers = reactionGiversRepository.findById_MemeIdAndId_ReactionId(meme.getId(), reaction.getId()).get();
        return new ReactionResponseDTO(reactionGivers);
    }

    private void saveReaction(User user, Meme meme, Reaction reaction, Integer amount) {
        Optional<ReactionGivers> optionalReactionGivers =
                reactionGiversRepository.findById_MemeIdAndId_ReactionId(meme.getId(), reaction.getId());
        if (!optionalReactionGivers.isPresent()) {
            createNewReactionGivers(user, meme, reaction, amount);
        } else {
            updateReactionGivers(optionalReactionGivers.get(), user, amount);
        }
    }

    private void createNewReactionGivers(User user, Meme meme, Reaction reaction, Integer amount) {
        ReactionGivers reactionGivers = reactionGiversRepository.save(new ReactionGivers(meme, reaction));
        reactionGiversValueRepository.save(new ReactionGiversValue(reactionGivers, user, amount));
    }

    private void updateReactionGivers(ReactionGivers reactionGivers, User user, Integer amount) {
        Optional<ReactionGiversValue> optionalReactionGiversValue =
                reactionGivers.getReactionGiversValueList().stream()
                .filter(rgv -> rgv.getUser().getId().equals(user.getId()))
                .findFirst();
        if (optionalReactionGiversValue.isPresent()) {
            updateReactionGiversValue(amount, optionalReactionGiversValue.get());
        } else {
            reactionGiversValueRepository.save(new ReactionGiversValue(reactionGivers, user, amount));
        }
    }

    private void updateReactionGiversValue(Integer amount, ReactionGiversValue reactionGiversValue) {
        reactionGiversValue.setAmount(amount);
        reactionGiversValueRepository.save(reactionGiversValue);
    }

    private Reaction checkIfValidReactionRequest(ReactionRequestDTO reactionRequest)
            throws InvalidReactionException {
        if (reactionRequest == null || (reactionRequest.getReactionType() == null && reactionRequest.getAmount() == null)) {
            throw new InvalidReactionException("Missing parameter: reaction type and amount");
        }
        if (reactionRequest.getReactionType() == null) {
            throw new InvalidReactionException("Missing parameter: reaction type");
        }
        checkIfValidReactionValue(reactionRequest);
        return reactionRepository.findById(
                reactionRequest.getReactionType()).orElseThrow(() -> new InvalidReactionException("Invalid Reaction Type"));
    }

    private void checkIfValidReactionValue(ReactionRequestDTO reactionRequest) throws InvalidReactionException {
        if (reactionRequest.getAmount() == null) {
            throw new InvalidReactionException("Missing parameter: amount");
        }
        if (reactionRequest.getAmount() < 0) {
            throw new InvalidReactionException("Reaction amount should be at least 0");
        }
    }

    public List<ReactionDTO> reactionsOfOneMeme(Meme meme) {
        List<ReactionDTO> reactionList = new ArrayList<>();
        meme.getReactionGiversList()
                .forEach(rg -> reactionList.add(new ReactionDTO(rg.getReaction().getId(), getValueOfReaction(rg))));
        return reactionList;
    }

    private Integer getValueOfReaction(ReactionGivers reactionGivers) {
        return reactionGivers.getReactionGiversValueList()
                .stream().mapToInt(ReactionGiversValue::getAmount)
                .sum();
    }
}
