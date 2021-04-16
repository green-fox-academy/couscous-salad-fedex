package com.greenfoxacademy.fedex.controllers;

import com.greenfoxacademy.fedex.exceptions.InvalidMemeException;
import com.greenfoxacademy.fedex.exceptions.InvalidReactionException;
import com.greenfoxacademy.fedex.models.MemeDTO;
import com.greenfoxacademy.fedex.models.reactions.UserMemeDTO;
import com.greenfoxacademy.fedex.models.MemeRequestDTO;
import com.greenfoxacademy.fedex.services.MemeService;
import com.greenfoxacademy.fedex.services.ReactionService;
import com.greenfoxacademy.fedex.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MemeController {

    private MemeService memeService;
    private ReactionService reactionService;
    private UserService userService;

    @Autowired
    public MemeController(MemeService memeService, ReactionService reactionService, UserService userService) {
        this.memeService = memeService;
        this.reactionService = reactionService;
        this.userService = userService;
    }

    @CrossOrigin
    @GetMapping("/meme")
    public ResponseEntity<List<MemeDTO>> getAllMemes() {
        return ResponseEntity.ok(memeService.getAllMemes());
    }

    @CrossOrigin
    @GetMapping("/meme/{memeId}")
    public ResponseEntity<UserMemeDTO> getMemeById(@PathVariable Long memeId) throws InvalidMemeException {
        return ResponseEntity.ok(reactionService.getMetadataByMemeAndUser(userService.getAuthenticatedUser(), memeService.getMemeFromId(memeId)));
    }

    @CrossOrigin
    @PostMapping("meme")
    public ResponseEntity<MemeDTO> uploadMeme(@RequestBody MemeRequestDTO meme) throws InvalidMemeException {
        return ResponseEntity.ok(memeService.saveMeme(meme));
    }

    @CrossOrigin
    @PutMapping("/meme/{memeId}")
    public ResponseEntity<List<MemeDTO>> giveReactionAndComment(
            @PathVariable Long memeId, @RequestBody UserMemeDTO reactionRequest)
            throws InvalidReactionException, InvalidMemeException {
        reactionService.addUserReactionAndComment(
                userService.getAuthenticatedUser(), memeService.getMemeFromId(memeId), reactionRequest);
        return ResponseEntity.ok(memeService.getAllMemes());
    }
}
