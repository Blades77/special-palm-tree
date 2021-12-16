package first.iteration.endlesscreation.dao;

import first.iteration.endlesscreation.Model.BookEntity;
import first.iteration.endlesscreation.Model.BookReviewEntity;
import first.iteration.endlesscreation.exception.ResourceNotFoundException;
import first.iteration.endlesscreation.repository.BookReviewRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookReviewDAO {

    private final BookReviewRepository bookReviewRepository;

    public BookReviewDAO(BookReviewRepository bookReviewRepository) {
        this.bookReviewRepository = bookReviewRepository;
    }

    public BookReviewEntity getBookReviewEntity(Long id){
        return bookReviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find specified review"));
    }

    public List<BookReviewEntity> getBookReviewEntityByBookEntity(BookEntity bookEntity){
        return  bookReviewRepository.getBookReviewEntityByBookEntity(bookEntity);
    }
    public List<BookReviewEntity> getBookReviewEntityByBookEntitySort(BookEntity bookEntity, Sort sort){
        return  bookReviewRepository.getBookReviewEntityByBookEntity(bookEntity,sort);
    }
}
