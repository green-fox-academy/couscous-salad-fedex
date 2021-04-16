package com.greenfoxacademy.fedex.models.reactions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ReactionGiversKey implements Serializable {
    @Column(name = "meme_id")
    private Long memeId;

    @Column(name = "reaction_id")
    private ReactionType reactionId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReactionGiversKey that = (ReactionGiversKey) o;
        return memeId.equals(that.memeId) && reactionId == that.reactionId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(memeId, reactionId);
    }
}
