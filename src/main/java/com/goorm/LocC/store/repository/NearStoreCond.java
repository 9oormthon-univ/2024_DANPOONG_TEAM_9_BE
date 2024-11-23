package com.goorm.LocC.store.repository;

import com.goorm.LocC.store.domain.Province;
import com.goorm.LocC.store.domain.Store;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class NearStoreCond {

    private Province province;
    private Store excludeStore;
    private int limit;

    @Builder
    public NearStoreCond(Province province, Store excludeStore, int limit) {
        this.province = province;
        this.excludeStore = excludeStore;
        this.limit = limit;
    }
}
