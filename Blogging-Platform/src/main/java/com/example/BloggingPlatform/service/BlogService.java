package com.example.BloggingPlatform.service;

import com.example.BloggingPlatform.model.Blog;
import com.example.BloggingPlatform.model.User;
import com.example.BloggingPlatform.repository.IBlogRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BlogService {
    @Autowired
    IBlogRepo blogRepo;
    public String createBlog(Blog blog) {
        blog.setCreatedTimeStamp(LocalDateTime.now());
        blogRepo.save(blog);
        return "Blog uploaded!!!!";
    }

    public String removeBlog(Long blogId, User user) {
        Blog blog  = blogRepo.findById(blogId).orElse(null);
        if(blog != null && blog.getBlogOwner().equals(user))
        {
            blogRepo.deleteById(blogId);
            return "Removed successfully";
        }
        else if (blog == null)
        {
            return "Post to be deleted does not exist";
        }
        else{
            return "Un-Authorized delete detected....Not allowed";
        }
    }
    public boolean validateBlog(Blog blogPost) {
        return (blogPost!=null && blogRepo.existsById(blogPost.getBlogId()));
    }

    public Blog getBlogById(Long id) {
        return blogRepo.findById(id).orElse(null);
    }
}
