package com.goorm.LocC.store.repository;

import com.goorm.LocC.member.dto.condition.MemberCond;
import com.goorm.LocC.store.dto.SimpleStoreInfoDto;

import java.util.List;

public interface StoreBookmarkRepositoryCustom {

    List<SimpleStoreInfoDto> findSimpleStoreInfoDtosByMember(MemberCond condition);
}
