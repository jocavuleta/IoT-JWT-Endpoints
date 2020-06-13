package inter.venture.project.domain.user.service;

import inter.venture.project.domain.user.JwtTokenUtil;
import inter.venture.project.domain.user.entity.Device;
import inter.venture.project.domain.user.entity.User;
import inter.venture.project.domain.user.filter.JwtRequestFilter;
import inter.venture.project.domain.user.repository.DeviceRepository;
import inter.venture.project.domain.user.repository.UserRepository;
import inter.venture.project.domain.user.request.CreateDeviceRequest;
import inter.venture.project.domain.user.request.CreateUserRequest;
import inter.venture.project.domain.user.response.JwtResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.xml.bind.ValidationException;
import java.util.List;

@Service
public class DeviceService {

    @Autowired
    DeviceRepository deviceRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    UserService userService;




    public List<Device> list(){
        return deviceRepository.findAll();
    }

    public List<Device> listUserDevices(String token){
        System.out.println(token);
        token = token.substring(token.indexOf(" ") + 1);
        System.out.println(token);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        System.out.println(username);
        return deviceRepository.listUserDevices(username);
    }

    public Device create(CreateDeviceRequest createDeviceRequest, String token) {
        String name = createDeviceRequest.getName();
        String description = createDeviceRequest.getDescription();
        String secret = createDeviceRequest.getSecret();
        System.out.println(token);
        token = token.substring(token.indexOf(" ") + 1);
        System.out.println(token);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User creator = userRepository.findByUsername(username);

        return this.deviceRepository.save(new Device(name, description, secret, creator));
    }


    public Device get(Long id) {
        return deviceRepository.getOne(id);
    }

    public Device update(Long id, Device device) {
        Device existingDevice = deviceRepository.getOne(id);
        BeanUtils.copyProperties(device, existingDevice, "id");
        return deviceRepository.saveAndFlush(existingDevice);
    }



    public void delete(Long id) {
        //Also need to check for children record before deleting
        deviceRepository.deleteById(id);
    }

    public Device findByName(String name) {
        return deviceRepository.findByName(name);
    }

    public Device findByDescription(String description) {
        return deviceRepository.findByDescription(description);
    }
}
