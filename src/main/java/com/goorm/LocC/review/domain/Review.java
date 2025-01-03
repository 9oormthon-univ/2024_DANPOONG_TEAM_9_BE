package com.goorm.LocC.review.domain;

import com.goorm.LocC.global.common.entity.BaseEntity;
import com.goorm.LocC.member.domain.Member;
import com.goorm.LocC.store.domain.Store;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    private int rating = 0;

    private String content;

    @Column(length = 2048)
    private String imageUrl;

    @Column(nullable = false)
    private int likeCount = 0;

    @Column(nullable = false)
    private LocalDate visitedDate;

    @OneToMany(mappedBy = "review")
    private List<ReviewImage> reviewImage;

    public int addLikeCount() {
        likeCount += 1;
        return likeCount;
    }

    public int subLikeCount() {
        likeCount -= 1;
        return likeCount;
    }
}