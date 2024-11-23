package com.goorm.LocC.review.application;

import com.goorm.LocC.member.domain.Member;
import com.goorm.LocC.member.exception.MemberException;
import com.goorm.LocC.member.repository.MemberRepository;
import com.goorm.LocC.review.domain.Review;
import com.goorm.LocC.review.domain.ReviewLike;
import com.goorm.LocC.review.dto.ReviewDetailResponseDto;
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

    // 추가된 개별 리뷰 상세 조회 메서드
    public ReviewDetailResponseDto getReviewDetail(Long reviewId) {
        Review review = reviewRepository.findReviewDetailById(reviewId)
                .orElseThrow(() -> new ReviewException(REVIEW_NOT_FOUND));

        // DTO로 변환하여 반환
        return new ReviewDetailResponseDto(
                review.getReviewId(),
                review.getStore().getName(),
                review.getContent(),
                review.getImageUrl(),
                false, // 좋아요 여부 - 구현 필요
                review.getLikeCount(),
                review.getVisitedDate(),
                review.getStore().getKakaomapUrl()
        );
    }
}
