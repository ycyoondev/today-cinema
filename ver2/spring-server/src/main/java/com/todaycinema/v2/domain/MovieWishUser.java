package com.todaycinema.v2.domain;

import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MovieWishUser {

    @Id
    @GeneratedValue
    @Column(name = "movie_wish_user_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;


    @Builder
    public MovieWishUser(User user, Movie movie) {
        this.user = user;
        this.movie = movie;
    }
}
