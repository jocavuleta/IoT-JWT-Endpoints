package inter.venture.integration.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import inter.venture.project.domain.user.dto.privateDto.DeviceDtoPrivate;
import inter.venture.project.domain.user.dto.publicDto.UserDto;
import inter.venture.project.domain.user.entity.Device;
import inter.venture.project.domain.user.entity.User;
import inter.venture.project.domain.user.mapper.DeviceMapper;
import inter.venture.project.domain.user.mapper.UserMapper;
import inter.venture.project.domain.user.repository.DeviceRepository;
import inter.venture.project.domain.user.repository.UserRepository;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

public class DeviceRestTest extends AbstractITTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DeviceRepository deviceRepository;

    private User user1;
    private User user2;
    private User user3;
    private String sifra = "sifra";
    private Device device1;

    @Before
    public void setUp() {
        super.setUp();
        String encodedPassword = new BCryptPasswordEncoder().encode(sifra);
        user1 = new User("jovan", encodedPassword, "Jovan", "Vuleta");
        user2 = new User("joca", encodedPassword, "J", "V");
        user3 = new User("jocavuleta", encodedPassword, "Joca", "Vuleta");
        device1 = new Device("iphone", "new phone for the future", "shhhh");
        user1.setDevices(new ArrayList<>());
        user1.getDevices().add(device1);
        device1.setCreator(user1);
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        deviceRepository.save(device1);
    }

    @Test
    public void getDeviceList() {
        String path = "/devices/";

        given()
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .get(path)
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED).extract().response();

        String token = login(user1.getUsername(), sifra);

        given()
                .contentType(ContentType.JSON)
                .log().all()
                .header("Authorization", "Bearer " + token)
                .when()
                .get(path)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .log().all()
                .body("size()", is(1))
                .body("get(0).name", Matchers.equalTo(device1.getName()))
                .body("get(0).description", Matchers.equalTo(device1.getDescription()))
                .body("get(0).properties", Matchers.equalTo(device1.getProperties()))
                .body("get(0).creator.username", Matchers.equalTo(device1.getCreator().getUsername()));
    }

    @Test
    public void createDevice() throws JsonProcessingException {
        String path = "devices/";

        UserDto creator = UserMapper.instance.userToUserDto(user1);

        DeviceDtoPrivate newDevice = new DeviceDtoPrivate();
        newDevice.setName("macbook");
        newDevice.setDescription("Apple Cool Product");
        newDevice.setSecret("secret");
        Map<String, Object> properties = new HashMap<>();
        properties.put("screen", "1080 HD quality");
        newDevice.setProperties(properties);
        newDevice.setCreator(creator);

        String jsonObject = objectMapper.writeValueAsString(newDevice);
        given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(jsonObject)
                .when()
                .post(path)
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED);

        String token = login(user1.getUsername(), sifra);

        given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(jsonObject)
                .when()
                .post(path)
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .log().all()
                .body("size()", is(4))
                .body("name", Matchers.equalTo(newDevice.getName()))
                .body("description", Matchers.equalTo(newDevice.getDescription()))
                .body("properties.screen", Matchers.equalTo(newDevice.getProperties().get("screen")))
                .body("creator.username", Matchers.equalTo(newDevice.getCreator().getUsername()));
    }


    @Test
    public void updateDevice() throws JsonProcessingException {
        long id = deviceRepository.findAll().get(0).getId();
        String path = "devices/update/" + id;

        DeviceDtoPrivate deviceDtoPrivate = DeviceMapper.instance.deviceToDeviceDtoPrivate(device1);
        Map<String, Object> properties = new HashMap<>();
        properties.put("screen", "1080 HD quality");
        deviceDtoPrivate.setProperties(properties);

        String jsonObject = objectMapper.writeValueAsString(deviceDtoPrivate);
        given()
                .contentType(ContentType.JSON)
                .body(jsonObject)
                .when()
                .put(path)
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED).extract().response();

        String token = login(user1.getUsername(), sifra);

        given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(jsonObject)
                .when()
                .put(path)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .log().all()
                .body("size()", is(4))
                .body("name", Matchers.equalTo(deviceDtoPrivate.getName()))
                .body("description", Matchers.equalTo(deviceDtoPrivate.getDescription()))
                .body("properties.screen", Matchers.equalTo(deviceDtoPrivate.getProperties().get("screen")))
                .body("creator.username", Matchers.equalTo(deviceDtoPrivate.getCreator().getUsername()));
    }

    @Test
    public void deleteDevice() {
        long id = deviceRepository.findAll().get(0).getId();
        String path = "devices/delete/" + id;

        given()
                .when()
                .delete(path)
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED);

        String token = login(user1.getUsername(), sifra);

        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .delete(path)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .log().all()
                .contentType(ContentType.TEXT)
                .body(Matchers.equalTo("Device Deleted!"));
    }

    @Test
    public void searchDevice() {
        String path = "/devices/" + device1.getName();

        given()
                .contentType(ContentType.JSON)
                .when()
                .get(path)
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED).extract().response();

        String token = login(user1.getUsername(), sifra);

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .when()
                .get(path)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .log().all()
                .body("size()", is(4))
                .body("name", Matchers.equalTo(device1.getName()))
                .body("description", Matchers.equalTo(device1.getDescription()))
                .body("properties", Matchers.equalTo(device1.getProperties()))
                .body("creator.username", Matchers.equalTo(device1.getCreator().getUsername()));

        path = "/devices/" + device1.getDescription();
        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .when()
                .get(path)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .log().all()
                .body("size()", is(4))
                .body("name", Matchers.equalTo(device1.getName()))
                .body("description", Matchers.equalTo(device1.getDescription()))
                .body("properties", Matchers.equalTo(device1.getProperties()))
                .body("creator.username", Matchers.equalTo(device1.getCreator().getUsername()));
    }

    @After
    public void tearDown() {
        deviceRepository.deleteAll();
        userRepository.deleteAll();
    }

}
