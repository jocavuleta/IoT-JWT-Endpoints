package inter.venture.project.domain.user.facade.auth;

import inter.venture.project.domain.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import inter.venture.project.domain.user.repository.UserRepository;
import javax.servlet.http.HttpServletRequest;

@Component
public class AuthFacade implements IAuthFacade {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HttpServletRequest request;

    @Override
    public Long getId() {
        return this.getUser().getId();
    }
    
    public void setUser(User user) {
        request.setAttribute("user", user);
    }

    @Override
    public User getUser() {
        User user = (User) this.request.getAttribute("user");
        if (user == null) {
            var username = SecurityContextHolder.getContext().getAuthentication().getName();
            user = this.userRepository.findByUsername(username);
            this.setUser(this.userRepository.findByUsername(username));
        }

        return user;
    }


}
