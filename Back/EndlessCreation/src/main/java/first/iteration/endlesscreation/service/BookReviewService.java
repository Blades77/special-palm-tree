package first.iteration.endlesscreation.service;


import first.iteration.endlesscreation.dao.BookReviewDAO;
import first.iteration.endlesscreation.repository.BookReviewRepository;
import org.springframework.stereotype.Service;

@Service
public class BookReviewService {

    private final BookReviewRepository bookReviewRepository;
    private final BookReviewDAO bookReviewDAO;

    public BookReviewService(BookReviewRepository bookReviewRepository, BookReviewDAO bookReviewDAO) {
        this.bookReviewRepository = bookReviewRepository;
        this.bookReviewDAO = bookReviewDAO;
    }
}


