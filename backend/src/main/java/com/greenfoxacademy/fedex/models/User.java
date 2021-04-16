package com.greenfoxacademy.fedex.models;

import com.greenfoxacademy.fedex.models.reactions.ReactionGiversValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String password;

    @OneToMany(mappedBy = "user")
    private List<ReactionGiversValue> reactionList;

    @OneToMany(mappedBy = "user")
    private List<Comment> comments = new ArrayList<>();

    public User(String username, String email, String password) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public void addComment(Comment comment) {
        if (comments.contains(comment)) {
            comments.get(comments.indexOf(comment)).setCommentText(comment.getCommentText());
        } else {
            comments.add(comment);
        }
    }
}

