package com.goorm.LocC.member.repository;

import com.goorm.LocC.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findBySocialId(String socialId);
    Optional<Member> findByEmail(String email);
}
