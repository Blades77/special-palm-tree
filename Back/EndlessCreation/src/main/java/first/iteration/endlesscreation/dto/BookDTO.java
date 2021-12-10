package first.iteration.endlesscreation.dto;

import java.time.LocalDateTime;
import java.util.List;

public class BookDTO {

    private Long bookId;
    private String bookTitle;
    private Integer pagesNumber;
    private Long ownerUser;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<TagDTO> tagDTOList;

    public BookDTO(Long bookId, String bookTitle, Integer pagesNumber, Long ownerUser, LocalDateTime createdAt, LocalDateTime updatedAt, List<TagDTO> tagDTOList) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.pagesNumber = pagesNumber;
        this.ownerUser = ownerUser;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.tagDTOList = tagDTOList;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public Integer getPagesNumber() {
        return pagesNumber;
    }

    public void setPagesNumber(Integer pagesNumber) {
        this.pagesNumber = pagesNumber;
    }

    public Long getOwnerUser() {
        return ownerUser;
    }

    public void setOwnerUser(Long ownerUser) {
        this.ownerUser = ownerUser;
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

    public List<TagDTO> getTagDTOList() {
        return tagDTOList;
    }

    public void setTagDTOList(List<TagDTO> tagDTOList) {
        this.tagDTOList = tagDTOList;
    }
}
