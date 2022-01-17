package first.iteration.endlesscreation.controller;


import first.iteration.endlesscreation.configuration.SpringFoxConfig;
import first.iteration.endlesscreation.service.FireBaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@Api(tags = {SpringFoxConfig.file})
public class filesController {

    private final FireBaseService fireBaseService;

    public filesController(FireBaseService fireBaseService) {
        this.fireBaseService = fireBaseService;
    }

    @RequestMapping(
            path = "file/group/{groupId}",
            method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Object groupUpload(@RequestPart("file") MultipartFile multipartFile,@ApiParam(value = "groupId", example = "1", required = true) @PathVariable Long groupId) {
        return fireBaseService.uploadGroupImage(multipartFile,groupId);
    }


    @DeleteMapping("file/group/{groupId}")
    private void deleteGroupImage(@ApiParam(value = "groupId", example = "1", required = true) @PathVariable Long groupId){
        fireBaseService.deleteGroupImage(groupId);
    }
    @RequestMapping(
            path = "/user/upload",
            method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Object userUpload(@RequestPart("file") MultipartFile multipartFile) {
        return fireBaseService.uploadUserImage(multipartFile);
    }

    @DeleteMapping("/user/delete")
    private void deleteUserImage(){
        fireBaseService.deleteUserImage();
    }



//    @DeleteMapping("/group/delete")
//    private void deleteGroupImage(){
//        fireBaseService.delete2();
//    }

}
