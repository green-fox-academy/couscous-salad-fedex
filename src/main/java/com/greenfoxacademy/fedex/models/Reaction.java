package com.greenfoxacademy.fedex.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Reaction {
    @Id
    private ReactionType id;

    @OneToMany(mappedBy = "reaction")
    private List<ReactionGivers> reactionGiversList;

    public String getType() {
        return id.toString();
    }
}