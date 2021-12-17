package first.iteration.endlesscreation.dto.Update;

public class BookReviewUpdateDTO {
    private Long reviewId;
    private String reviewContent;
    private Double reviewRating;

    public BookReviewUpdateDTO(Long reviewId, String reviewContent, Double reviewRating) {
        this.reviewId = reviewId;
        this.reviewContent = reviewContent;
        this.reviewRating = reviewRating;
    }

    public BookReviewUpdateDTO(){}

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
