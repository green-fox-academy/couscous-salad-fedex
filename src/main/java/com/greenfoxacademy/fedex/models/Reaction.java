package com.greenfoxacademy.fedex.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Reaction {
    @Id
    @Enumerated(EnumType.ORDINAL)
    private ReactionType id;

    @OneToMany(mappedBy = "reaction")
    private List<ReactionGivers> reactionGiversList;

    public String getType() {
        return id.toString();
    }
}