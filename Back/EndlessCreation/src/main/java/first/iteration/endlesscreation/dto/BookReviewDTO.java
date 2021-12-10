package first.iteration.endlesscreation.dto;

import java.time.LocalDateTime;

public class BookReviewDTO {

    private Long reviewId;
    private String reviewContent;
    private Double reviewRating;
    private Long bookId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public BookReviewDTO(Long reviewId, String reviewContent, Double reviewRating, Long bookId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.reviewId = reviewId;
        this.reviewContent = reviewContent;
        this.reviewRating = reviewRating;
        this.bookId = bookId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public BookReviewDTO(){}

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

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }
}
