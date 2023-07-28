package com.example.BloggingPlatform.service;

import com.example.BloggingPlatform.model.*;
import com.example.BloggingPlatform.model.dto.SignInInput;
import com.example.BloggingPlatform.model.dto.SignUpOutput;
import com.example.BloggingPlatform.repository.IUserRepo;
import com.example.BloggingPlatform.service.emailUtility.EmailHandler;
import com.example.BloggingPlatform.service.hashingUtility.PasswordEncrypter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    IUserRepo userRepo;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    BlogService blogService;

    @Autowired
    CommentService commentService;



    @Autowired
    FollowService followService;
    public SignUpOutput signUpUser(User user) {

            boolean signUpStatus = true;
            String signUpStatusMessage = null;

            String newEmail = user.getUserEmail();

            if(newEmail == null)
            {
                signUpStatusMessage = "Invalid email";
                signUpStatus = false;
                return new SignUpOutput(signUpStatus,signUpStatusMessage);
            }

            //check if this user email already exists ??
            User existingUser = userRepo.findFirstByUserEmail(newEmail);

            if(existingUser != null)
            {
                signUpStatusMessage = "Email already registered!!!";
                signUpStatus = false;
                return new SignUpOutput(signUpStatus,signUpStatusMessage);
            }

            //hash the password: encrypt the password
            try {
                String encryptedPassword = PasswordEncrypter.encryptPassword(user.getUserPassword());

                //saveAppointment the user with the new encrypted password

                user.setUserPassword(encryptedPassword);
                userRepo.save(user);

                return new SignUpOutput(signUpStatus, "User registered successfully!!!");
            }
            catch(Exception e)
            {
                signUpStatusMessage = "Internal error occurred during sign up";
                signUpStatus = false;
                return new SignUpOutput(signUpStatus,signUpStatusMessage);
            }
    }

    public String signInUser(SignInInput signInInput) {
        String signInStatusMessage = null;

        String signInEmail = signInInput.getEmail();

        if(signInEmail == null)
        {
            signInStatusMessage = "Invalid email";
            return signInStatusMessage;


        }

        //check if this user email already exists ??
        User existingUser = userRepo.findFirstByUserEmail(signInEmail);

        if(existingUser == null)
        {
            signInStatusMessage = "Email not registered!!!";
            return signInStatusMessage;

        }

        //match passwords :

        //hash the password: encrypt the password
        try {
            String encryptedPassword = PasswordEncrypter.encryptPassword(signInInput.getPassword());
            if(existingUser.getUserPassword().equals(encryptedPassword))
            {
                //session should be created since password matched and user id is valid
                AuthenticationToken authToken  = new AuthenticationToken(existingUser);
                authenticationService.saveAuthToken(authToken);

                EmailHandler.sendEmail("neha.kumari1vns@gmail.com","email testing",authToken.getTokenValue());
                return "Token sent to your email";
            }
            else {
                signInStatusMessage = "Invalid credentials!!!";
                return signInStatusMessage;
            }
        }
        catch(Exception e)
        {
            signInStatusMessage = "Internal error occurred during sign in";
            return signInStatusMessage;
        }

    }

    public String sigOutUser(String email) {
        User user = userRepo.findFirstByUserEmail(email);
        AuthenticationToken token = authenticationService.findFirstByUser(user);
        authenticationService.removeToken(token);
        return "User Signed out successfully";
    }

    public String createBlog(Blog blog, String email) {
        User blogOwner = userRepo.findFirstByUserEmail(email);
        blog.setBlogOwner(blogOwner);
        return blogService.createBlog(blog);
    }

    public String removeBlog(Long blogId, String email) {
        User user = userRepo.findFirstByUserEmail(email);
        return blogService.removeBlog(blogId,user);
    }

    public String addComment(Comment comment, String commenterEmail) {
        boolean postValid = blogService.validateBlog(comment.getBlogPost());
        if(postValid) {
            User commenter = userRepo.findFirstByUserEmail(commenterEmail);
            comment.setCommenter(commenter);
            return commentService.addComment(comment);
        }
        else {
            return "Cannot comment on Invalid Post!!";
        }
    }
    private boolean authorizeCommentRemover(String email, Comment comment) {
        String  commentOwnerEmail = comment.getCommenter().getUserEmail();
        String  blogOwnerEmail  = comment.getBlogPost().getBlogOwner().getUserEmail();

        return blogOwnerEmail.equals(email) || commentOwnerEmail.equals(email);
    }

    public String removeBlogComment(Long commentId, String email) {
        Comment comment  = commentService.findComment(commentId);
        if(comment!=null)
        {
            if(authorizeCommentRemover(email,comment))
            {
                commentService.removeComment(comment);
                return "comment deleted successfully";
            }
            else
            {
                return "Unauthorized delete detected...Not allowed!!!!";
            }

        }
        else
        {
            return "Invalid Comment";
        }

    }





    public String followUser(Follow follow, String followerEmail) {
        User followTargetUser = userRepo.findById(follow.getCurrentUser().getUserId()).orElse(null);

        User follower = userRepo.findFirstByUserEmail(followerEmail);

        if(followTargetUser!=null)
        {
            if(followService.isFollowAllowed(followTargetUser,follower))
            {
                followService.startFollowing(follow,follower);
                return follower.getUserHandle()  + " is now following " + followTargetUser.getUserHandle();
            }
            else {
                return follower.getUserHandle()  + " already follows " + followTargetUser.getUserHandle();
            }
        }
        else {
            return "User to be followed is Invalid!!!";
        }


    }
}
