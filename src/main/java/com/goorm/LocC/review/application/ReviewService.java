package com.goorm.LocC.review.application;

import com.goorm.LocC.member.domain.Member;
import com.goorm.LocC.member.exception.MemberException;
import com.goorm.LocC.member.repository.MemberRepository;
import com.goorm.LocC.review.domain.Review;
import com.goorm.LocC.review.domain.ReviewLike;
import com.goorm.LocC.review.dto.response.DetailReviewRespDto;
import com.goorm.LocC.review.exception.ReviewException;
import com.goorm.LocC.review.repository.ReviewLikeRepository;
import com.goorm.LocC.review.repository.ReviewRepository;
import com.goorm.LocC.store.dto.response.ToggleStoreBookmarkRespDto;
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

    public ToggleStoreBookmarkRespDto toggleLike(Long reviewId, String email) {
        Member member = findMemberByEmail(email);
        Review review = findReviewById(reviewRepository.findById(reviewId));
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
    @Transactional(readOnly = true)
    public DetailReviewRespDto getReviewDetail(Long reviewId, String email) {
        Review review = findReviewById(reviewRepository.findReviewDetailById(reviewId));

        Member member = findMemberByEmail(email);
        boolean isLiked = reviewLikeRepository.existsByMemberAndReview(member, review);

        // DTO로 변환하여 반환
        return new DetailReviewRespDto(
                review.getReviewId(),
                review.getStore().getName(),
                review.getContent(),
                review.getImageUrl(),
                isLiked,
                review.getLikeCount(),
                review.getVisitedDate(),
                review.getStore().getKakaomapUrl()
        );
    }

    private Review findReviewById(Optional<Review> reviewRepository) {
        return reviewRepository
                .orElseThrow(() -> new ReviewException(REVIEW_NOT_FOUND));
    }

    private Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException(MEMBER_NOT_FOUND));
    }
}
