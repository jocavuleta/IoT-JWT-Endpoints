package inter.venture.project.domain.user.mapper;

import inter.venture.project.domain.user.dto.DeviceDto;
import inter.venture.project.domain.user.entity.Device;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-06-13T16:58:14+0200",
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
        deviceDto.setCreator( device.getCreator() );

        return deviceDto;
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
}
