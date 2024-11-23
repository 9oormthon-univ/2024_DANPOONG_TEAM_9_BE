package com.goorm.LocC.curation.repository;

import com.goorm.LocC.curation.dto.CurationInfoDto;
import com.goorm.LocC.member.dto.condition.MemberCond;

import java.util.List;

public interface CurationBookmarkRepositoryCustom {

    List<CurationInfoDto> findCurationInfoDtosByMember(MemberCond condition);

}
