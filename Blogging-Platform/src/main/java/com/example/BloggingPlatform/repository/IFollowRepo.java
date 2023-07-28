package com.example.BloggingPlatform.repository;

import com.example.BloggingPlatform.model.Follow;
import com.example.BloggingPlatform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFollowRepo extends JpaRepository<Follow,Long> {
    List<Follow> findByCurrentUserAndCurrentUserFollower(User targetUser, User follower);
}
