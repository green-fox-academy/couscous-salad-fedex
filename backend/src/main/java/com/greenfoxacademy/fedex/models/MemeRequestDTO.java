package com.greenfoxacademy.fedex.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemeRequestDTO {
  @JsonProperty(value = "meme_path")
  private String memePath;
}
