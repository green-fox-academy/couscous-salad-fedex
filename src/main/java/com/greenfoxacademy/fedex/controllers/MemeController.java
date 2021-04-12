package com.greenfoxacademy.fedex.controllers;

import com.greenfoxacademy.fedex.exceptions.InvalidMemeException;
import com.greenfoxacademy.fedex.exceptions.InvalidReactionException;
import com.greenfoxacademy.fedex.models.ReactionRequestDTO;
import com.greenfoxacademy.fedex.models.ReactionResponseDTO;
import com.greenfoxacademy.fedex.models.User;
import com.greenfoxacademy.fedex.repositories.UserRepository;
import com.greenfoxacademy.fedex.services.MemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@Controller
public class MemeController {

    private MemeService memeService;

    @Autowired
    public MemeController(MemeService memeService) {
        this.memeService = memeService;
    }

    @Autowired
    UserRepository userRepository;

    @PutMapping("/reaction/{memeId}")
    public ResponseEntity<ReactionResponseDTO> giveReaction(@RequestHeader Long user, @PathVariable Long memeId, @RequestBody ReactionRequestDTO reactionRequest) {
        //TODO token instead of user in header

        ReactionResponseDTO reactionResponse;
        try {
            reactionResponse = memeService.giveReaction(userRepository.findById(user).get(), memeId, reactionRequest);
        } catch (InvalidMemeException | InvalidReactionException e) {
            reactionResponse = new ReactionResponseDTO(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return ResponseEntity.ok(reactionResponse);
    }
}
