package first.iteration.endlesscreation.controller;


import first.iteration.endlesscreation.configuration.SpringFoxConfig;
import first.iteration.endlesscreation.dto.TagDTO;
import first.iteration.endlesscreation.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags ={SpringFoxConfig.tag})
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService){
        this.tagService = tagService;
    }

    @ApiOperation(value="Return list of all Tags")
    @GetMapping("/getTags")
    private List<TagDTO> getTags(){
        return tagService.getAllTags();
    }

    @ApiOperation(value = "deleteTag")
    @GetMapping("/deleteTag")
    private void deleTag(@RequestParam Long id) {
        tagService.deleTag(id);
    }

    @ApiOperation(value = "edittag")
    @GetMapping("/editTag")
    private void editTag(@RequestParam Long id) {
        tagService.editTag(id);
    }

}
