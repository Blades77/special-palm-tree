package first.iteration.endlesscreation.service;


import first.iteration.endlesscreation.Model.BookEntity;
import first.iteration.endlesscreation.Model.BookReviewEntity;
import first.iteration.endlesscreation.dao.BookReviewDAO;
import first.iteration.endlesscreation.dto.BookReviewDTO;
import first.iteration.endlesscreation.dto.Update.BookReviewUpdateDTO;
import first.iteration.endlesscreation.dto.create.BookReviewCreateDTO;
import first.iteration.endlesscreation.exception.InvalidPathVariableExpection;
import first.iteration.endlesscreation.mapper.BookMapper;
import first.iteration.endlesscreation.mapper.BookReviewMapper;
import first.iteration.endlesscreation.repository.BookReviewRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookReviewService {

    private final BookReviewRepository bookReviewRepository;
    private final BookService bookService;
    private final BookReviewDAO bookReviewDAO;

    public BookReviewService(BookReviewRepository bookReviewRepository, BookReviewDAO bookReviewDAO,BookService bookService) {
        this.bookReviewRepository = bookReviewRepository;
        this.bookReviewDAO = bookReviewDAO;
        this.bookService = bookService;
    }

    public List<BookReviewDTO> getBookReviewsByBookId(Long bookId){
        BookEntity bookEntity = bookService.getBookEntityById(bookId);
        List<BookReviewEntity> bookReviewEntityList = bookReviewDAO.getBookReviewEntityByBookEntity(bookEntity);
        return BookReviewMapper.mapToBookReviewDTOLIst(bookReviewEntityList);
    }

    public List<BookReviewDTO> getBookReviewsByBookIdOrderByDate(String order, Long bookId){
        BookEntity bookEntity = bookService.getBookEntityById(bookId);
        List<BookReviewEntity> bookReviewEntityList;
        if(order.equals("asc")) {
            bookReviewEntityList  = bookReviewDAO.getBookReviewEntityByBookEntitySort(bookEntity,Sort.by(Sort.Direction.ASC, "createdAt"));
        }
        else if(order.equals("desc")){
            bookReviewEntityList  = bookReviewDAO.getBookReviewEntityByBookEntitySort(bookEntity,Sort.by(Sort.Direction.DESC, "createdAt"));
        }
        else{
            throw new InvalidPathVariableExpection("Invalid search type, should be \"asc\" or \"desc\".");
        }
        return BookReviewMapper.mapToBookReviewDTOLIst(bookReviewEntityList);
    }

    public void createBookReview(BookReviewCreateDTO bookReviewCreateDTO){
        BookEntity bookEntity = bookService.getBookEntityById(bookReviewCreateDTO.getBookId());
        BookReviewEntity bookReviewEntity = BookReviewMapper.mapCreateToBookReviewEntity(bookReviewCreateDTO,bookEntity);
        bookReviewRepository.save(bookReviewEntity);
    }

    public void deleteBookReview(Long id){
        BookReviewEntity bookReviewEntity =  bookReviewDAO.getBookReviewEntity(id);
        bookReviewRepository.delete(bookReviewEntity);
    }
    public void updateBookReview(BookReviewUpdateDTO bookReviewUpdateDTO){
        BookReviewEntity bookReviewEntity = bookReviewDAO.getBookReviewEntity(bookReviewUpdateDTO.getReviewId());
        bookReviewRepository.save(BookReviewMapper.mapUpdateToBookEntity(bookReviewUpdateDTO,bookReviewEntity));
    }

}


