package com.example.BloggingPlatform.repository;

import com.example.BloggingPlatform.model.Blog;
import com.example.BloggingPlatform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBlogRepo extends JpaRepository<Blog,Long> {
}
