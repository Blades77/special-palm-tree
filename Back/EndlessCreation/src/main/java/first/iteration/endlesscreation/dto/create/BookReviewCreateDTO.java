package first.iteration.endlesscreation.dto.create;

public class BookReviewCreateDTO {

    private String reviewContent;
    private Double reviewRating;
    private Long bookId;

    public BookReviewCreateDTO(String reviewContent, Double reviewRating, Long bookId) {
        this.reviewContent = reviewContent;
        this.reviewRating = reviewRating;
        this.bookId = bookId;
    }

    private BookReviewCreateDTO(){}

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
