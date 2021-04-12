package com.greenfoxacademy.fedex.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReactionRequestDTO {
    @JsonProperty(value = "reaction_type")
    private ReactionType reactionType;
}
