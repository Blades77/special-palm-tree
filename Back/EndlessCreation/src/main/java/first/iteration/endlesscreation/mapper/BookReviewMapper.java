package first.iteration.endlesscreation.mapper;


import first.iteration.endlesscreation.Model.BookEntity;
import first.iteration.endlesscreation.Model.BookReviewEntity;
import first.iteration.endlesscreation.dto.BookReviewDTO;
import first.iteration.endlesscreation.dto.create.BookCreateDTO;
import first.iteration.endlesscreation.dto.create.BookReviewCreateDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BookReviewMapper {
    public static List<BookReviewDTO> mapToBookReviewDTOLIst(List<BookReviewEntity> bookReviewEntityList){
        List<BookReviewDTO> bookReviewDTOList = new ArrayList<>();
        for(BookReviewEntity bookReviewEntity : bookReviewEntityList){
            BookReviewDTO bookReviewDTO = new BookReviewDTO();
            bookReviewDTO.setBookId(bookReviewEntity.getBookEntity().getBookId());
            bookReviewDTO.setReviewId(bookReviewEntity.getReviewId());
            bookReviewDTO.setReviewContent(bookReviewEntity.getReviewContent());
            bookReviewDTO.setReviewRating(bookReviewEntity.getReviewRating());
            bookReviewDTOList.add(bookReviewDTO);
        }
        return bookReviewDTOList;
    }

    public static BookReviewEntity mapCreateToBookReviewEntity(BookReviewCreateDTO bookReviewCreateDTO, BookEntity bookEntity){
        BookReviewEntity bookReviewEntity = new BookReviewEntity();
        bookReviewEntity.setReviewContent(bookReviewCreateDTO.getReviewContent());
        bookReviewEntity.setReviewRating(bookReviewCreateDTO.getReviewRating());
        bookReviewEntity.setCreatedAt(LocalDateTime.now());
        bookReviewEntity.setUpdatedAt(LocalDateTime.now());
        bookReviewEntity.setBookEntity(bookEntity);
        return  bookReviewEntity;
    }
}
