package com.greenfoxacademy.fedex.models.reactions;

import com.greenfoxacademy.fedex.models.User;
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
public class ReactionGiversValue {
    @EmbeddedId
    private ReactionGiversValueKey id;

    @ManyToOne
    @MapsId("reactionGiversId")
    @JoinColumns(value =
            {@JoinColumn(name = "meme_id", referencedColumnName = "meme_id"),
            @JoinColumn(name = "reaction_id", referencedColumnName = "reaction_id")})
    private ReactionGivers reactionGivers;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    private Integer amount;

    public ReactionGiversValue(ReactionGivers reactionGivers, User user, Integer amount) {
        id = new ReactionGiversValueKey();
        this.reactionGivers = reactionGivers;
        this.user = user;
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReactionGiversValue that = (ReactionGiversValue) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
