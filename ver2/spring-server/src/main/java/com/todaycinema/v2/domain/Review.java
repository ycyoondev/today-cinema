package com.todaycinema.v2.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Review {
    @Id @GeneratedValue
    @Column(name = "review_id")
    private Long id;

    @Column(length = 1000)
    private String content;

    @Column(name = "is_spoiler_self")
    private Boolean isSpoilerSelf;

    @Column(name = "is_spoiler_checked")
    private Boolean isSpoilerChecked;

    @Column(name = "user_rating")
    private Integer userRating;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ReviewLikeUser> reviewLikeUsers = new ArrayList<>();

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ReviewSpoilerCheckUser> reviewSpoilerCheckUsers = new ArrayList<>();
}
