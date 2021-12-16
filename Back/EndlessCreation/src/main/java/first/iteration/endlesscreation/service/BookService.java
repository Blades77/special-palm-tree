package first.iteration.endlesscreation.service;

import first.iteration.endlesscreation.Model.BookEntity;
import first.iteration.endlesscreation.Model.TagEntity;
import first.iteration.endlesscreation.dao.BookDAO;
import first.iteration.endlesscreation.dto.BookDTO;
import first.iteration.endlesscreation.dto.TagDTO;
import first.iteration.endlesscreation.dto.Update.BookUpdateDTO;
import first.iteration.endlesscreation.dto.create.BookCreateDTO;
import first.iteration.endlesscreation.dto.create.TagCreateDTO;
import first.iteration.endlesscreation.exception.InvalidPathVariableExpection;
import first.iteration.endlesscreation.mapper.BookMapper;
import first.iteration.endlesscreation.repository.BookRepository;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final TagService tagService;
    private final BookDAO bookDAO;

    public BookService(BookRepository bookRepository, BookDAO bookDAO,TagService tagService) {
        this.bookRepository = bookRepository;
        this.bookDAO = bookDAO;
        this.tagService = tagService;
    }

    public BookEntity getBookEntityById(Long id){
        BookEntity bookEntity = bookDAO.getBookEntity(id);
        return bookEntity;
    }

    public List<BookEntity> findBookEntityByTitle(String titleSearch){
        List<BookEntity> bookEntityList = bookDAO.searchTileTitleByParam(titleSearch);
        return bookEntityList;
    }

    public BookDTO getBookDTOById(Long id){
        List<TagDTO> tagDTOList = getTagsForBook(id);
        return BookMapper.mapToBookDTO(getBookEntityById(id),tagDTOList);
    }

    public List<BookDTO> getBooksDTOByTitle(String titleSearch){
        List <BookEntity> bookEntityList = findBookEntityByTitle(titleSearch);
        return mapToBookDTOList(bookEntityList);
    }
    public List<BookDTO> getBooksIncludingTagsIdList(String searchType, List<Long> tagIdList){
        List<BookEntity> bookEntityList = new ArrayList<>();
        if(searchType.equals("all")) {
            int listLength = tagIdList.size();
            bookEntityList = bookDAO.getBookEntityByTagIdList(tagIdList,listLength);
        }
        else if(searchType.equals("one")){
           bookEntityList = bookDAO.getTileEntityByAtLeastOneTagIdList(tagIdList);
        }
        else{
            throw new InvalidPathVariableExpection("Invalid search type, should be \"all\" or \"one\".");
        }
        return mapToBookDTOList(bookEntityList);
    }

    private List<BookDTO> mapToBookDTOList(List<BookEntity> bookEntityList){
        List<BookDTO> bookDTOList = new ArrayList<>();
        for(BookEntity bookEntity : bookEntityList){
            List<TagDTO> tagDTOList = getTagsForBook(bookEntity.getBookId());
            bookDTOList.add(BookMapper.mapToBookDTO(bookEntity,tagDTOList));
        }
        return bookDTOList;
    }

    public List<TagDTO> getTagsForBook(Long id){
        BookEntity bookEntity = bookDAO.getBookEntity(id);
        return tagService.getTagsForBook(bookEntity);
    }

    public void createBook(BookCreateDTO bookCreateDTO){
        BookEntity bookEntity = BookMapper.mapCreateToBookEntity(bookCreateDTO);
        List<TagEntity> tagEntityList = tagService.getTagsEntityListByTagCreateDTOList(bookCreateDTO.getTagCreateDTOList());
        for(TagEntity tagEntity : tagEntityList){
            bookEntity.addTag(tagEntity);
        }
        bookRepository.save(bookEntity);
    }


    public void editBook(BookUpdateDTO bookUpdateDTO){
        BookEntity bookEntity = getBookEntityById(bookUpdateDTO.getBookId());
        bookEntity.setBookTitle(bookUpdateDTO.getBookTitle());
        bookRepository.save(bookEntity);
    }

    public void deleteBook(Long id){
        BookEntity bookEntity = getBookEntityById(id);
        List<TagEntity> tagEntityList = tagService.getTagEntityListByBook(bookEntity);
        if(tagEntityList.isEmpty() == false) {
            for (TagEntity tagEntity : tagEntityList) {
                bookEntity.removeTag(tagEntity);
            }
        }
        bookRepository.save(bookEntity);
        bookRepository.delete(bookEntity);
    }

    public void addTagForBook(Long bookId, TagCreateDTO tagCreateDTO){
        BookEntity bookEntity = bookDAO.getBookEntity(bookId);
        TagEntity tagEntity = tagService.findOrCreateTag(tagCreateDTO);
        bookEntity.addTag(tagEntity);
        bookRepository.save(bookEntity);
    }

    public void deleteTagFromBook(Long bookId, Long tagId){
        BookEntity bookEntity = bookDAO.getBookEntity(bookId);
        TagEntity tagEntity = tagService.getTagEntityById(tagId);
        bookEntity.removeTag(tagEntity);
        bookRepository.save(bookEntity);
    }

}
