package inter.venture.unit.rest;

import inter.venture.project.core.exception.Violation;
import inter.venture.project.domain.user.dto.privateDto.DeviceDtoPrivate;
import inter.venture.project.domain.user.dto.publicDto.DeviceDto;
import inter.venture.project.domain.user.dto.publicDto.UserDto;
import inter.venture.project.domain.user.service.DeviceService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DeviceUnitRestTest extends AbstractUnitRestTest{
    @MockBean
    private DeviceService deviceService;

    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void getDevice() throws Exception {
        UserDto user = new UserDto();
        user.setFirstName("Jovan");
        user.setUsername("jovan");
        user.setLastName("Jovanovic");
        DeviceDto device = new DeviceDto();
        device.setName("iphone");
        device.setDescription("black, version X");
        Map<String, Object> propeties = new HashMap<>();
        propeties.put("key", "value");
        device.setProperties(propeties);
        when(deviceService.get(any(Long.class))).thenReturn(device);

        mockMvc.perform(get("/devices/get/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.name").isString())
                .andExpect(jsonPath("$.description").exists())
                .andExpect(jsonPath("$.description").isString())
                .andExpect(jsonPath("$.properties").exists())
                .andExpect(jsonPath("$.properties").isMap())
                .andExpect(jsonPath("$.properties.key").isString())
                .andExpect(jsonPath("$.properties.key").value("value"))
                .andReturn();
    }

    @Test
    public void updateDevice() throws Exception, Violation {
        UserDto user = new UserDto();
        user.setFirstName("Jovan");
        user.setUsername("jovan");
        user.setLastName("Jovanovic");

        DeviceDto deviceResponse = new DeviceDto();
        deviceResponse.setName("iphone");
        deviceResponse.setDescription("black, version X");
        Map<String, Object> properties = new HashMap<>();
        properties.put("color", "black");
//        properties.put("width", "25cm");
        deviceResponse.setProperties(properties);
        deviceResponse.setCreator(user);

        DeviceDtoPrivate deviceRequest = new DeviceDtoPrivate();
        deviceRequest.setName("iphone");
        deviceRequest.setDescription("black, version X");
        deviceRequest.setProperties(properties);
        deviceRequest.setCreator(user);
        deviceRequest.setSecret("mysecretmessage");

//        Device existing = DeviceMapper.instance.deviceDtoToDevice(deviceService.get((1L)));

        when(deviceService.update(1L, deviceRequest)).thenReturn(deviceResponse);

        mockMvc.perform(put("/devices/update/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.name").isString())
                .andExpect(jsonPath("$.description").exists())
                .andExpect(jsonPath("$.description").isString())
                .andExpect(jsonPath("$.creator.username").exists())
                .andExpect(jsonPath("$.creator.username").isString())
                .andExpect(jsonPath("$.creator.firstName").exists())
                .andExpect(jsonPath("$.creator.firstName").isString())
                .andExpect(jsonPath("$.creator.lastName").exists())
                .andExpect(jsonPath("$.creator.lastName").isString())
                .andExpect(jsonPath("$.properties").exists())
                .andExpect(jsonPath("$.properties").isMap())
                .andExpect(jsonPath("$.properties.color").isString())
                .andExpect(jsonPath("$.properties.color").value("black"))
                .andReturn();
    }
}
