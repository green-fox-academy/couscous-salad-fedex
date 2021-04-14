package com.greenfoxacademy.fedex.models;

import com.greenfoxacademy.fedex.models.reactions.ReactionDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemeDTO {
    private String meme_path;
    private List<ReactionDTO> reaction_list;
}
