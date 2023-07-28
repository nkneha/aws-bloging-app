package com.example.BloggingPlatform.service;

import com.example.BloggingPlatform.model.Follow;
import com.example.BloggingPlatform.model.User;
import com.example.BloggingPlatform.repository.IFollowRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowService {
    @Autowired
    IFollowRepo followRepo;

    public boolean isFollowAllowed(User targetUser, User follower) {
        List<Follow> followList =  followRepo.findByCurrentUserAndCurrentUserFollower(targetUser,follower);

        return followList!=null && followList.isEmpty() && !targetUser.equals(follower);
    }

    public void startFollowing(Follow follow, User follower) {
        follow.setCurrentUserFollower(follower);
        followRepo.save(follow);
    }
}
