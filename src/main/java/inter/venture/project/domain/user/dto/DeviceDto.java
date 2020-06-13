package inter.venture.project.domain.user.dto;

import inter.venture.project.domain.user.entity.User;

public class DeviceDto {
    private String name;
    private String description;
    private User creator;

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

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }
}
