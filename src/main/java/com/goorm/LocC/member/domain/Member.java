package com.goorm.LocC.member.domain;

import com.goorm.LocC.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.goorm.LocC.member.domain.Role.USER;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SocialType socialType;

    @Column(nullable = false, unique = true)
    private String socialId;

    @Column(nullable = false)
    private String username;

    private String profileImageUrl = "";

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = USER;

    @Column(nullable = false, unique = true)
    private String email;

    @Builder
    public Member(SocialType socialType, String socialId, String username, String profileImageUrl, String email) {
        this.socialType = socialType;
        this.socialId = socialId;
        this.username = username;
        this.profileImageUrl = profileImageUrl;
        this.email = email;
    }
}
