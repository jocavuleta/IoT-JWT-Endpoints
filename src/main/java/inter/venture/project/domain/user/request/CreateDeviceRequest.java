package inter.venture.project.domain.user.request;

import inter.venture.project.domain.user.entity.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CreateDeviceRequest {
    @NotBlank
    private String name;
    private String description;
    private String secret;
    @NotNull
    private long creator;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public long getCreator() {
        return creator;
    }

    public void setCreator(long creator) {
        this.creator = creator;
    }
}
