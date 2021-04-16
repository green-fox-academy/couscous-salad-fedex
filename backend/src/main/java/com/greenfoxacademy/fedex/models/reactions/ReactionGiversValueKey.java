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
public class ReactionGiversValueKey implements Serializable {
    @Column(name = "reaction_givers_id")
    private ReactionGiversKey reactionGiversId;

    @Column(name = "user_id")
    private Long userId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReactionGiversValueKey that = (ReactionGiversValueKey) o;
        return reactionGiversId.equals(that.reactionGiversId) && userId.equals(that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reactionGiversId, userId);
    }
}
