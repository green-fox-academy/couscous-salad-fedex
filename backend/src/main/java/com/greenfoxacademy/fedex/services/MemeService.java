package com.greenfoxacademy.fedex.services;

import com.greenfoxacademy.fedex.exceptions.InvalidMemeException;
import com.greenfoxacademy.fedex.models.Meme;
import com.greenfoxacademy.fedex.models.MemeDTO;
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

    public Meme getMemeFromId(Long memeId)
            throws InvalidMemeException {
        return memeRepository.findById(memeId).orElseThrow(() -> new InvalidMemeException("Invalid Meme ID"));
    }

    public List<MemeDTO> getAllMemes() {
        List<MemeDTO> memeList = new ArrayList<>();
        memeRepository.findAll().forEach(
                meme -> memeList.add(new MemeDTO(meme.getMemePath(), reactionService.reactionsOfOneMeme(meme))));
        return memeList;
    }
}
