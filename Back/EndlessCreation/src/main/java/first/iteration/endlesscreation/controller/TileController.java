package first.iteration.endlesscreation.controller;

import first.iteration.endlesscreation.dto.Update.TileUpdateDTO;
import first.iteration.endlesscreation.dto.create.TagCreateDTO;
import first.iteration.endlesscreation.service.TileService;
import first.iteration.endlesscreation.dto.TileDTO;
import first.iteration.endlesscreation.dto.create.TileCreateDTO;
import first.iteration.endlesscreation.configuration.SpringFoxConfig;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
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
    @GetMapping("tile/{id}")
    private TileDTO getSpecificTile(@ApiParam(value = "Id of tile", example = "1", required = true) @PathVariable Long id) {
        return tileService.getFullTileById(id);
    }

//    @ApiOperation(value = "Returns list of tiles")
//    @GetMapping("/tiles")
//    private List<TileDTO> getTiles(){
//       return tileService.getTiles();
//    }

//    @ApiOperation(value = "Returns list of tiles by groupDataId")
//    @GetMapping("/tiles/group/{id}")
//    private List<TileDTO> getTilesByGroupId(@ApiParam(value = "Id of group", example = "1", required = true) @PathVariable Long id) {
//        return tileService.getTilesByGroupId(id);
//    }

    @ApiOperation(value="Returns list of tiles containing  tags")
    @GetMapping("/tiles/group/{groupId}/{searchType}/{order}/{tagIdList}/")
    private List<TileDTO> getTilesByAllTagsId(@ApiParam(value = "Search type: all returns Tiles with all tags included, one returns Tiles with at least one tile included", example = "all", required = true) @PathVariable String searchType,
                                              @ApiParam(value = "Order", example = "asc", required = true) @PathVariable String order,
                                              @ApiParam(value = "List of ids of tags", example = "1034,1035", required = true) @PathVariable List<Long> tagIdList,
                                              @ApiParam(value = "Current Group Id or 0 for all group search", example = "1", required = true) @PathVariable Long groupId) {
        return tileService.getTilesIncludingAllTagIdList(groupId,searchType,order,tagIdList);
    }

    @ApiOperation(value="Returns list of tiles ordered by date")
    @GetMapping("/tiles/group/{groupId}/{order}")
    private List<TileDTO> getTilesOrderedByDate(
            @ApiParam(value = "Id of an group 0 search everywhere", example = "1", required = true) @PathVariable Long groupId,
            @ApiParam(value = "order : asc returns reviews in ascending order, order : desc return reviews in descending order", example = "asc", required = true) @PathVariable String order)
    {
        return tileService.getTilesByGroupId(groupId,order);
    }

    @ApiOperation(value="Returns list of tiles containing parameter in title")
    @GetMapping("/tile/group/{groupId}/search={searchParameter}")
    private List<TileDTO> getTilesByParameter(@ApiParam(value = "Search parameter", example = "string", required = true) @PathVariable String searchParameter,
        @ApiParam(value = "Current Group Id or 0 for all group search", example = "1", required = true) @PathVariable Long groupId) {
        return tileService.getTilesBySearchTileTitle(groupId,searchParameter);
    }



    @ApiOperation(value = "Creates Tile")
    @PostMapping("/tile/create/group/{groupId}")
    private void createTile(@ApiParam(value = "groupId", example = "1", required = true) @PathVariable Long groupId,
                            @RequestBody TileCreateDTO tileCreateDTO) {
        tileService.createTile(tileCreateDTO,groupId);
    }


    @ApiOperation(value = "Adds like to tile")
    @PostMapping("/tile/like/{tileId}")
    private ResponseEntity<String> doLikeTile(@ApiParam(value = "tileId", example = "1", required = true) @PathVariable Long tileId){
       return tileService.doLike(tileId);
    }

//    @ApiOperation(value = "Delete tile")
//    @DeleteMapping("/tile/like/{tileId}")
//    private void deleteLikeFromTile(@ApiParam(value = "tileId", example = "1", required = true) @PathVariable Long tileId){
//        tileService.deleteLikeForTile(tileId);
//    }

    @ApiOperation(value = "Edit specific tile")
    @PutMapping("/tile/edit/group/{groupId}")
    private void editTile(@ApiParam(value = "groupId", example = "1", required = true) @PathVariable Long groupId,
                          @RequestBody TileUpdateDTO tileUpdateDTO){
        tileService.editTile(tileUpdateDTO);
    }


    @ApiOperation(value = "Delete tile")
    @DeleteMapping("/tile/delete/group/{groupId}/{tileId}")
    private void deleteTile(@ApiParam(value = "groupId", example = "1", required = true) @PathVariable Long groupId,
                          @ApiParam(value = "tileId", example = "1", required = true) @PathVariable Long tileId){
        tileService.deleteTile(tileId);
    }

//    @ApiOperation(value = "Gets tags by tile id")
//    @GetMapping("/tile/tags/{id}")
//    private List<TagDTO> getTagsForTile(@ApiParam(value = "Id of tile", example = "1", required = true) @PathVariable Long id){
//        return tileService.getTagsForTile(id);
//    }

    @ApiOperation(value = "Add tag to tile")
    @PostMapping("/tile/tag/{id}")
    private void addTagToTile(@ApiParam(value = "Id of tile", example = "1", required = true) @PathVariable Long id,@RequestBody TagCreateDTO tagCreateDTO) {
        tileService.addTagToTile(id,tagCreateDTO);
    }

    @ApiOperation(value = "Remove tag from tile")
    @DeleteMapping("/tile/tag/{tileId}/{tagId}")
    private void removeTagFromTile(@ApiParam(value = "Id of tile", example = "1", required = true) @PathVariable Long tileId,
                                   @ApiParam(value = "Id of tag", example = "1", required = true) @PathVariable Long tagId) {
        tileService.deleteTagFromTile(tileId,tagId);
    }

}
