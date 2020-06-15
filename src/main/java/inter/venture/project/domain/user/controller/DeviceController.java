package inter.venture.project.domain.user.controller;

import inter.venture.project.core.exception.Violation;
import inter.venture.project.domain.user.dto.publicDto.DeviceDto;
import inter.venture.project.domain.user.dto.privateDto.DeviceDtoPrivate;
import inter.venture.project.domain.user.filter.JwtRequestFilter;
import inter.venture.project.domain.user.repository.DeviceRepository;
import inter.venture.project.domain.user.service.DeviceService;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

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

    @Autowired
    DeviceRepository deviceRepository;


    @GetMapping
    public List<DeviceDto> list(@RequestHeader(name="Authorization") String token){
        return this.deviceService.listUserDevices(token);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DeviceDto create(@RequestBody @Valid DeviceDtoPrivate deviceDtoPrivate, @RequestHeader(name="Authorization") String token) throws ValidationException {
        return this.deviceService.create(deviceDtoPrivate, token);
    }

//    @GetMapping("/get/{id}")
//    public DeviceDto get(@PathVariable(name = "id") Long id) {
//        return this.deviceService.get(id);
//    }

    @PutMapping(value = "/update/{id}")
    public DeviceDto update(@PathVariable Long id, @RequestBody DeviceDtoPrivate deviceDtoPrivate) throws NoHandlerFoundException, Violation {
        return this.deviceService.update(id, deviceDtoPrivate);
    }


    @DeleteMapping(value = "/delete/{id}")
    @Cascade(CascadeType.ALL)
    public String delete(@PathVariable Long id) {
        this.deviceService.delete(id);
        return "Device Deleted!";
    }

    @GetMapping(value = "/{search}")
    public DeviceDto searchByParameter(@PathVariable(value = "search",required = false) String search){
        DeviceDto device = null;
        device = this.deviceService.findByName(search);
        if (device == null) {
            device = this.deviceService.findByDescription(search);
        }
        if(device == null){
            device = this.deviceService.findByProperties(search);
        }

        return device;
    }
}
