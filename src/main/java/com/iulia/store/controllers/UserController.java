package com.iulia.store.controllers;

import com.iulia.store.dtos.RegisterUserDto;
import com.iulia.store.dtos.UpdateUserDto;
import com.iulia.store.dtos.UserDto;
import com.iulia.store.entities.User;
import com.iulia.store.mappers.UserMapper;
import com.iulia.store.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Set;


@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

//    public UserController(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }

//    @GetMapping
//    public Iterable<User> getAllUsers() {
//        return userRepository.findAll();
//    }

    @GetMapping
    public List<UserDto> getAllUsers(
            @RequestParam(required = false, defaultValue = "", name = "sort") String sort
    ) {
        if (!Set.of("name", "email").contains(sort))
            sort = "name"; // default sort by name

        return userRepository.findAll(Sort.by(sort))
                .stream()
                .map(userMapper::userToUserDto)
//                .map(user -> userMapper.userToUserDto(user)) // la fel ca mai sus
//                .map(user -> new UserDto(user.getId(), user.getName(), user.getEmail()))
                .toList();
    }

//    @GetMapping("/{id}")
//    public User getUserById(@PathVariable Long id) {
//        return userRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
//    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        var userDto = userRepository.findById(id).orElse(null);
        if (userDto == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(userMapper.userToUserDto(userDto));
//            return ResponseEntity.ok(new UserDto(userDto.getId(), userDto.getName(), userDto.getEmail()));
        }
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody RegisterUserDto request,
                              UriComponentsBuilder uriBuilder) {
        var user = userMapper.userDtoToUser(request);
        user = userRepository.save(user);
        var userDto = userMapper.userToUserDto(user);
        var uri = uriBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(userDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable(name="id") Long id,
                              @RequestBody UpdateUserDto request){
        var user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
//        user.setName(request.getName());
//        user.setEmail(request.getEmail());
        userMapper.updateUserFromDto(request, user); // fromDto to existing entity
        user = userRepository.save(user);
        var userDto = userMapper.userToUserDto(user);
        return ResponseEntity.ok(userDto);
    }

    @DeleteMapping("/id")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        var user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        userRepository.delete(user);
        return ResponseEntity.noContent().build();
    }

}

