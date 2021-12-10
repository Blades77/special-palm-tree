package first.iteration.endlesscreation.dto.create;


public class BookPageCreateDTO {

    private Long bookId;
    private Integer pageNumber;
    private String pageContent;

    public BookPageCreateDTO(Long bookId, Integer pageNumber, String pageContent) {
        this.bookId = bookId;
        this.pageNumber = pageNumber;
        this.pageContent = pageContent;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
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
}
