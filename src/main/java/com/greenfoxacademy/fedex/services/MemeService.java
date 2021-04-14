package com.greenfoxacademy.fedex.services;

import com.greenfoxacademy.fedex.exceptions.InvalidMemeException;
import com.greenfoxacademy.fedex.exceptions.InvalidReactionException;
import com.greenfoxacademy.fedex.models.*;
import com.greenfoxacademy.fedex.repositories.MemeRepository;
import com.greenfoxacademy.fedex.repositories.ReactionGiversRepository;
import com.greenfoxacademy.fedex.repositories.ReactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
        if (!optionalReactionGivers.isPresent()) {
            return createNewReactionGivers(user, meme, reaction);
        }
        return updateReactionGivers(optionalReactionGivers.get(), user);
    }

    private ReactionGivers createNewReactionGivers(User user, Meme meme, Reaction reaction) {
        return reactionGiversRepository.save(new ReactionGivers(meme, reaction, user));
    }

    private ReactionGivers updateReactionGivers(ReactionGivers reactionGivers, User user)
            throws InvalidReactionException {
        checkIfUserAlreadyReacted(reactionGivers, user);
        reactionGivers.addUser(user);
        return reactionGiversRepository.save(reactionGivers);
    }

    private void checkIfUserAlreadyReacted(ReactionGivers reactionGivers, User user)
            throws InvalidReactionException {
        Optional<ReactionGivers> optionalReactionGivers = user.getReactionList().stream()
                .filter(rg -> rg.getMeme().getId().equals(reactionGivers.getMeme().getId()))
                .findFirst();
        if (optionalReactionGivers.isPresent()) {
            throw new InvalidReactionException("This user already reacted to this meme.");
        }
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

    public List<MemeDTO> getAllMemes() {
        List<MemeDTO> memeList = new ArrayList<>();
        memeRepository.findAll().forEach(
                meme -> memeList.add(new MemeDTO(meme.getMemePath(), reactionsOfOneMeme(meme))));
        return memeList;
    }

    private List<ReactionDTO> reactionsOfOneMeme(Meme meme) {
        List<ReactionDTO> reactionList = new ArrayList<>();
        meme.getReactionGiversList()
                .forEach(rg -> reactionList.add(new ReactionDTO(rg.getReaction().getId(), rg.getUserList().size())));
        return reactionList;
    }
}
