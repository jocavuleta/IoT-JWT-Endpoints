package inter.venture.project.domain.user.dto.privateDto;


import inter.venture.project.core.validation.constraints.UniqueField;
import inter.venture.project.domain.user.entity.User;

public class UserDtoPrivate {
    @UniqueField(entity = User.class, fieldName = "username", message = "User with given username already exists")
    private String username;
    private String password;
    private String firstName;
    private String lastName;

    public String getUsername() {
        return username;
    }

    public void setUsername(String email) {
        this.username = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

