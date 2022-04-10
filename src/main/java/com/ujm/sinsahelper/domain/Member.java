package com.ujm.sinsahelper.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_seq")
    private Long memberSeq;

    private String email;

    private String password;

    private String userEmail;

    private String userName;

    private String gender;


    @Enumerated(value = EnumType.STRING)
    private AuthRole authRole;

    @Builder
    public Member(String email, String password, String userEmail, String userName, String gender, AuthRole authRole) {
        this.email = email;
        this.password = password;
        this.userEmail = userEmail;
        this.userName = userName;
        this.gender = gender;
        this.authRole = authRole;
    }
}
