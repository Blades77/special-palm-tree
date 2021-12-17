package first.iteration.endlesscreation.controller;


import first.iteration.endlesscreation.configuration.SpringFoxConfig;
import first.iteration.endlesscreation.dto.BookDTO;
import first.iteration.endlesscreation.dto.BookPageDTO;
import first.iteration.endlesscreation.dto.Update.BookPageUpdateDTO;
import first.iteration.endlesscreation.dto.Update.BookUpdateDTO;
import first.iteration.endlesscreation.dto.create.BookCreateDTO;
import first.iteration.endlesscreation.dto.create.BookPageCreateDTO;
import first.iteration.endlesscreation.service.BookPageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = {SpringFoxConfig.bookPage})
public class PageController {

    private final BookPageService bookPageService;

    public PageController(BookPageService bookPageService) {
        this.bookPageService = bookPageService;
    }

    @ApiOperation(value ="Return data of specific page")
    @GetMapping("page/{id}")
    private BookPageDTO getSpecificPageById(@ApiParam(value = "Id of page", example = "1", required = true) @PathVariable Long id) {
        return bookPageService.getBookPageById(id);
    }

    @ApiOperation(value ="Return data of specific page")
    @GetMapping("page/book/{bookId}/{pageNumber}")
    private BookPageDTO getSpecificPageByPageNumber(@ApiParam(value = "id of an book", example = "1", required = true) @PathVariable Long bookId,
                                                    @ApiParam(value = "Number of page", example = "1", required = true) @PathVariable int pageNumber) {
        return bookPageService.getBookPageByPageNumber(bookId,pageNumber);
    }

    @ApiOperation(value ="Return data of pages by bookId")
    @GetMapping("pages/book/{id}")
    private List<BookPageDTO> getPagesByBookId(@ApiParam(value = "Id of book", example = "1", required = true) @PathVariable Long id) {
        return bookPageService.getBookPagesByBookId(id);
    }

    @ApiOperation(value ="Return data of specific pages by bookId")
    @GetMapping("pages/book/{id}/{startPage}/{endPage}")
    private List<BookPageDTO> getPagesByBookIdAndPageNumbers(@ApiParam(value = "Id of book", example = "1", required = true) @PathVariable Long id,
                                                             @ApiParam(value = "Start page", example = "1", required = true) @PathVariable int startPage,
                                                             @ApiParam(value = "Ending page", example = "2", required = true) @PathVariable int endPage) {
        return bookPageService.getBookPagesByBookIdAndPages(id,startPage,endPage);
    }

    @ApiOperation(value = "Creates Page")
    @PostMapping("/page")
    private void createPage(@RequestBody BookPageCreateDTO bookPageCreateDTO) {
        bookPageService.createBookPage(bookPageCreateDTO);
    }

    @ApiOperation(value = "Edit specific Page")
    @PutMapping("/page/")
    private void editBook(@RequestBody BookPageUpdateDTO bookPageUpdateDTO){
        bookPageService.updateBookPage(bookPageUpdateDTO);
    }

    @ApiOperation(value = "Deletes page")
    @DeleteMapping("/page/{id}")
    private void deletePage(@ApiParam(value = "Id of page", example = "1", required = true) @PathVariable Long id) {
        bookPageService.deleteBookPage(id);
    }




}
