package com.example.BloggingPlatform.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "BlogComment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(nullable = false)
    private String commentBody;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY) // hide this in json but not in database table column
    private LocalDateTime commentCreationTimeStamp;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_comment_blog_id")
    private Blog blogPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_commenter_id")
    private User commenter;

}
