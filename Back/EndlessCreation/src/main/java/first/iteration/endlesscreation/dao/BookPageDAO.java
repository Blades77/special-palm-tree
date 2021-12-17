package first.iteration.endlesscreation.dao;

import first.iteration.endlesscreation.Model.BookEntity;
import first.iteration.endlesscreation.Model.BookPageEntity;
import first.iteration.endlesscreation.exception.ResourceNotFoundException;
import first.iteration.endlesscreation.repository.BookPageRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookPageDAO {

    BookPageRepository bookPageRepository;

    public BookPageDAO(BookPageRepository bookPageRepository) {
        this.bookPageRepository = bookPageRepository;
    }

    public BookPageEntity getBookPageEntityById(Long id){
        return bookPageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find specific page"));
    }

    public BookPageEntity getBookPageEntityByBookEntityAndPageNumber(BookEntity bookEntity, int pageNumber){
        return bookPageRepository.getBookPageEntityByBookEntityAndPageNumber(bookEntity,pageNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find specific page"));
    }

    public List<BookPageEntity> getBookPageEntityByBookEntity(BookEntity bookEntity){
        return bookPageRepository.getBookPageEntityByBookEntity(bookEntity);
    }

    public List<BookPageEntity> getBookPageEntityByBookIdAndPages(Long bookId,int startPage,int endPage){
        return bookPageRepository.getBookPageEntitiesByPageNumbers(bookId,startPage,endPage);
    }

}
