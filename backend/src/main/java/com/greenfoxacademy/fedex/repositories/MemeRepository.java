package com.greenfoxacademy.fedex.repositories;

import com.greenfoxacademy.fedex.models.Meme;
import org.springframework.data.repository.CrudRepository;

public interface MemeRepository extends CrudRepository<Meme, Long> {
}
