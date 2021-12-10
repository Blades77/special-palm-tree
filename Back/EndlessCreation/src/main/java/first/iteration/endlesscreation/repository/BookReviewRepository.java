package first.iteration.endlesscreation.repository;

import first.iteration.endlesscreation.Model.BookReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookReviewRepository extends JpaRepository<BookReviewEntity, Long> {

}
