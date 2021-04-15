package com.greenfoxacademy.fedex.models.reactions;

import com.greenfoxacademy.fedex.models.Meme;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@Entity
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

    @OneToMany (mappedBy = "reactionGivers", cascade = CascadeType.ALL)
    private List<ReactionGiversValue> reactionGiversValueList;

    public ReactionGivers() {
        id = new ReactionGiversKey();
        reactionGiversValueList = new ArrayList<>();
    }

    public ReactionGivers(Meme meme, Reaction reaction) {
        id = new ReactionGiversKey();
        this.meme = meme;
        this.reaction = reaction;
        reactionGiversValueList = new ArrayList<>();
    }

    public void addToReactionGiversValueList(ReactionGiversValue reactionGiversValue) {
        if (reactionGiversValueList.contains(reactionGiversValue)) {
            reactionGiversValueList.get(reactionGiversValueList.indexOf(reactionGiversValue)).setAmount(reactionGiversValue.getAmount());
        } else reactionGiversValueList.add(reactionGiversValue);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReactionGivers that = (ReactionGivers) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
