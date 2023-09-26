package dev.aziz.jwt.backend.controllers;

import dev.aziz.jwt.backend.dtos.UserDto;
import dev.aziz.jwt.backend.entites.User;
import dev.aziz.jwt.backend.mappers.UserMapper;
import dev.aziz.jwt.backend.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("admins")
public class AdminController {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public AdminController(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }


    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> users() {
        List<User> allUsers = userRepository.findAll();
        List<UserDto> collect = allUsers.stream().map(userMapper::toUserDto).collect(Collectors.toList());
        return new ResponseEntity<>(collect, HttpStatus.OK);
    }

    @GetMapping("/secretadmin")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String adminSecured() {
        return "This is secret ADMIN secured message";
    }


}