package com.greenfoxacademy.fedex.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Comment {
    @EmbeddedId
    private CommentKey id;

    @ManyToOne
    @MapsId("memeId")
    @JoinColumn(name = "meme_id")
    private Meme meme;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @JsonProperty("comment_text")
    @Column(columnDefinition = "TEXT")
    private String commentText;

    public Comment(Meme meme, User user, String commentText) {
        this.id = new CommentKey();
        this.meme = meme;
        this.user = user;
        this.commentText = commentText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return id.equals(comment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
