package first.iteration.endlesscreation.dto.Update;

import first.iteration.endlesscreation.dto.TagDTO;

import java.time.LocalDateTime;
import java.util.List;

public class BookUpdateDTO {

    private Long bookId;
    private String bookTitle;

    public BookUpdateDTO(Long bookId, String bookTitle) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
    }

    public BookUpdateDTO(){}

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
}
