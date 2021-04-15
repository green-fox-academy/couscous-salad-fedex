package com.greenfoxacademy.fedex.models.reactions;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserMemeDTO {
    @JsonProperty(value = "reaction_list")
    private List<ReactionDTO> reactionList;
    private String comment;
}
