package inter.venture.project.domain.user.dto.publicDto;

import inter.venture.project.core.validation.constraints.UniqueField;
import inter.venture.project.domain.user.entity.User;

public class UserDto {
    @UniqueField(entity = User.class, fieldName = "username", message = "User with given username already exists")
    private String username;
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
}
