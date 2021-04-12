package com.greenfoxacademy.fedex.models;

import com.greenfoxacademy.fedex.exceptions.InvalidReactionException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class ReactionGiver {
    @EmbeddedId
    private ReactionGiverKey id;
    @ManyToMany
    @JoinTable(
            name="reaction_giver_list",
            joinColumns = {
                    @JoinColumn(name = "reaction_giver_meme_id", referencedColumnName = "meme_id"),
                    @JoinColumn(name = "reaction_giver_reaction_id", referencedColumnName = "reaction_id")
            },
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> userList;

    public ReactionGiver() {
        userList = new ArrayList<>();
    }

    public ReactionGiver(Long memeId, ReactionType reactionId) {
        id = new ReactionGiverKey(memeId, reactionId);
        userList = new ArrayList<>();
    }

    public void addUser(User user) throws InvalidReactionException {
        if (userList.contains(user)) {
            throw new InvalidReactionException("This user already reacted to this meme.");
        }
        userList.add(user);
    }
}
