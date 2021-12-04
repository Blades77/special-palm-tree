package first.iteration.endlesscreation.controller;

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

    @ApiOperation(value = "Returns list of tiles by groupDataId")
    @GetMapping("/tiles/group/{id}")
    private List<TileDTO> getTilesByGroupId(@ApiParam(value = "Id of group", example = "1", required = true) @PathVariable Long id) {
        return tileService.getTilesByGroupId(id);
    }

    @ApiOperation(value="Returns list of tiles containing all tags")
    @GetMapping("/tile/groups/{searchType}/{tagIdList}/{groupId}")
    private List<TileDTO> getTilesByAllTagsId(@ApiParam(value = "Search type: all returns Tiles with all tags included, one returns Tiles with at least one tile included", example = "all", required = true) @PathVariable String searchType,
                                              @ApiParam(value = "List of ids of tags", example = "1034,1035", required = true) @PathVariable List<Long> tagIdList,
                                              @ApiParam(value = "List of id of group to search, if id = 0 search will be in every group user is part of", example = "1", required = true) @PathVariable Long groupId) {
        return tileService.getTilesIncludingAllTagIdList(searchType,tagIdList,groupId);
    }

    @ApiOperation(value="Returns list of tiles containing parameter in title")
    @GetMapping("/tile/groups/search{searchParameter}")
    private List<TileDTO> getTilesByAtleastOneTagsId(@ApiParam(value = "Search parameter", example = "string", required = true) @PathVariable String searchParameter) {
        return tileService.getTilesBySearchTileTitle(searchParameter);
    }


    @ApiOperation(value = "Creates Tile")
    @PostMapping("/tile")
    private void createTile(@RequestBody TileCreateDTO tileCreateDTO) {
        tileService.createTile(tileCreateDTO);
    }

    @ApiOperation(value = "Edit specific tile")
    @PutMapping("/tile")
    private void editTile(@RequestBody TileDTO tileDTO){
        tileService.editTile(tileDTO);
        }


    @ApiOperation(value = "Delete tile")
    @DeleteMapping("/tiles{id}")
    private void deleTile(@ApiParam(value = "Id of tile", example = "1", required = true) @PathVariable Long id) {
        tileService.deleteTile(id);
    }

//    @ApiOperation(value = "Get groups for tile")
//    @GetMapping("/tile/groups/{id}")
//    private GroupDataDTO getGroups(@ApiParam(value = "Id of tile", example = "1", required = true) @PathVariable Long id){
//        return tileService.getGroupForTile(id);
//    }

    @ApiOperation(value = "Gets tags by tile id")
    @GetMapping("/tile/tags/{id}")
    private List<TagDTO> getTagsForTile(@ApiParam(value = "Id of tile", example = "1", required = true) @PathVariable Long id){
        return tileService.getTagsForTile(id);
    }

//    @ApiOperation(value = "Add tag to tile")
//    @PostMapping("/tile/tags/{id}")
//    private void addTagToTile(@ApiParam(value = "Id of tile", example = "1", required = true) @PathVariable Long id,@RequestBody TagCreateDTO tagCreateDTO) {
//        tileService.addNewTagToTile(id,tagCreateDTO);
//    }

    @ApiOperation(value = "insert tags")
    @GetMapping("/inserttags")
    private void insertTags() {
        tileService.deleteTagsForTile();
    }





}
