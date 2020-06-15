package inter.venture.project.domain.user.mapper;

import inter.venture.project.domain.user.dto.publicDto.DeviceDto;
import inter.venture.project.domain.user.dto.privateDto.DeviceDtoPrivate;
import inter.venture.project.domain.user.entity.Device;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper
public interface DeviceMapper {

    DeviceMapper instance = Mappers.getMapper( DeviceMapper.class );

    DeviceDto deviceToDeviceDto(Device device);

    Device deviceDtoToDevice(DeviceDto deviceDto);

    Device deviceDtoPrivateToDevice(DeviceDtoPrivate deviceDtoPrivate);

    List<DeviceDto> listOfDevicesToListOfDeviceDto(List<Device> devices);

    default String propertiesMapToPropertiesString(Map<String, Object> properties) {
        return properties.keySet().stream()
                .map(key -> key + ":" + properties.get(key))
                .collect(Collectors.joining(", ", "", ""));
    }

    default Map<String, Object> propertiesStringToPropertiesMap(String properties) {
        return Arrays.stream(properties.split(","))
                .map(entry -> entry.trim().split(":"))
                .collect(Collectors.toMap(entry -> entry[0], entry -> entry[1]));
    }
}
