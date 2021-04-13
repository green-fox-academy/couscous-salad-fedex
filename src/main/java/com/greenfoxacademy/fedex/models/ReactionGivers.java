package com.greenfoxacademy.fedex.models;

import com.greenfoxacademy.fedex.exceptions.InvalidReactionException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "reaction_givers")
public class ReactionGivers {
    @EmbeddedId
    private ReactionGiversKey id;

    @ManyToOne
    @MapsId("memeId")
    @JoinColumn(name = "meme_id")
    private Meme meme;

    @ManyToOne
    @MapsId("reactionId")
    @JoinColumn(name = "reaction_id")
    private Reaction reaction;

    @ManyToMany (cascade = CascadeType.ALL)
    @JoinTable(
            name="reaction_givers_list",
            joinColumns = {
                    @JoinColumn(name = "meme_id", referencedColumnName = "meme_id"),
                    @JoinColumn(name = "reaction_id", referencedColumnName = "reaction_id")
            },
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> userList;

    public ReactionGivers() {
        id = new ReactionGiversKey();
        userList = new ArrayList<>();
    }

    public ReactionGivers(Meme meme, Reaction reaction) {
        id = new ReactionGiversKey();
        this.meme = meme;
        this.reaction = reaction;
        userList = new ArrayList<>();
    }

    public ReactionGivers(Meme meme, Reaction reaction, User user) {
        id = new ReactionGiversKey();
        this.meme = meme;
        this.reaction = reaction;
        userList = new ArrayList<>();
        userList.add(user);
    }

    public void addUser(User user) throws InvalidReactionException {
        if (userList.contains(user)) {
            throw new InvalidReactionException("This user already reacted to this meme.");
        }
        userList.add(user);
    }
}
