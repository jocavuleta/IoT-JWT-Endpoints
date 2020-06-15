package inter.venture.unit.rest;

import inter.venture.project.core.exception.Violation;
import inter.venture.project.domain.user.dto.privateDto.DeviceDtoPrivate;
import inter.venture.project.domain.user.dto.privateDto.UserDtoPrivate;
import inter.venture.project.domain.user.dto.publicDto.DeviceDto;
import inter.venture.project.domain.user.dto.publicDto.UserDto;
import inter.venture.project.domain.user.entity.Device;
import inter.venture.project.domain.user.service.DeviceService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DeviceUnitRestTest extends AbstractUnitRestTest {
    @MockBean
    private DeviceService deviceService;

    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void getDeviceList() throws Exception {
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
        device.setCreator(user);
        List<DeviceDto> devices = new ArrayList<>();
        devices.add(device);

        when(deviceService.listUserDevices(any(String.class))).thenReturn(devices);

        mockMvc.perform(get("/devices/")
                .header("Authorization", "token"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].name").exists())
                .andExpect(jsonPath("$.[0].name").isString())
                .andExpect(jsonPath("$.[0].description").exists())
                .andExpect(jsonPath("$.[0].description").isString())
                .andExpect(jsonPath("$.[0].properties").exists())
                .andExpect(jsonPath("$.[0].properties").isMap())
                .andExpect(jsonPath("$.[0].properties.key").isString())
                .andExpect(jsonPath("$.[0].properties.key").value("value"))
                .andReturn();
    }

    @Test
    public void createDevice() throws Exception {

        UserDto userDto = new UserDto();
        userDto.setFirstName("Jovan");
        userDto.setLastName("Jovan");
        userDto.setUsername("Jovan");
        DeviceDtoPrivate deviceDtoPrivate = new DeviceDtoPrivate();
        deviceDtoPrivate.setName("apple");
        deviceDtoPrivate.setDescription("new apple device");
        deviceDtoPrivate.setCreator(userDto);
        deviceDtoPrivate.setSecret("2021 W");

        DeviceDto deviceDto = new DeviceDto();
        deviceDto.setName("new Apple");
        deviceDto.setDescription("new apple device");
        deviceDto.setCreator(userDto);

        when(deviceService.create(any(DeviceDtoPrivate.class),any(String.class))).thenReturn(deviceDto);

        String jsonObject = objectMapper.writeValueAsString(deviceDtoPrivate);

        mockMvc.perform(post("/devices/")
                .header("Authorization", "token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObject))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.name").isString())
                .andExpect(jsonPath("$.description").exists())
                .andExpect(jsonPath("$.description").isString())
                .andExpect(jsonPath("$.creator").exists())
                .andExpect(jsonPath("$.creator").isMap())
                .andExpect(jsonPath("$.properties").doesNotExist())
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
        deviceResponse.setProperties(properties);
        deviceResponse.setCreator(user);

        DeviceDtoPrivate deviceRequest = new DeviceDtoPrivate();
        deviceRequest.setName("iphone");
        deviceRequest.setDescription("black, version X");
        deviceRequest.setProperties(properties);
        deviceRequest.setCreator(user);
        deviceRequest.setSecret("mysecretmessage");

        when(deviceService.update(any(Long.class), any(DeviceDtoPrivate.class))).thenReturn(deviceResponse);

        String jsonObject = objectMapper.writeValueAsString(deviceRequest);

        mockMvc.perform(put("/devices/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObject))
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

    @Test
    public void deleteDevice() throws Exception {

        when(deviceService.delete(any(Long.class))).thenReturn(true);

        mockMvc.perform(delete("/devices/delete/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$").isString())
                .andReturn();
    }
}
