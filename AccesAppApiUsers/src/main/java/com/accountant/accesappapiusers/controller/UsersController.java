package com.accountant.accesappapiusers.controller;

import com.accountant.accesappapiusers.model.dto.UserDto;
import com.accountant.accesappapiusers.model.requests.CreateUserRequestModel;
import com.accountant.accesappapiusers.model.requests.UpdateUserRequestModel;
import com.accountant.accesappapiusers.model.responses.CreateUserResponseModel;
import com.accountant.accesappapiusers.model.responses.UserResponseModel;
import com.accountant.accesappapiusers.model.user.UserEntity;
import com.accountant.accesappapiusers.service.UserService;
import com.accountant.accesappapiusers.util.UserMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private Environment env;
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper mapper;
    @Value("${token.secret}")
    private String secret;

    @GetMapping("/status/check")
    public String status() {
        return "working on " + env.getProperty("local.server.port") + "\n" + "secret: " + secret;
    }

    @PostMapping(
        consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
        produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<CreateUserResponseModel> createUser(@Valid @RequestBody CreateUserRequestModel user) {
        UserDto userDto = mapper.toUserDto(user);
        UserDto savedUser = userService.createUser(userDto);
        CreateUserResponseModel createUserResponseModel = mapper.toCreateUserResponseModel(savedUser);
        return ResponseEntity.status(HttpStatus.valueOf(201)).body(createUserResponseModel);
    }

    @GetMapping(path = "/{userId}/user",
        consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
        produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("principal.userId == #userId")
    public ResponseEntity<UserResponseModel> getUser(@PathVariable String userId) {
        UserDto userDto = userService.getUserByUserIdWithAlbums(userId);
        UserResponseModel userResponseModel = mapper.toUserResponseModel(userDto);
        return ResponseEntity.status(HttpStatus.valueOf(200)).body(userResponseModel);
    }

    @DeleteMapping(path = "/{userId}/user")
    @PreAuthorize("hasAuthority('ADMIN')")//role != authority
    public ResponseEntity<CreateUserResponseModel> deleteUser(@PathVariable String userId) {
        CreateUserResponseModel createUserResponseModel = userService.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.valueOf(200)).body(createUserResponseModel);
    }

    @GetMapping(path = "/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers());
    }

    @PutMapping(
        consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
        produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("principal.userId == #user.userId")
    public ResponseEntity<UserEntity> updateUser(@Valid @RequestBody UpdateUserRequestModel user) {
        UserEntity userEntity = userService.updateUser(user);
        return ResponseEntity.status(HttpStatus.valueOf(200)).body(userEntity);
    }
}
