package com.study.service.user;

import com.study.service.common.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
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

    @Column
    @Enumerated(EnumType.STRING)
    private DeveloperType developerType;

    @Builder
    public User(final String email, final String name, final String principal, final Role role,
                final SocialType socialType, final DeveloperType developerType) {
        this.email = email;
        this.name = name;
        this.principal = principal;
        this.role = role;
        this.socialType = socialType;
        this.developerType = developerType;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", principal='" + principal + '\'' +
                ", role=" + role +
                ", socialType=" + socialType +
                '}';
    }


}
