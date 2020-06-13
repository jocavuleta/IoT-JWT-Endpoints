package inter.venture.project.domain.user.facade.auth;

import inter.venture.project.domain.user.entity.User;

public interface IAuthFacade {

    Long getId();

    User getUser();

    void setUser(User user);

}
