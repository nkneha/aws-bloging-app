package com.example.BloggingPlatform.model;

import com.example.BloggingPlatform.model.enums.BlogType;
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
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long blogId;
    private String blogContent;
    private String blogCaption;
    private String blogLocation;
    private LocalDateTime createdTimeStamp;
    private BlogType blogType;

    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JoinColumn(name = "fk_blog_user_id")
    private User blogOwner;


}
