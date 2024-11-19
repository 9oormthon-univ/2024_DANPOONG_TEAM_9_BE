package com.goorm.LocC.store.repository;

import com.goorm.LocC.member.dto.MemberCond;
import com.goorm.LocC.store.dto.StoreInfoDto;

import java.util.List;

public interface StoreBookmarkRepositoryCustom {

    List<StoreInfoDto> searchStoresByMember(MemberCond condition);
}
