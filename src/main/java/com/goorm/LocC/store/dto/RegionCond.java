package com.goorm.LocC.store.dto;

import com.goorm.LocC.store.domain.City;
import com.goorm.LocC.store.domain.Province;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Getter
public class RegionCond {

    private Province province;
    private City city;
    private int limit;

    @Builder
    public RegionCond(Province province, City city, int limit) {
        this.province = province;
        this.city = city;
        this.limit = limit;
    }
}
