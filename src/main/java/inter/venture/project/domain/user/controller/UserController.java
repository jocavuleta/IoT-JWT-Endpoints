package inter.venture.project.domain.user.controller;

import inter.venture.project.domain.user.dto.publicDto.UserDto;
import inter.venture.project.domain.user.dto.privateDto.UserDtoPrivate;
import inter.venture.project.domain.user.entity.User;
import inter.venture.project.domain.user.service.UserService;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
        return this.userService.list();
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@RequestBody @Valid UserDtoPrivate userDto) throws ValidationException {
        return this.userService.create(userDto);
    }

    @GetMapping("/get/{id}")
    public UserDto get(@PathVariable(name = "id") Long id) {
        return this.userService.get(id);
    }

    @PutMapping(value = "/update/{id}")
    public UserDto update(@PathVariable Long id, @RequestBody UserDtoPrivate user) {
        return this.userService.update(id, user);
    }


    //All children all deleted as well
    @DeleteMapping(value = "/delete/{id}")
    @Cascade(CascadeType.ALL)
    public String delete(@PathVariable Long id) {
        this.userService.delete(id);
        return "User Deleted!";
    }

}
