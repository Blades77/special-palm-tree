package first.iteration.endlesscreation.controller;

import first.iteration.endlesscreation.configuration.SpringFoxConfig;
import first.iteration.endlesscreation.dto.create.ColorCreateDTO;
import first.iteration.endlesscreation.dto.create.TileCreateDTO;
import first.iteration.endlesscreation.service.ColorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = {SpringFoxConfig.color})
public class ColorController {

    private final ColorService colorService;

    public ColorController(ColorService colorService) {
        this.colorService = colorService;
    }

    @ApiOperation(value = "Creates color")
    @PostMapping("/color")
    private void createColor(@RequestBody ColorCreateDTO colorCreateDTO){
        colorService.findOrCreateColor(colorCreateDTO);
    }
}
