package com.goorm.LocC.member.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PreferenceRequest {
    private List<String> categories; // 관심 카테고리
    private String province; // 선호 지역
}
