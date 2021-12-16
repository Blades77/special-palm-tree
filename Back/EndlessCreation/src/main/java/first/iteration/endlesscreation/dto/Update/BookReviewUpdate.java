package first.iteration.endlesscreation.dto.Update;

import first.iteration.endlesscreation.dto.BookReviewDTO;

import java.time.LocalDateTime;

public class BookReviewUpdate {
    private Long reviewId;
    private String reviewContent;
    private Double reviewRating;

    public BookReviewUpdate(Long reviewId, String reviewContent, Double reviewRating) {
        this.reviewId = reviewId;
        this.reviewContent = reviewContent;
        this.reviewRating = reviewRating;
    }

    public BookReviewUpdate(){}

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public Double getReviewRating() {
        return reviewRating;
    }

    public void setReviewRating(Double reviewRating) {
        this.reviewRating = reviewRating;
    }
}
