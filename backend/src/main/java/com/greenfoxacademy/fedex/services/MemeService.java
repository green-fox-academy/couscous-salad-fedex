package com.greenfoxacademy.fedex.services;

import com.greenfoxacademy.fedex.exceptions.InvalidMemeException;
import com.greenfoxacademy.fedex.models.*;
import com.greenfoxacademy.fedex.models.reactions.ReactionDTO;
import com.greenfoxacademy.fedex.models.reactions.ReactionGiversValue;
import com.greenfoxacademy.fedex.models.reactions.UserMemeDTO;
import com.greenfoxacademy.fedex.repositories.MemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemeService {
    private MemeRepository memeRepository;
    private ReactionService reactionService;

    @Autowired
    public MemeService(MemeRepository memeRepository, ReactionService reactionService) {
        this.memeRepository = memeRepository;
        this.reactionService = reactionService;
    }



    public Meme getMemeFromId(Long memeId) throws InvalidMemeException {
        return memeRepository.findById(memeId).orElseThrow(() -> new InvalidMemeException("Invalid Meme ID"));
    }

    private boolean checkMemeExistenceByPath(String memePath){
        return memeRepository.findMemeByMemePath(memePath).isPresent();
    }

    public MemeDTO saveMeme(MemeRequestDTO memeRequest) throws InvalidMemeException {
        if(!checkMemeExistenceByPath(memeRequest.getMemePath())){
            Meme meme = memeRepository.save(new Meme(memeRequest.getMemePath()));
            meme.setReactionGiversList(reactionService.addAllEmptyReactionToMeme(meme));
            memeRepository.save(meme);
            return memeToDTO(meme);
        } else throw new InvalidMemeException("This meme already exists.");
    }

    public MemeDTO memeToDTO(Meme meme){
        List<ReactionDTO> reactionList = getReactionDTOsOfOneMeme(meme);
        List<String> comments = getCommentsOfOneMeme(meme);
        return new MemeDTO(meme.getId(), meme.getMemePath(), reactionList, comments);
    }

    private List<String> getCommentsOfOneMeme(Meme meme) {
        return meme.getComments().stream()
                .map(Comment::getCommentText)
                .collect(Collectors.toList());
    }

    private List<ReactionDTO> getReactionDTOsOfOneMeme(Meme meme) {
        List<ReactionDTO> reactionList = new ArrayList<>();
        meme.getReactionGiversList()
                .forEach(rg -> reactionList.add(new ReactionDTO(
                        rg.getReaction().getId(),
                        rg.getReactionGiversValueList()
                                .stream().mapToInt(ReactionGiversValue::getAmount)
                                .sum()))
                );
        return reactionList;
    }

    public List<MemeDTO> getAllMemes() {
        List<MemeDTO> memeList = new ArrayList<>();
        memeRepository.findAll().forEach(
                meme -> memeList.add(new MemeDTO(
                        meme.getId(), meme.getMemePath(), reactionService.reactionsOfOneMeme(meme), getCommentsOfOneMeme(meme))));
        return memeList;
    }
}
