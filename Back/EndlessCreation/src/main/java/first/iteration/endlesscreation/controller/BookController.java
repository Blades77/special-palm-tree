package first.iteration.endlesscreation.controller;


import first.iteration.endlesscreation.configuration.SpringFoxConfig;
import first.iteration.endlesscreation.dto.BookDTO;
import first.iteration.endlesscreation.dto.TileDTO;
import first.iteration.endlesscreation.dto.Update.BookUpdateDTO;
import first.iteration.endlesscreation.dto.Update.TileUpdateDTO;
import first.iteration.endlesscreation.dto.create.BookCreateDTO;
import first.iteration.endlesscreation.dto.create.TagCreateDTO;
import first.iteration.endlesscreation.dto.create.TileCreateDTO;
import first.iteration.endlesscreation.service.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = {SpringFoxConfig.book})
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @ApiOperation(value ="Return data of specific book")
    @GetMapping("book/{id}")
    private BookDTO getSpecificBook(@ApiParam(value = "Id of book", example = "1", required = true) @PathVariable Long id) {
        return bookService.getBookDTOById(id);
    }

    @ApiOperation(value="Returns list of tiles containing parameter in title")
    @GetMapping("/book/search{searchParameter}")
    private List<BookDTO> getBooksByASearchParameter(@ApiParam(value = "Search parameter", example = "string", required = true) @PathVariable String searchParameter) {
        return bookService.getBooksDTOByTitle(searchParameter);
    }

    @ApiOperation(value="Returns list of tiles containing all tags")
    @GetMapping("/book/search{searchType}/{tagIdList}/{groupId}")
    private List<BookDTO> getBooksByTagIdList(@ApiParam(value = "Search type: all returns Books with all tags included, one returns Books with at least one tag included", example = "all", required = true) @PathVariable String searchType,
                                              @ApiParam(value = "List of ids of tags", example = "1034,1035", required = true) @PathVariable List<Long> tagIdList)
                                               {
        return bookService.getBooksIncludingTagsIdList(searchType,tagIdList);
    }

    @ApiOperation(value = "Creates Book")
    @PostMapping("/book")
    private void createBook(@RequestBody BookCreateDTO bookCreateDTO) {
        bookService.createBook(bookCreateDTO);
    }

    @ApiOperation(value = "Edit specific Book")
    @PutMapping("/book/")
    private void editBook(@RequestBody BookUpdateDTO bookUpdateDTO){
        bookService.editBook(bookUpdateDTO);
    }

    @ApiOperation(value = "Deletes book")
    @DeleteMapping("/book{id}")
    private void deleteBook(@ApiParam(value = "Id of book", example = "1", required = true) @PathVariable Long id) {
        bookService.deleteBook(id);
    }

    @ApiOperation(value = "Add tag to book")
    @PostMapping("/book/tag/{id}")
    private void addTagToBook(@ApiParam(value = "Id of book", example = "1", required = true) @PathVariable Long id,@RequestBody TagCreateDTO tagCreateDTO) {
        bookService.addTagForBook(id,tagCreateDTO);
    }

    @ApiOperation(value = "Remove tag from book")
    @DeleteMapping("/book/tag/{bookId}/{tagId}")
    private void removeTagFromBook(@ApiParam(value = "Id of book", example = "1", required = true) @PathVariable Long bookId,@ApiParam(value = "Id of tag", example = "1", required = true) @PathVariable Long tagId) {
        bookService.deleteTagFromBook(bookId,tagId);
    }
}
