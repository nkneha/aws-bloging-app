package com.example.BloggingPlatform.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long followId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_actual_user")
    User currentUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_follower_of_actual_user")
    User currentUserFollower;

}
