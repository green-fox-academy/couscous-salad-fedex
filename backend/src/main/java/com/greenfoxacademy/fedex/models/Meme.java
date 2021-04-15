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

  public Meme(String memePath,
              List<ReactionGivers> reactionGiversList) {
    this.memePath = memePath;
    this.reactionGiversList = reactionGiversList;
  }
}
