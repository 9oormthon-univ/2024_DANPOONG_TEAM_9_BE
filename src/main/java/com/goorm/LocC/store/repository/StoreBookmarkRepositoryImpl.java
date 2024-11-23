package com.goorm.LocC.store.repository;

import com.goorm.LocC.member.domain.Member;
import com.goorm.LocC.member.dto.condition.MemberCond;
import com.goorm.LocC.store.dto.SimpleStoreInfoDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import static com.goorm.LocC.store.domain.QBusinessHour.businessHour;
import static com.goorm.LocC.store.domain.QStore.store;
import static com.goorm.LocC.store.domain.QStoreBookmark.storeBookmark;

@RequiredArgsConstructor
public class StoreBookmarkRepositoryImpl implements StoreBookmarkRepositoryCustom {

    private final JPAQueryFactory queryFactory;


    @Override
    public List<SimpleStoreInfoDto> findSimpleStoreInfoDtosByMember(MemberCond condition) {
        DayOfWeek today = LocalDate.now().getDayOfWeek();

        return queryFactory
                .select(
                        Projections.constructor(SimpleStoreInfoDto.class,
                                store.storeId,
                                store.name,
                                store.category,
                                store.province,
                                store.city,
                                store.thumbnailImageUrl.as("imageUrl"),
                                store.rating,
                                store.reviewCount,
                                businessHour.openTime,
                                businessHour.closeTime,
                                businessHour.isHoliday
                        )
                )
                .from(storeBookmark)
                .join(storeBookmark.store, store).on(bookmarkMemberEq(condition.getMember()))
                .join(businessHour).on(
                        businessHour.store.eq(store),
                        businessHourDayEq(today))
                .orderBy(storeBookmark.createdAt.desc())
                .fetch();
    }

    private BooleanExpression bookmarkMemberEq(Member member) {
        return member != null ? storeBookmark.member.eq(member) : null;
    }

    private BooleanExpression businessHourDayEq(DayOfWeek today) {
        return today != null ? businessHour.dayOfWeek.eq(today) : null;
    }
}
