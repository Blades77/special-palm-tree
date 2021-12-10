package first.iteration.endlesscreation.dto.create;

import java.util.List;

public class BookCreateDTO {

    private String bookTitle;
    private Integer pagesNumber;
    private Long ownerUser;
    private List<TagCreateDTO> tagCreateDTOList;

    public BookCreateDTO(String bookTitle, Integer pagesNumber, Long ownerUser, List<TagCreateDTO> tagCreateDTOList) {
        this.bookTitle = bookTitle;
        this.pagesNumber = pagesNumber;
        this.ownerUser = ownerUser;
        this.tagCreateDTOList = tagCreateDTOList;
    }

    public BookCreateDTO(){}

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

    public List<TagCreateDTO> getTagCreateDTOList() {
        return tagCreateDTOList;
    }

    public void setTagCreateDTOList(List<TagCreateDTO> tagCreateDTOList) {
        this.tagCreateDTOList = tagCreateDTOList;
    }
}
