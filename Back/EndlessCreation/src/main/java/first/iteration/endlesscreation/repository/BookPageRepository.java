package first.iteration.endlesscreation.repository;


import first.iteration.endlesscreation.Model.BookPageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookPageRepository extends JpaRepository<BookPageEntity, Long> {

}
