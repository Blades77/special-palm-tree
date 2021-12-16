package first.iteration.endlesscreation.mapper;

import first.iteration.endlesscreation.Model.BookEntity;
import first.iteration.endlesscreation.dto.BookDTO;
import first.iteration.endlesscreation.dto.TagDTO;
import first.iteration.endlesscreation.dto.create.BookCreateDTO;

import java.time.LocalDateTime;
import java.util.List;

public class BookMapper {

    public static BookDTO mapToBookDTO(BookEntity bookEntity, List<TagDTO> tagDTOList){
        BookDTO bookDTO = new BookDTO();
        bookDTO.setBookId(bookEntity.getBookId());
        bookDTO.setBookTitle(bookEntity.getBookTitle());
        bookDTO.setCreatedAt(bookEntity.getCreatedAt());
        bookDTO.setUpdatedAt(bookEntity.getUpdatedAt());
        bookDTO.setOwnerUser(bookEntity.getOwnerUser());
        bookDTO.setPagesNumber(bookEntity.getPagesNumber());
        bookDTO.setTagDTOList(tagDTOList);
        return bookDTO;
    }

    public static BookEntity mapCreateToBookEntity(BookCreateDTO bookCreateDTO){
        BookEntity bookEntity = new BookEntity();
        bookEntity.setBookTitle(bookCreateDTO.getBookTitle());
        bookEntity.setCreatedAt(LocalDateTime.now());
        bookEntity.setUpdatedAt(LocalDateTime.now());
        bookEntity.setOwnerUser(bookCreateDTO.getOwnerUser());
        bookEntity.setPagesNumber(bookCreateDTO.getPagesNumber());
        return bookEntity;
    }
}
