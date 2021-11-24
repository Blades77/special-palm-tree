package first.iteration.endlesscreation.controller;


import first.iteration.endlesscreation.service.TileService;
import first.iteration.endlesscreation.service.ksiazkaService;
import first.iteration.endlesscreation.dto.TileDTO;
import first.iteration.endlesscreation.dto.create.TileCreateDTO;
import first.iteration.endlesscreation.configuration.SpringFoxConfig;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = {SpringFoxConfig.tile})
public class tileController {

    private final TileService tileService;

    public tileController(TileService tileService){
        this.tileService = tileService;
    }

    @ApiOperation(value = "Returns list of tiles")
    @GetMapping("/getTiles")
    private List<TileDTO> getTiles(){
       return tileService.getTiles();
    }

    @ApiOperation(value = "Creates Tile")
    @PostMapping("/tile")
    private void createDepartment(@RequestBody TileCreateDTO tileCreateDTO) {
        tileService.createTile(tileCreateDTO);
    }

}
