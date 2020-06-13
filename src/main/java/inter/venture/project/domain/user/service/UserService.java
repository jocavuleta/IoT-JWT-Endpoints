package inter.venture.project.domain.user.service;

import inter.venture.project.domain.user.entity.User;
import inter.venture.project.domain.user.repository.UserRepository;
import inter.venture.project.domain.user.request.CreateUserRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.xml.bind.ValidationException;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    public List<User> list(){
        return userRepository.findAll();
    }

    public User create(CreateUserRequest createUserRequest) throws ValidationException {
        String username = createUserRequest.getUsername();
        if (this.userRepository.existsByUsername(username)){
            throw new IllegalArgumentException("The given username is already taken.");
        }
        String encodedPassword = new BCryptPasswordEncoder().encode(createUserRequest.getPassword());
        String firstName = createUserRequest.getFirstName();
        String lastName = createUserRequest.getLastName();

        return this.userRepository.save(new User(username, encodedPassword, firstName, lastName));
    }


    public User get(Long id) {
        return userRepository.getOne(id);
    }

        public User update(Long id, User user) {
        User existingUser = userRepository.getOne(id);
        //Password was changed, encryption needs to be done before sending back to the database
        if(!existingUser.getPassword().equals(passwordEncoder.encode(user.getPassword()))){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        BeanUtils.copyProperties(user, existingUser, "id");
        return userRepository.saveAndFlush(existingUser);
    }



    public void delete(Long id) {
        //Also need to check for children record before deleting
        userRepository.deleteById(id);
    }




}
