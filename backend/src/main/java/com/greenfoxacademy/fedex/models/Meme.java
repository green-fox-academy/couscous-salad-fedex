package com.greenfoxacademy.fedex.models;

import com.greenfoxacademy.fedex.models.reactions.ReactionGivers;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Meme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String memePath;

    @OneToMany(mappedBy = "meme")
    private List<ReactionGivers> reactionGiversList;

    @OneToMany(mappedBy = "meme")
    private List<Comment> comments = new ArrayList<>();

    public Meme(String memePath) {
        this.memePath = memePath;
    }

    public void addComment(Comment comment) {
        if (comments.contains(comment)) {
            comments.get(comments.indexOf(comment)).setCommentText(comment.getCommentText());
        } else {
            comments.add(comment);
        }
    }
}