package com.greenfoxacademy.fedex.services;

import com.greenfoxacademy.fedex.exceptions.InvalidMemeException;
import com.greenfoxacademy.fedex.models.Meme;
import com.greenfoxacademy.fedex.models.MemeDTO;
import com.greenfoxacademy.fedex.models.MemeRequestDTO;
import com.greenfoxacademy.fedex.models.reactions.ReactionDTO;
import com.greenfoxacademy.fedex.models.reactions.ReactionGivers;
import com.greenfoxacademy.fedex.models.reactions.ReactionGiversValue;
import com.greenfoxacademy.fedex.repositories.MemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
            List<ReactionGivers> reactionGiversList = new ArrayList<>();
            Meme meme = new Meme(memeRequest.getMemePath(), reactionGiversList);
            memeRepository.save(meme);
            return memeToDTO(meme);
        } else throw new InvalidMemeException("This meme already exists.");
    }

    public Meme checkIfValidMemeId(Long memeId)
            throws InvalidMemeException {
        return memeRepository.findById(memeId).orElseThrow(() -> new InvalidMemeException("Invalid Meme ID"));
    }

    public MemeDTO memeToDTO(Meme meme){
        List<ReactionDTO> reactionList = new ArrayList<>();
        meme.getReactionGiversList()
            .forEach(rg -> reactionList.add(new ReactionDTO(
                rg.getReaction().getId(),
                rg.getReactionGiversValueList()
                .stream().mapToInt(ReactionGiversValue::getAmount)
                .sum()))
            );
        return new MemeDTO(meme.getMemePath(), reactionList);
    }

    public List<MemeDTO> getAllMemes() {
        List<MemeDTO> memeList = new ArrayList<>();
        memeRepository.findAll().forEach(
                meme -> memeList.add(new MemeDTO(meme.getMemePath(), reactionService.reactionsOfOneMeme(meme))));
        return memeList;
    }
}
