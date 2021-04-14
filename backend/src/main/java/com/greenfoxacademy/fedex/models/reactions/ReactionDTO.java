package com.greenfoxacademy.fedex.models.reactions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReactionDTO {
    private ReactionType reaction_type;
    private Integer count;
}
