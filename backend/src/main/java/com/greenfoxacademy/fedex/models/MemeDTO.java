package com.greenfoxacademy.fedex.models;

import com.greenfoxacademy.fedex.models.reactions.ReactionDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.greenfoxacademy.fedex.models.reactions.ReactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemeDTO {

    @JsonProperty(value = "meme_id")
    private Long memeId;
    @JsonProperty(value = "meme_path")
    private String memePath;
    @JsonProperty(value = "reaction_list")
    private List<ReactionDTO> reactionList;
    private List<String> comments;
}
