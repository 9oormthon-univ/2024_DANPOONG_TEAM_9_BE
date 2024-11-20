package com.goorm.LocC.review.repository;

import com.goorm.LocC.member.domain.Member;
import com.goorm.LocC.review.domain.Review;
import com.goorm.LocC.review.domain.ReviewLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewLikeRepository extends JpaRepository<ReviewLike, Long> {

    Optional<ReviewLike> findReviewLikeByMemberAndReview(Member member, Review review);
}
