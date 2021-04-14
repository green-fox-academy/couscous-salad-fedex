package com.greenfoxacademy.fedex.models.reactions;

import com.greenfoxacademy.fedex.models.Meme;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
}
