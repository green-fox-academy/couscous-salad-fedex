package com.greenfoxacademy.fedex.models.reactions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReactionResponseDTO {
    private HttpStatus status;
    private String message;
    @JsonProperty("meme_id")
    private Long memeId;
    @JsonProperty("reaction_type")
    private String reactionType;


    public ReactionResponseDTO(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
     public ReactionResponseDTO(ReactionGivers reactionGivers) {
        memeId = reactionGivers.getMeme().getId();
        reactionType = reactionGivers.getReaction().getType();
     }
}
