package inter.venture.project.domain.user.mapper;

import inter.venture.project.domain.user.dto.DeviceDto;
import inter.venture.project.domain.user.dto.UserDto;
import inter.venture.project.domain.user.entity.Device;
import inter.venture.project.domain.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DeviceMapper {
    DeviceMapper instance = Mappers.getMapper( DeviceMapper.class );

    DeviceDto deviceToDeviceDto(Device device);

    List<DeviceDto> listOfDevicesToListOfDeviceDto(List<Device> devices);
}
