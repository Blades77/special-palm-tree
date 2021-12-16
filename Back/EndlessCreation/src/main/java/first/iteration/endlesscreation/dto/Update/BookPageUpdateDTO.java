package first.iteration.endlesscreation.dto.Update;

import java.time.LocalDateTime;

public class BookPageUpdateDTO {
    private Long pageId;
    private Integer pageNumber;
    private String pageContent;

    public BookPageUpdateDTO(Long pageId, Integer pageNumber, String pageContent) {
        this.pageId = pageId;
        this.pageNumber = pageNumber;
        this.pageContent = pageContent;
    }

    public Long getPageId() {
        return pageId;
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
}
