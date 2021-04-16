package com.greenfoxacademy.fedex.models;

import com.greenfoxacademy.fedex.models.reactions.ReactionType;
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
public class CommentKey implements Serializable {
    @Column(name = "meme_id")
    private Long memeId;

    @Column(name = "user_id")
    private Long userId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentKey that = (CommentKey) o;
        return memeId.equals(that.memeId) && userId.equals(that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memeId, userId);
    }
}
