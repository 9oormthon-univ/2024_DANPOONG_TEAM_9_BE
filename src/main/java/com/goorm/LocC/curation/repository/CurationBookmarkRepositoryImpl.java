package com.goorm.LocC.curation.repository;

import com.goorm.LocC.curation.dto.CurationInfoDto;
import com.goorm.LocC.member.domain.Member;
import com.goorm.LocC.member.dto.condition.MemberCond;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.goorm.LocC.curation.domain.QCuration.curation;
import static com.goorm.LocC.curation.domain.QCurationBookmark.curationBookmark;

@RequiredArgsConstructor
public class CurationBookmarkRepositoryImpl implements CurationBookmarkRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<CurationInfoDto> findCurationInfoDtosByMember(MemberCond condition) {
        return queryFactory
                .select(
                        Projections.constructor(CurationInfoDto.class,
                                curation.curationId,
                                curation.title,
                                curation.subtitle,
                                curation.imageUrl
                        )
                )
                .from(curationBookmark)
                .join(curationBookmark.curation, curation)
                .on(bookmarkMemberEq(condition.getMember()))
                .orderBy(curationBookmark.createdAt.desc())
                .fetch();
    }

    private BooleanExpression bookmarkMemberEq(Member member) {
        return member != null ? curationBookmark.member.eq(member) : null;
    }
}
