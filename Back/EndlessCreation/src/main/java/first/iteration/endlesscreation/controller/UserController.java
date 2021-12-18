package first.iteration.endlesscreation.controller;


import first.iteration.endlesscreation.configuration.SpringFoxConfig;
import first.iteration.endlesscreation.dto.BookDTO;
import first.iteration.endlesscreation.dto.UserDTO;
import first.iteration.endlesscreation.dto.create.BookCreateDTO;
import first.iteration.endlesscreation.repository.UserRepository;
import first.iteration.endlesscreation.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@Api(tags = {SpringFoxConfig.user})
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value ="Return data of specific user")
    @GetMapping("user/{name}")
    private UserDTO getSpecifiUser(@ApiParam(value = "Name of user", example = "Hadzior", required = true) @PathVariable String name) {
        return userService.getUserByUserName(name);
    }

    @ApiOperation(value ="Login user")
    @PostMapping("login")
    private void signup(@Valid @RequestBody UserDTO userDTO) {
    }

    @ApiOperation(value = "Creates user")
    @PostMapping("/user")
    private void createUser(@RequestBody UserDTO userDTO) {
        userService.saveUser(userDTO);
    }
}
