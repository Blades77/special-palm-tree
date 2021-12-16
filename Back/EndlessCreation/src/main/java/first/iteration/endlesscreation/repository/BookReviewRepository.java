package first.iteration.endlesscreation.repository;

import first.iteration.endlesscreation.Model.BookEntity;
import first.iteration.endlesscreation.Model.BookReviewEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookReviewRepository extends JpaRepository<BookReviewEntity, Long> {
    List<BookReviewEntity> getBookReviewEntityByBookEntity(BookEntity bookEntity);
    List<BookReviewEntity> getBookReviewEntityByBookEntity(BookEntity bookEntity, Sort sort);
}
