package com.greenfoxacademy.fedex.models;

import com.greenfoxacademy.fedex.models.reactions.ReactionGivers;
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
public class Meme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String memePath;

    @OneToMany(mappedBy = "meme")
    private List<ReactionGivers> reactionGiversList;
}
