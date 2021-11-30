package first.iteration.endlesscreation.controller;

import first.iteration.endlesscreation.dto.GroupDataDTO;
import first.iteration.endlesscreation.dto.TagDTO;
import first.iteration.endlesscreation.service.TileService;
import first.iteration.endlesscreation.dto.TileDTO;
import first.iteration.endlesscreation.dto.create.TileCreateDTO;
import first.iteration.endlesscreation.configuration.SpringFoxConfig;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = {SpringFoxConfig.tile})
public class TileController {

    private final TileService tileService;

    public TileController(TileService tileService){
        this.tileService = tileService;
    }

    @ApiOperation(value ="Return data of specific tile")
    @GetMapping("tiles/{id}")
    private TileDTO getSpecificTile(@ApiParam(value = "Id of tile", example = "1", required = true) @PathVariable Long id) {
        return tileService.getTileById(id);
    }

    @ApiOperation(value = "Returns list of tiles")
    @GetMapping("/tiles")
    private List<TileDTO> getTiles(){
       return tileService.getTiles();
    }

    @ApiOperation(value = "Edit specific tile")
    @PutMapping("/tile")
    private void editTile(@RequestBody TileDTO tileDTO){
        tileService.editTile(tileDTO);
        }


    @ApiOperation(value = "Delete tile")
    @DeleteMapping("/tiles{id}")
    private void deleTile(@ApiParam(value = "Id of tile", example = "1", required = true) @PathVariable Long id) {
        tileService.deleTile(id);
    }

    @ApiOperation(value = "Get groups for tile")
    @GetMapping("/tile/groups/{id}")
    private GroupDataDTO getGroups(@ApiParam(value = "Id of tile", example = "1", required = true) @PathVariable Long id){
        return tileService.getGroupsForTile(id);
    }








    @ApiOperation(value = "Creates Tile")
    @PostMapping("/tile")
    private void createTile(@RequestBody TileCreateDTO tileCreateDTO) {
        tileService.createTile(tileCreateDTO);
    }

    @ApiOperation(value = "Gets tile by id")
    @PostMapping("/getTileById")
    private TileDTO getTagsForTileId(@RequestParam Long id) {
        return tileService.getTileById(id);
    }

    @ApiOperation(value = "Returns list of tags")
    @GetMapping("/getTagsById")
    private List<TagDTO> getTags(@RequestParam Long id) {
        return tileService.getTagsForTile(id);
    }

    @ApiOperation(value = "insert tags")
    @GetMapping("/inserttags")
    private void insertTags() {
        tileService.deleteTagsForTile();
    }





}
