package com.greenfoxacademy.fedex.models.reactions;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReactionDTO {
    @JsonProperty("reaction_type")
    private ReactionType reactionType;
    private Integer amount;
}
