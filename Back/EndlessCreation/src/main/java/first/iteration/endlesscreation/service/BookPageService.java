package first.iteration.endlesscreation.service;

import first.iteration.endlesscreation.dao.BookDAO;
import first.iteration.endlesscreation.repository.BookPageRepository;
import org.springframework.stereotype.Service;

@Service
public class BookPageService {

    private final BookPageRepository bookPageRepository;
    private final BookDAO bookDAO;

    public BookPageService(BookPageRepository bookPageRepository, BookDAO bookDAO) {
        this.bookPageRepository = bookPageRepository;
        this.bookDAO = bookDAO;
    }
}
