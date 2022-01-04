package first.iteration.endlesscreation.controller;


import first.iteration.endlesscreation.configuration.SpringFoxConfig;
import first.iteration.endlesscreation.dto.BookDTO;
import first.iteration.endlesscreation.dto.GroupDataDTO;
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

    @ApiOperation(value ="Return groups")
    @GetMapping("groups")
    private List<GroupDataDTO> getGroups() {
        return groupDataService.getGroups();
    }
}
