package inter.venture.project.domain.user.request;

import inter.venture.project.domain.user.entity.User;
import inter.venture.project.core.validation.constraints.UniqueField;

import javax.validation.constraints.NotBlank;

public class CreateUserRequest {

    @NotBlank
    @UniqueField(entity = User.class, fieldName = "username", message = "User with given username already exists")
    private String username;

    @NotBlank
    private String password;

    private String firstName;
    private String lastName;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
