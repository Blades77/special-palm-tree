package first.iteration.endlesscreation.mapper;

import first.iteration.endlesscreation.Model.BookEntity;
import first.iteration.endlesscreation.Model.BookPageEntity;
import first.iteration.endlesscreation.dto.BookPageDTO;
import first.iteration.endlesscreation.dto.Update.BookPageUpdateDTO;
import first.iteration.endlesscreation.dto.Update.BookUpdateDTO;
import first.iteration.endlesscreation.dto.create.BookPageCreateDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BookPageMapper {

    public static List<BookPageDTO> mapToBookPageDTOList(List<BookPageEntity> bookPageEntityList){
        List<BookPageDTO> bookPageDTOList = new ArrayList<>();
        if (bookPageEntityList.isEmpty() == false) {
            for (BookPageEntity bookPageEntity : bookPageEntityList) {
                bookPageDTOList.add(mapToBookPageDTO(bookPageEntity));
            }
        }
        return bookPageDTOList;
    }

    public static BookPageDTO mapToBookPageDTO(BookPageEntity bookPageEntity){
        BookPageDTO bookPageDTO = new BookPageDTO();
        bookPageDTO.setPageId(bookPageEntity.getPageId());
        bookPageDTO.setPageContent(bookPageEntity.getPageContent());
        bookPageDTO.setPageNumber(bookPageEntity.getPageNumber());
        bookPageDTO.setCreatedAt(bookPageEntity.getCreatedAt());
        bookPageDTO.setUpdatedAt(bookPageEntity.getUpdatedAt());
        bookPageDTO.setBookId(bookPageEntity.getBookEntity().getBookId());
        return bookPageDTO;
    }

    public static BookPageEntity mapCreateToBookPageEntity(BookPageCreateDTO bookPageCreateDTO, BookEntity bookEntity){
        BookPageEntity bookPageEntity = new BookPageEntity();
        bookPageEntity.setPageContent(bookPageCreateDTO.getPageContent());
        bookPageEntity.setPageNumber(bookPageCreateDTO.getPageNumber());
        bookPageEntity.setCreatedAt(LocalDateTime.now());
        bookPageEntity.setUpdatedAt(LocalDateTime.now());
        bookPageEntity.setBookEntity(bookEntity);
        return bookPageEntity;
    }

    public static BookPageEntity mapUpdateToBookPageEntity(BookPageUpdateDTO bookPageUpdateDTO, BookPageEntity bookPageEntity){
        bookPageEntity.setPageNumber(bookPageUpdateDTO.getPageNumber());
        bookPageEntity.setPageContent(bookPageUpdateDTO.getPageContent());
        return  bookPageEntity;
    }
}
