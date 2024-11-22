package com.goorm.LocC.member.domain;

import com.goorm.LocC.global.common.entity.BaseEntity;
import com.goorm.LocC.store.domain.City;
import com.goorm.LocC.store.domain.Province;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    @Column(nullable = false, unique = true)
    private String handle;

    private String profileImageUrl = "";

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = USER;

    @Column(nullable = false, unique = true)
    private String email;

    @Setter
    @Enumerated(EnumType.STRING)
    private Province preferredProvince;

    @Setter
    @Enumerated(EnumType.STRING)
    private City preferredCity;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PreferredCategory> preferredCategories = new ArrayList<>();

    @Builder
    public Member(SocialType socialType, String socialId, String username, String profileImageUrl, String email) {
        this.socialType = socialType;
        this.socialId = socialId;
        this.handle = email.substring(0, email.indexOf("@")); // 임시 지정
        this.username = username;
        this.profileImageUrl = profileImageUrl != null ? profileImageUrl
            : "https://locc-bucket.s3.ap-northeast-2.amazonaws.com/default_profile/default_profile_2.png";
        this.email = email;
    }
}
