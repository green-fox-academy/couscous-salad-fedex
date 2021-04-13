package com.greenfoxacademy.fedex.controllers;

import com.greenfoxacademy.fedex.exceptions.InvalidMemeException;
import com.greenfoxacademy.fedex.exceptions.InvalidReactionException;
import com.greenfoxacademy.fedex.models.ReactionRequestDTO;
import com.greenfoxacademy.fedex.models.ReactionResponseDTO;
import com.greenfoxacademy.fedex.services.MemeService;
import com.greenfoxacademy.fedex.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class MemeController {

    private MemeService memeService;
    private UserService userService;

    @Autowired
    public MemeController(MemeService memeService, UserService userService) {
        this.memeService = memeService;
        this.userService = userService;
    }

    @PutMapping("/reaction/{memeId}")
    public ResponseEntity<ReactionResponseDTO> giveReaction(
            @PathVariable Long memeId, @RequestBody ReactionRequestDTO reactionRequest)
            throws InvalidReactionException, InvalidMemeException {
        return ResponseEntity.ok(memeService.giveReaction(userService.getAuthenticatedUser(), memeId, reactionRequest));
    }
}
