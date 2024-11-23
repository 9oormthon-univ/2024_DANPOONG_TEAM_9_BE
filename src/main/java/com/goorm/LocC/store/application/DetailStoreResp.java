package com.goorm.LocC.store.application;

import com.goorm.LocC.store.domain.BusinessStatus;
import com.goorm.LocC.store.domain.Category;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class DetailStoreResp {

    private String storeName;
    private Category category;
    private String phone;
    private BusinessStatus status;
    private String homepage;
//    private float rating,
    private int reviewCount;
    private String content;


}
