package inter.venture.project.domain.user.request;

import javax.validation.constraints.NotBlank;

public class JwtRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    public JwtRequest(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
