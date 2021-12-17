package first.iteration.endlesscreation.repository;


import first.iteration.endlesscreation.Model.BookEntity;
import first.iteration.endlesscreation.Model.BookPageEntity;
import first.iteration.endlesscreation.Model.TileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookPageRepository extends JpaRepository<BookPageEntity, Long> {
    List<BookPageEntity> getBookPageEntityByBookEntity(BookEntity bookEntity);
    Optional<BookPageEntity> getBookPageEntityByBookEntityAndPageNumber(BookEntity bookEntity,int pageNumber);

    @Query(value="SELECT * FROM book_page WHERE book_id = :bookId AND (page_number BETWEEN :startPage AND :endPage)",nativeQuery = true)
    List<BookPageEntity> getBookPageEntitiesByPageNumbers(@Param("bookId") Long bookId,@Param("startPage") int startPage,@Param("endPage") int endPage);

}
