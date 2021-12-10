package first.iteration.endlesscreation.service;

import first.iteration.endlesscreation.dao.BookDAO;
import first.iteration.endlesscreation.repository.BookRepository;

import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookDAO bookDAO;

    public BookService(BookRepository bookRepository, BookDAO bookDAO) {
        this.bookRepository = bookRepository;
        this.bookDAO = bookDAO;
    }
}
