package first.iteration.endlesscreation.controller;


import first.iteration.endlesscreation.configuration.SpringFoxConfig;
import first.iteration.endlesscreation.dto.BookDTO;
import first.iteration.endlesscreation.dto.GroupDataDTO;
import first.iteration.endlesscreation.dto.TileDTO;
import first.iteration.endlesscreation.service.GroupDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = {SpringFoxConfig.groupData})
public class GroupController {

    private final GroupDataService groupDataService;

    public GroupController(GroupDataService groupDataService) {

        this.groupDataService = groupDataService;
    }

    @ApiOperation(value ="Return groups containing user")
    @GetMapping("groups")
    private List<GroupDataDTO> getGroups() {
        return groupDataService.getGroups();
    }

    @ApiOperation(value ="Return groups containing user")
    @GetMapping("groups/user")
    private List<GroupDataDTO> getGroupsForUser() {

        return groupDataService.getGroupsThatUserIsPartOf();
    }

//    @ApiOperation(value ="Return groups by search parametr")
//    @GetMapping("groups/{search}")
//    private List<GroupDataDTO> getGroupsByParametr(@ApiParam(value = "Id of tile", example = "1", required = true) @PathVariable String search) {
//        return groupDataService.getGroupsByParametr(search);
//    }
//
//    @ApiOperation(value="Returns list of groups containing tags")
//    @GetMapping("/groups/{order}/{tagIdList}/")
//    private List<TileDTO> getTilesByAllTagsId(@ApiParam(value = "Search type: all returns Tiles with all tags included, one returns Tiles with at least one tile included", example = "all", required = true) @PathVariable String order,
//                                              @ApiParam(value = "List of ids of tags", example = "1034,1035", required = true) @PathVariable List<Long> tagIdList){
//        return groupDataService.getTilesIncludingAllTagIdList(order,tagIdList);
//    }




}
