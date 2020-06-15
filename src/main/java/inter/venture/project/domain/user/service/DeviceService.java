package inter.venture.project.domain.user.service;

import inter.venture.project.core.exception.Violation;
import inter.venture.project.domain.user.JwtTokenUtil;
import inter.venture.project.domain.user.dto.publicDto.DeviceDto;
import inter.venture.project.domain.user.dto.privateDto.DeviceDtoPrivate;
import inter.venture.project.domain.user.entity.Device;
import inter.venture.project.domain.user.entity.User;
import inter.venture.project.domain.user.mapper.DeviceMapper;
import inter.venture.project.domain.user.repository.DeviceRepository;
import inter.venture.project.domain.user.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.NoHandlerFoundException;

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



    public List<DeviceDto> list(){
        return DeviceMapper.instance.listOfDevicesToListOfDeviceDto(deviceRepository.findAll());
    }

    public List<DeviceDto> listUserDevices(String token){
        //Extracting the token from request header
        token = token.substring(token.indexOf(" ") + 1);
        //Getting the username for the corresponding token
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = userRepository.findByUsername(username);
        //Listing only user devices
        return DeviceMapper.instance.listOfDevicesToListOfDeviceDto(deviceRepository.listUserDevices(username));
    }

    public DeviceDto create(DeviceDtoPrivate deviceDtoPrivate, String token) {
        //Getting the object Device from DTO
        Device device = DeviceMapper.instance.deviceDtoPrivateToDevice(deviceDtoPrivate);
        //Extracting the token from request header
        token = token.substring(token.indexOf(" ") + 1);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        //Getting the creator based on his username that we got from the token in the request header
        User creator = userRepository.findByUsername(username);
        device.setCreator(creator);

        return DeviceMapper.instance.deviceToDeviceDto(this.deviceRepository.save(device));
    }


    public DeviceDto get(Long id) {
        return DeviceMapper.instance.deviceToDeviceDto(deviceRepository.getOne(id));
    }

    public DeviceDto update(Long id, DeviceDtoPrivate deviceDtoPrivate) throws NoHandlerFoundException, Violation {
        Device device = DeviceMapper.instance.deviceDtoPrivateToDevice(deviceDtoPrivate);
//        DeviceDto existingDevice = get(id);
//        Device deviceExisting = DeviceMapper.instance.deviceDtoToDevice(existingDevice);

//        Device deviceExisting = deviceRepository.getOne(id);
        if(deviceRepository.findById(id).isPresent()) {
            Device deviceExisting = deviceRepository.findById(id).get();

            String name = deviceExisting.getName();
            String description = deviceExisting.getDescription();
            String secret = deviceExisting.getSecret();
            String properties = deviceExisting.getProperties();

            String username = deviceExisting.getCreator().getUsername();
            User creator = userRepository.findByUsername(username);
            //Compares the Device from the DB and the existing one and updates it accordingly
            BeanUtils.copyProperties(device, deviceExisting, "id");
            deviceExisting.setCreator(creator);

            if (device.getName() == null) {
                deviceExisting.setName(name);
            }
            if (device.getDescription() == null) {
                deviceExisting.setDescription(description);
            }
            if (device.getSecret() == null) {
                deviceExisting.setSecret(secret);
            }
            if (device.getProperties() == null) {
                deviceExisting.setProperties(properties);
            }
            return DeviceMapper.instance.deviceToDeviceDto(deviceRepository.saveAndFlush(deviceExisting));
        }else{
//            throw  new NoHandlerFoundException("No mentioned ID in the Database", "/update/id", HttpHeaders.EMPTY);
            throw new Violation("id", "No given ID found in db");
        }
    }



    public void delete(Long id) {
        //Also need to check for children record before deleting
        deviceRepository.deleteById(id);
    }

    public DeviceDto findByName(String name) {
        return DeviceMapper.instance.deviceToDeviceDto(deviceRepository.findByName(name));
    }

    public DeviceDto findByDescription(String description) {
        return DeviceMapper.instance.deviceToDeviceDto(deviceRepository.findByDescription(description));
    }

    public DeviceDto findByProperties(String properties) {
        return DeviceMapper.instance.deviceToDeviceDto(deviceRepository.findByProperties(properties));
    }
}
