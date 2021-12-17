package first.iteration.endlesscreation.controller;


import first.iteration.endlesscreation.configuration.SpringFoxConfig;
import first.iteration.endlesscreation.dto.BookReviewDTO;
import first.iteration.endlesscreation.dto.Update.BookReviewUpdateDTO;
import first.iteration.endlesscreation.dto.create.BookReviewCreateDTO;
import first.iteration.endlesscreation.service.BookReviewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = {SpringFoxConfig.bookReview})
public class ReviewController {

    private final BookReviewService bookReviewService;

    public ReviewController(BookReviewService bookReviewService) {
        this.bookReviewService = bookReviewService;
    }

    @ApiOperation(value="Returns list reviews for certain book")
    @GetMapping("/review/{bookId}")
    private List<BookReviewDTO> getBookReviewsByBookId(@ApiParam(value = "Id of an book", example = "1", required = true) @PathVariable Long bookId) {
        return bookReviewService.getBookReviewsByBookId(bookId);
    }

    @ApiOperation(value="Returns list of reviews for certain book order by date")
    @GetMapping("/review/{order}/{bookId}")
    private List<BookReviewDTO> getBookReviewsByBookIdAndOrder(@ApiParam(value = "order : asc returns reviews in ascending order, order : desc return reviews in descending orderd", example = "asc", required = true) @PathVariable String order,
                                              @ApiParam(value = "Id of a book", example = "1", required = true) @PathVariable Long bookId)
    {
        return bookReviewService.getBookReviewsByBookIdOrderByDate(order,bookId);
    }

    @ApiOperation(value = "Creates review")
    @PostMapping("/review")
    private void createReview(@RequestBody BookReviewCreateDTO bookReviewCreateDTO) {
        bookReviewService.createBookReview(bookReviewCreateDTO);
    }

    @ApiOperation(value = "Edit specific review")
    @PutMapping("/review/")
    private void editReview(@RequestBody BookReviewUpdateDTO bookReviewUpdateDTO){
        bookReviewService.updateBookReview(bookReviewUpdateDTO);
    }

    @ApiOperation(value = "Deletes review")
    @DeleteMapping("/review{id}")
    private void deleteReview(@ApiParam(value = "Id of review", example = "1", required = true) @PathVariable Long id) {
        bookReviewService.deleteBookReview(id);
    }
}
