package first.iteration.endlesscreation.dao;


import first.iteration.endlesscreation.Model.BookEntity;
import first.iteration.endlesscreation.Model.ColorEntity;
import first.iteration.endlesscreation.Model.TileEntity;
import first.iteration.endlesscreation.exception.ResourceNotFoundException;
import first.iteration.endlesscreation.repository.BookRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDAO {

    private final BookRepository bookRepository;

    public BookDAO(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookEntity getBookEntity(Long id){
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find specified book"));
    }

    public List<BookEntity> getBookEntityByTagIdList(List<Long> tagIdList, int listLength){
        return  bookRepository.getBookEntityByTagIdList(tagIdList, listLength)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find any books by provided tags"));
    }

    public List<BookEntity> getTileEntityByAtLeastOneTagIdList(List<Long> tagIdList){
        return  bookRepository.getBookEntityByAtLeastOneTagIdList(tagIdList)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find any books by provided tags"));
    }

    public List<BookEntity> searchTileTitleByParam(String tileTitleSearch){
        return  bookRepository.searchBookTitleByParam(tileTitleSearch)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find  any book with matching param in title"));
    }


}
