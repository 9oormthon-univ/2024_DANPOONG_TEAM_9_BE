//package com.goorm.LocC.member.domain;
//
//import com.goorm.LocC.global.common.entity.BaseEntity;
//import com.goorm.LocC.store.domain.City;
//import com.goorm.LocC.store.domain.Province;
//import jakarta.persistence.*;
//import lombok.AccessLevel;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@Entity
//public class PreferredRegion extends BaseEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long preferredRegionId;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "member_id", nullable = false)
//    private Member member;
//
//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private Province province;
//
//    @Enumerated(EnumType.STRING)
//    private City city;
//}