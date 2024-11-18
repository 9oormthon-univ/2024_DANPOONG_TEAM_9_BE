package com.goorm.LocC.member.repository;

import com.goorm.LocC.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
