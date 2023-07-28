# aws-bloging-app
Created a Blogging Application with the help of Spring Boot. Here I have used the basic Java concept to create the business logic. for creating the application I have used Spring MVC(Model View Controller) architecture. The Application performs multiple tasks like creating a list of Users and in the list, we can perform CRUD operations like creating the list of Users, reading the list of Users, updating the list based on the User id, and deleting a particular User from the list.
A user can create blog,update that blog,and delete that blog and also a user can check how many followers they have.

## Languages and Framework
java and spring boot.

## Controller
A controller is a package in which we have a userController class that deals with all the APIs.

## Services
A Service is a package in which we have written all the business logic for creating the User Management Application.

## Repository
For storing the data we have used ArrayList. Here we have created an ArrayList which will store all the data.

## Data Flow
 1. Controller (UserController.java)

```bash
  signUpBlogUser()
  signInBlogUser()
  sigOutBlogUser()
  createBlog()
  removeBlog()
  addComment()
  removeBlogComment()
  followUser()
```
2. Service  (UserService.java)

```bash
 signUpBlogUser()
  signInBlogUser()
  sigOutBlogUser()
  createBlog()
  removeBlog()
  addComment()
  removeBlogComment()
  followUser()
```
3. model
   Blog
   User
   Follow
   Comment  

```bash
 
```
4. Repository
   
```bash
 getUsers()
 delete()
```



## Data Structure Used

```bash
 MySql
```

## Summary
Our blogging app is a feature-rich platform that empowers users to create, publish, and share captivating blog posts.
