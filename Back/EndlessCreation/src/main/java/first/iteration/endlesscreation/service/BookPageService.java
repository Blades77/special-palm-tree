package first.iteration.endlesscreation.service;

import first.iteration.endlesscreation.Model.BookEntity;
import first.iteration.endlesscreation.Model.BookPageEntity;
import first.iteration.endlesscreation.dao.BookDAO;
import first.iteration.endlesscreation.dao.BookPageDAO;
import first.iteration.endlesscreation.dto.BookPageDTO;
import first.iteration.endlesscreation.dto.Update.BookPageUpdateDTO;
import first.iteration.endlesscreation.dto.Update.BookUpdateDTO;
import first.iteration.endlesscreation.dto.create.BookPageCreateDTO;
import first.iteration.endlesscreation.mapper.BookMapper;
import first.iteration.endlesscreation.mapper.BookPageMapper;
import first.iteration.endlesscreation.repository.BookPageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookPageService {

    private final BookPageRepository bookPageRepository;
    private final BookService bookService;
    private final BookPageDAO bookPageDAO;

    public BookPageService(BookPageRepository bookPageRepository, BookPageDAO bookPageDAO, BookService bookService) {
        this.bookPageRepository = bookPageRepository;
        this.bookPageDAO = bookPageDAO;
        this.bookService = bookService;
    }

    public BookPageDTO getBookPageById(Long id){
        BookPageEntity bookPageEntity = bookPageDAO.getBookPageEntityById(id);
        return BookPageMapper.mapToBookPageDTO(bookPageEntity);
    }

    public BookPageDTO getBookPageByPageNumber(Long pageNumber){
        BookPageEntity bookPageEntity = bookPageDAO.getBookPageEntityByPageNumber(pageNumber);
        return BookPageMapper.mapToBookPageDTO(bookPageEntity);
    }

    public List<BookPageDTO> getBookPagesByBookId(Long id){
        BookEntity bookEntity  = bookService.getBookEntityById(id);
        List<BookPageEntity> bookPageEntityList = bookPageDAO.getBookPageEntityByBookEntity(bookEntity);
        return BookPageMapper.mapToBookPageDTOList(bookPageEntityList);
    }

    public List<BookPageDTO> getBookPagesByBookIdAndPages(Long bookId,Long startPage,Long endPage){
        List<BookPageEntity> bookPageEntityList = bookPageDAO.getBookPageEntityByBookIdAndPages(bookId,startPage,endPage);
        return BookPageMapper.mapToBookPageDTOList(bookPageEntityList);
    }

    public void createBookPage(BookPageCreateDTO bookPageCreateDTO){
        BookEntity bookEntity = bookService.getBookEntityById(bookPageCreateDTO.getBookId());
        BookPageEntity bookPageEntity = BookPageMapper.mapCreateToBookPageEntity(bookPageCreateDTO,bookEntity);
        bookPageRepository.save(bookPageEntity);
    }

    public void updateBookPage(BookPageUpdateDTO bookPageUpdateDTO){
        BookPageEntity bookPageEntity = bookPageDAO.getBookPageEntityById(bookPageUpdateDTO.getPageId());
        bookPageEntity = BookPageMapper.mapUpdateToBookPageEntity(bookPageUpdateDTO,bookPageEntity);
        bookPageRepository.save(bookPageEntity);
    }

    public void deleteBookPage(Long id){
        BookPageEntity bookPageEntity = bookPageDAO.getBookPageEntityById(id);
        bookPageRepository.delete(bookPageEntity);
    }
}
