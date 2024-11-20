package com.goorm.LocC.review.application;

import com.goorm.LocC.member.domain.Member;
import com.goorm.LocC.member.exception.MemberException;
import com.goorm.LocC.member.repository.MemberRepository;
import com.goorm.LocC.review.domain.Review;
import com.goorm.LocC.review.domain.ReviewLike;
import com.goorm.LocC.review.exception.ReviewException;
import com.goorm.LocC.review.repository.ReviewLikeRepository;
import com.goorm.LocC.review.repository.ReviewRepository;
import com.goorm.LocC.store.dto.ToggleStoreBookmarkRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.goorm.LocC.member.exception.MemberErrorCode.MEMBER_NOT_FOUND;
import static com.goorm.LocC.review.exception.ReviewErrorCode.REVIEW_NOT_FOUND;

@RequiredArgsConstructor
@Transactional
@Service
public class ReviewService {

    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewLikeRepository reviewLikeRepository;

    public ToggleStoreBookmarkRespDto toggleLike(Long storeId, String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException(MEMBER_NOT_FOUND));
        Review review = reviewRepository.findById(storeId)
                .orElseThrow(() -> new ReviewException(REVIEW_NOT_FOUND));
        Optional<ReviewLike> optionalBookmark = reviewLikeRepository.findReviewLikeByMemberAndReview(member, review);

        if (optionalBookmark.isPresent()) { // 이미 좋아요한 경우
            reviewLikeRepository.delete(optionalBookmark.get());

            return ToggleStoreBookmarkRespDto.builder()
                    .count(review.subLikeCount())
                    .isBookmarked(false)
                    .build();
        } else { // 좋아요하지 않은 경우
            reviewLikeRepository.save(
                    ReviewLike.builder()
                    .member(member)
                    .review(review)
                    .build());

            return ToggleStoreBookmarkRespDto.builder()
                    .count(review.addLikeCount())
                    .isBookmarked(true)
                    .build();
        }
    }
}
