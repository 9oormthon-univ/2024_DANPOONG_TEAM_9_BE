package com.goorm.LocC.member.dto.request;

import com.goorm.LocC.store.domain.Category;
import com.goorm.LocC.store.domain.Province;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PreferenceReqDto {
    private List<Category> categories; // 관심 카테고리
    private Province province; // 선호 지역
}
