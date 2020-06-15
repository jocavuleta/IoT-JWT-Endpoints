package inter.venture.project.domain.user.mapper;

import inter.venture.project.domain.user.dto.privateDto.DeviceDtoPrivate;
import inter.venture.project.domain.user.dto.publicDto.DeviceDto;
import inter.venture.project.domain.user.dto.publicDto.UserDto;
import inter.venture.project.domain.user.entity.Device;
import inter.venture.project.domain.user.entity.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-06-15T22:15:25+0200",
    comments = "version: 1.3.0.Final, compiler: javac, environment: Java 13 (Oracle Corporation)"
)
public class DeviceMapperImpl implements DeviceMapper {

    @Override
    public DeviceDto deviceToDeviceDto(Device device) {
        if ( device == null ) {
            return null;
        }

        DeviceDto deviceDto = new DeviceDto();

        deviceDto.setName( device.getName() );
        deviceDto.setDescription( device.getDescription() );
        deviceDto.setCreator( userToUserDto( device.getCreator() ) );
        deviceDto.setProperties( propertiesStringToPropertiesMap( device.getProperties() ) );

        return deviceDto;
    }

    @Override
    public Device deviceDtoToDevice(DeviceDto deviceDto) {
        if ( deviceDto == null ) {
            return null;
        }

        Device device = new Device();

        device.setName( deviceDto.getName() );
        device.setDescription( deviceDto.getDescription() );
        device.setCreator( userDtoToUser( deviceDto.getCreator() ) );
        device.setProperties( propertiesMapToPropertiesString( deviceDto.getProperties() ) );

        return device;
    }

    @Override
    public Device deviceDtoPrivateToDevice(DeviceDtoPrivate deviceDtoPrivate) {
        if ( deviceDtoPrivate == null ) {
            return null;
        }

        Device device = new Device();

        device.setName( deviceDtoPrivate.getName() );
        device.setDescription( deviceDtoPrivate.getDescription() );
        device.setSecret( deviceDtoPrivate.getSecret() );
        device.setCreator( userDtoToUser( deviceDtoPrivate.getCreator() ) );
        device.setProperties( propertiesMapToPropertiesString( deviceDtoPrivate.getProperties() ) );

        return device;
    }

    @Override
    public DeviceDtoPrivate deviceToDeviceDtoPrivate(Device device) {
        if ( device == null ) {
            return null;
        }

        DeviceDtoPrivate deviceDtoPrivate = new DeviceDtoPrivate();

        deviceDtoPrivate.setName( device.getName() );
        deviceDtoPrivate.setDescription( device.getDescription() );
        deviceDtoPrivate.setCreator( userToUserDto( device.getCreator() ) );
        deviceDtoPrivate.setProperties( propertiesStringToPropertiesMap( device.getProperties() ) );
        deviceDtoPrivate.setSecret( device.getSecret() );

        return deviceDtoPrivate;
    }

    @Override
    public List<DeviceDto> listOfDevicesToListOfDeviceDto(List<Device> devices) {
        if ( devices == null ) {
            return null;
        }

        List<DeviceDto> list = new ArrayList<DeviceDto>( devices.size() );
        for ( Device device : devices ) {
            list.add( deviceToDeviceDto( device ) );
        }

        return list;
    }

    protected UserDto userToUserDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setUsername( user.getUsername() );
        userDto.setFirstName( user.getFirstName() );
        userDto.setLastName( user.getLastName() );

        return userDto;
    }

    protected User userDtoToUser(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        User user = new User();

        user.setUsername( userDto.getUsername() );
        user.setFirstName( userDto.getFirstName() );
        user.setLastName( userDto.getLastName() );

        return user;
    }
}
