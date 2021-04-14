package com.greenfoxacademy.fedex.repositories;

import com.greenfoxacademy.fedex.models.Meme;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MemeRepository extends CrudRepository<Meme, Long> {
  Optional<Meme> findMemeByMemePath(String memePath);
}
