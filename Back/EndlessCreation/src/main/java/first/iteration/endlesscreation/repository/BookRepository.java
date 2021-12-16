package first.iteration.endlesscreation.repository;

import first.iteration.endlesscreation.Model.BookEntity;
import first.iteration.endlesscreation.Model.TileEntity;
import first.iteration.endlesscreation.dao.BookDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {
    @Query(value ="SELECT * FROM book WHERE book_title LIKE :string",nativeQuery = true)
    Optional<List<BookEntity>> searchBookTitleByParam(@Param("string") String search);

    @Query(value="SELECT * FROM book WHERE  book_id IN ( SELECT book_id FROM book_tag WHERE tag_id IN :tagIdList\n" +
            " GROUP BY tile_id HAVING COUNT(tile_id) = :listLength)",nativeQuery = true)
    Optional<List<BookEntity>> getBookEntityByTagIdList(@Param("tagIdList") List<Long> tagIdList, @Param("listLength") int listLength);

    @Query(value="SELECT DISTINCT b.* FROM book b JOIN book_tag g ON b.book_id = g.book_id WHERE g.tag_id IN :tagIdList" ,nativeQuery = true)
    Optional<List<BookEntity>> getBookEntityByAtLeastOneTagIdList(@Param("tagIdList") List<Long> tagIdList);

}
