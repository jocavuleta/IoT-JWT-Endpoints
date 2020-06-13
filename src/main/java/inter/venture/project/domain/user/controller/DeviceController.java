package inter.venture.project.domain.user.controller;

import inter.venture.project.domain.user.dto.DeviceDto;
import inter.venture.project.domain.user.entity.Device;
import inter.venture.project.domain.user.filter.JwtRequestFilter;
import inter.venture.project.domain.user.mapper.DeviceMapper;
import inter.venture.project.domain.user.request.CreateDeviceRequest;
import inter.venture.project.domain.user.service.DeviceService;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.bind.ValidationException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/devices")
public class DeviceController {

    @Autowired
    DeviceService deviceService;

    @Autowired
    JwtRequestFilter jwtRequestFilter;


    @GetMapping
    public List<DeviceDto> list(@RequestHeader(name="Authorization") String token){
        return DeviceMapper.instance.listOfDevicesToListOfDeviceDto(this.deviceService.listUserDevices(token));
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DeviceDto create(@RequestBody @Valid CreateDeviceRequest createDeviceRequest, @RequestHeader(name="Authorization") String token) throws ValidationException {
        return DeviceMapper.instance.deviceToDeviceDto(this.deviceService.create(createDeviceRequest, token));
    }

//    @GetMapping(value = "/get/{id}")
    @RequestMapping("/get/id={id}")
    public DeviceDto get(@PathVariable(name = "id") Long id) {
        return DeviceMapper.instance.deviceToDeviceDto(this.deviceService.get(id));
    }

    //    @PutMapping(value = "{id}")
    @RequestMapping(value = "/update/id={id}", method = RequestMethod.PUT)
    public DeviceDto update(@PathVariable Long id, @RequestBody Device device) {
        return DeviceMapper.instance.deviceToDeviceDto(this.deviceService.update(id, device));
    }


    @RequestMapping(value = "/delete/id={id}", method = RequestMethod.DELETE)
    @Cascade(CascadeType.ALL)
    public void delete(@PathVariable Long id) {
        this.deviceService.delete(id);
    }

    @RequestMapping(value = "/{search}")
    public DeviceDto anotherList(@PathVariable(value = "search",required = false) String search){
        DeviceDto device = null;
        device = DeviceMapper.instance.deviceToDeviceDto(this.deviceService.findByName(search));
        if (device == null){
            device = DeviceMapper.instance.deviceToDeviceDto(this.deviceService.findByDescription(search));
        }
        return device;
    }
}
