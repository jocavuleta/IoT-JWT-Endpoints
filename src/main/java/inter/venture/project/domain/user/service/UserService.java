package inter.venture.project.domain.user.service;

import inter.venture.project.domain.user.dto.privateDto.UserDtoPrivate;
import inter.venture.project.domain.user.dto.publicDto.UserDto;
import inter.venture.project.domain.user.entity.Device;
import inter.venture.project.domain.user.entity.User;
import inter.venture.project.domain.user.mapper.UserMapper;
import inter.venture.project.domain.user.repository.DeviceRepository;
import inter.venture.project.domain.user.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.xml.bind.ValidationException;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    public List<UserDto> list(){
        return UserMapper.instance.listOfUsersToListOfUserDto(userRepository.findAll());
    }

    public UserDto create(UserDtoPrivate userDtoPrivate) throws ValidationException {
        User user = UserMapper.instance.userDtoPrivateToUser(userDtoPrivate);

        if (this.userRepository.existsByUsername(user.getUsername())){
            throw new IllegalArgumentException("The given username is already taken.");
        }
        //Password is given in plain text, but we want to bcrypt it and then sent it to the DB encrypted
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        return UserMapper.instance.userToUserDto(this.userRepository.save(user));
    }


    public UserDto get(Long id) {
        return UserMapper.instance.userToUserDto(userRepository.getOne(id));
    }

    public UserDto update(Long id, UserDtoPrivate userDtoPrivate) {
        User existingUser = userRepository.getOne(id);
        User user = UserMapper.instance.userDtoPrivateToUser(userDtoPrivate);
        //Password was changed, encryption needs to be done before sending back to the database
        //Checking if the passwords are the same, if not then change it and bcrypt it
        if(!existingUser.getPassword().equals(passwordEncoder.encode(user.getPassword()))){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        //Compares the User from the DB and the existing one and updates it accordingly
        BeanUtils.copyProperties(user, existingUser, "id");
        return UserMapper.instance.userToUserDto(userRepository.saveAndFlush(existingUser));
    }


    public boolean delete(Long id) {
        //Also need to check for children record before deleting
        if(userRepository.findById(id).isPresent()) {
            User user = userRepository.findById(id).get();

            //Delete all devices for the user that we are deleting
            List<Device> devices = deviceRepository.listUserDevices(user.getUsername());
            if (!devices.isEmpty()) {
                for (Device device : devices) {
                    deviceRepository.delete(device);
                }
            }
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }




}
