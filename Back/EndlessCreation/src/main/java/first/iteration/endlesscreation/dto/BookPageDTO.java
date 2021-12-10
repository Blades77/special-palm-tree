package first.iteration.endlesscreation.dto;

import java.time.LocalDateTime;

public class BookPageDTO {

    private Long pageId;
    private Integer pageNumber;
    private String pageContent;
    private Long bookId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public BookPageDTO(Long pageId, Integer pageNumber, String pageContent, Long bookId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.pageId = pageId;
        this.pageNumber = pageNumber;
        this.pageContent = pageContent;
        this.bookId = bookId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public  BookPageDTO(){}

    public Long getPageId() {
        return pageId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setPageId(Long pageId) {
        this.pageId = pageId;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getPageContent() {
        return pageContent;
    }

    public void setPageContent(String pageContent) {
        this.pageContent = pageContent;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }
}
