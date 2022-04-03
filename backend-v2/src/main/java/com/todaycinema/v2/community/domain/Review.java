package com.todaycinema.v2.community.domain;

import com.todaycinema.v2.accounts.domain.User;
import com.todaycinema.v2.movies.domain.Movie;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Review {
    @Id
    @Column(name = "review_id")
    private Long id;

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
}
