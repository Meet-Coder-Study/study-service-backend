package com.study.service.blog;

import java.net.URL;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.study.service.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "blog_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = true)
    private User user;

    @Column
    private URL link;

    @Column
    private URL rssLink;

    @Column
    private String title;

    @Builder
    public Blog(final Long id, final User user, final URL link, final URL rssLink, final String title) {
        this.id = id;
        this.user = user;
        this.link = link;
        this.rssLink = rssLink;
        this.title = title;
    }
}
