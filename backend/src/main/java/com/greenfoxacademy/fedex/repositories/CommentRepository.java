package com.greenfoxacademy.fedex.repositories;

import com.greenfoxacademy.fedex.models.Comment;
import com.greenfoxacademy.fedex.models.CommentKey;
import com.greenfoxacademy.fedex.models.Meme;
import com.greenfoxacademy.fedex.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CommentRepository extends CrudRepository<Comment, CommentKey> {
    Optional<Comment> findByUserAndMeme(User user, Meme meme);
}
