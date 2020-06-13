package inter.venture.project.domain.user.controller;

import inter.venture.project.domain.user.dto.UserDto;
import inter.venture.project.domain.user.entity.User;
import inter.venture.project.domain.user.mapper.UserMapper;
import inter.venture.project.domain.user.request.CreateUserRequest;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import inter.venture.project.domain.user.service.UserService;

import javax.validation.Valid;
import javax.xml.bind.ValidationException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping
    public List<UserDto> list(){
        return UserMapper.instance.listOfUsersToListOfUserDto(this.userService.list());
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@RequestBody @Valid CreateUserRequest createUserRequest) throws ValidationException {
        return UserMapper.instance.userToUserDto(this.userService.create(createUserRequest));
    }

//    @GetMapping(value = "/get")
    @RequestMapping("/get/id={id}")
    public UserDto get(@PathVariable(name = "id") Long id) {
        return UserMapper.instance.userToUserDto(this.userService.get(id));
    }

//    @PutMapping(value = "{id}")
    @RequestMapping(value = "/update/id={id}", method = RequestMethod.PUT)
    public UserDto update(@PathVariable Long id, @RequestBody User user) {
        return UserMapper.instance.userToUserDto(this.userService.update(id, user));
    }


    @RequestMapping(value = "/delete/id={id}", method = RequestMethod.DELETE)
    @Cascade(CascadeType.ALL)
    public void delete(@PathVariable Long id) {
        this.userService.delete(id);
    }








}
