package com.example.BloggingPlatform.repository;

import com.example.BloggingPlatform.model.Comment;
import com.example.BloggingPlatform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICommentRepo extends JpaRepository<Comment,Long> {
}
