package com.goorm.LocC.searchHistory.dto.condition;

import com.goorm.LocC.member.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RecentKeywordCond {

    private Member member;
    private int limit;

    public RecentKeywordCond(Member member, int limit) {
        this.member = member;
        this.limit = limit;
    }
}
