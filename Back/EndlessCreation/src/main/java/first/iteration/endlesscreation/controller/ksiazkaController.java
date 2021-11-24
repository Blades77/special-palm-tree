package first.iteration.endlesscreation.controller;


import first.iteration.endlesscreation.service.ksiazkaService;
import first.iteration.endlesscreation.dto.ksiazkaDTO;
import first.iteration.endlesscreation.configuration.SpringFoxConfig;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@Api(tags = {SpringFoxConfig.ksiazka})
public class ksiazkaController {
    private final ksiazkaService ksiazkaService;


    public ksiazkaController(ksiazkaService ksiazkaService) {
        this.ksiazkaService = ksiazkaService;
    }

    @ApiOperation(value = "Returns list of ksiazki")
    @GetMapping("/getKsiazki")
    private List<ksiazkaDTO> getKsiazki() {
        return ksiazkaService.getKsiazki();
    }
}
