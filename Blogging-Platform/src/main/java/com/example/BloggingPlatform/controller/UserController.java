package com.example.BloggingPlatform.controller;


import com.example.BloggingPlatform.model.*;
import com.example.BloggingPlatform.model.dto.SignInInput;
import com.example.BloggingPlatform.model.dto.SignUpOutput;
import com.example.BloggingPlatform.service.AuthenticationService;
import com.example.BloggingPlatform.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    AuthenticationService authenticationService;


    //sign up, sign in , sign out a particular blogger
    @PostMapping("user/signup")
    public SignUpOutput signUpBlogUser(@RequestBody User user)
    {

        return userService.signUpUser(user);
    }
    @PostMapping("user/signIn")
    public String signInBlogUser(@RequestBody @Valid SignInInput signInInput)
    {
        return userService.signInUser(signInInput);
    }
    @DeleteMapping("user/signOut")
    public String sigOutBlogUser(String email, String token)
    {
        if(authenticationService.authenticate(email,token)) {
            return userService.sigOutUser(email);
        }
        else {
            return "Sign out not allowed for non authenticated user.";
        }

    }
    //blog content
    //creating blog
    @PostMapping("blog")
    public String createBlog(@RequestBody Blog blog, @RequestParam String email, @RequestParam String token)
    {
        if(authenticationService.authenticate(email,token)) {
            return userService.createBlog(blog,email);
        }
        else {
            return "Not an Authenticated user activity!!!";
        }
    }
    @DeleteMapping("blog")
    public String removeBlog(@RequestParam Long blogId, @RequestParam String email, @RequestParam String token)
    {
        if(authenticationService.authenticate(email,token)) {
            return userService.removeBlog(blogId,email);
        }
        else {
            return "Not an Authenticated user activity!!!";
        }
    }
    @PostMapping("comment")
    public String addComment(@RequestBody Comment comment, @RequestParam String commenterEmail, @RequestParam String commenterToken)
    {
        if(authenticationService.authenticate(commenterEmail,commenterToken)) {
            return userService.addComment(comment,commenterEmail);
        }
        else {
            return "Not an Authenticated user activity!!!";
        }
    }
    @DeleteMapping("comment")
    public String removeBlogComment(@RequestParam Long commentId, @RequestParam String email, @RequestParam String token)
    {
        if(authenticationService.authenticate(email,token)) {
            return userService.removeBlogComment(commentId,email);
        }
        else {
            return "Not an Authenticated user activity!!!";
        }
    }

    @PostMapping("follow")
    public String followUser(@RequestBody Follow follow, @RequestParam String followerEmail, @RequestParam String followerToken)
    {
        if(authenticationService.authenticate(followerEmail,followerToken)) {
            return userService.followUser(follow,followerEmail);
        }
        else {
            return "Not an Authenticated user activity!!!";
        }
    }




}
