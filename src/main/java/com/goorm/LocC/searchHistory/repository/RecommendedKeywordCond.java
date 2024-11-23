package com.goorm.LocC.searchHistory.repository;

import com.goorm.LocC.store.domain.Category;
import com.goorm.LocC.store.domain.Province;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RecommendedKeywordCond {

    private Province province;
    private List<Category> categories;
    private int limit;

    public RecommendedKeywordCond(Province province, List<Category> categories, int limit) {
        this.province = province;
        this.categories = categories;
        this.limit = limit;
    }
}
