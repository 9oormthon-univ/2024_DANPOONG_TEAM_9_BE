package com.goorm.LocC.store.domain;

import com.goorm.LocC.curation.domain.Curation;
import com.goorm.LocC.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long storeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curation_id")
    private Curation curation;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Province province;

    @Enumerated(EnumType.STRING)
    private City city;

    private String homepageUrl;

    @Column(length = 2048)
    private String thumbnailImageUrl;

    private float rating = 0;

    @Column(nullable = false)
    private int reviewCount = 0;

    @Column(nullable = false)
    private int dibsCount = 0;

    private String content;

    private String kakaomapUrl;

    // 아래 필드는 새로 추가한 부분입니다.
    private LocalTime openTime;
    private LocalTime closeTime; // 영업 종료 시간
    private Boolean isBookmarked = false; // 북마크 여부
    private Float distance = 0.0f; // 거리 정보

    public String getKakaomapUrl() {
        return kakaomapUrl;
    }

    public int addDibsCount() {
        dibsCount += 1;
        return dibsCount;
    }

    public int subDibsCount() {
        dibsCount -= 1;
        return dibsCount;
    }
}