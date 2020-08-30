package com.study.service.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.study.service.common.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String email;

    @Column
    private String name;

    @Column
    private String principal;

    @Column
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column
    @Enumerated(EnumType.STRING)
    private SocialType socialType;

<<<<<<< HEAD
    @Builder
    public User(final String email, final String name, final String principal, final Role role,
            final SocialType socialType) {
=======
    public User(final String email, final String name, final String principal, final Role role, final SocialType socialType) {
>>>>>>> 76a404329153a9fd584260d05ce5c8c4c4b93fae
        this.email = email;
        this.name = name;
        this.principal = principal;
        this.role = role;
        this.socialType = socialType;
    }
}
