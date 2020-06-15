package inter.venture.integration.rest;


import com.fasterxml.jackson.core.JsonProcessingException;
import inter.venture.project.domain.user.dto.privateDto.UserDtoPrivate;
import inter.venture.project.domain.user.entity.User;
import inter.venture.project.domain.user.repository.UserRepository;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

public class UserRestTest extends AbstractITTest {

    @Autowired
    private UserRepository userRepository;

    private User user1;
    private User user2;
    private User user3;
    private String sifra = "sifra";

    @Before
    public void setUp() {
        super.setUp();
        String encodedPassword = new BCryptPasswordEncoder().encode(sifra);
        user1 = new User("jovan", encodedPassword, "Jovan", "Vuleta");
        user2 = new User("joca", encodedPassword, "J", "V");
        user3 = new User("jocavuleta", encodedPassword, "Joca", "Vuleta");
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
    }

    @Test
    public void getUserList() {
        String path = "/users/";

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
                .body("size()", is(3))
                .body("get(0).username", Matchers.equalTo(user1.getUsername()))
                .body("get(0).firstName", Matchers.equalTo(user1.getFirstName()))
                .body("get(0).lastName", Matchers.equalTo(user1.getLastName()))
                .body("get(1).username", Matchers.equalTo(user2.getUsername()))
                .body("get(1).firstName", Matchers.equalTo(user2.getFirstName()))
                .body("get(1).lastName", Matchers.equalTo(user2.getLastName()))
                .body("get(2).username", Matchers.equalTo(user3.getUsername()))
                .body("get(2).firstName", Matchers.equalTo(user3.getFirstName()))
                .body("get(2).lastName", Matchers.equalTo(user3.getLastName()));
    }

    @Test
    public void createUser() throws JsonProcessingException {
        String path = "users/";

        UserDtoPrivate newUser = new UserDtoPrivate();
        newUser.setUsername("jova");
        newUser.setFirstName("JOVAN");
        newUser.setLastName("VULETA");
        newUser.setPassword("joca");

        String jsonObject = objectMapper.writeValueAsString(newUser);
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
                .body("size()", is(3))
                .body("username", Matchers.equalTo(newUser.getUsername()))
                .body("firstName", Matchers.equalTo(newUser.getFirstName()))
                .body("lastName", Matchers.equalTo(newUser.getLastName()));
    }

    @Test
    public void getUser() {
        long id = userRepository.findByUsername(user1.getUsername()).getId();
        String path = "/users/get/" + id;

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
                .body("size()", is(3))
                .body("username", Matchers.equalTo(user1.getUsername()))
                .body("firstName", Matchers.equalTo(user1.getFirstName()))
                .body("lastName", Matchers.equalTo(user1.getLastName()));
    }

    @Test
    public void updateUser() throws JsonProcessingException {
        long id = userRepository.findByUsername(user1.getUsername()).getId();
        String path = "users/update/" + id;

        user1.setFirstName("JOVAN");
        user1.setLastName("VULETA");

        String jsonObject = objectMapper.writeValueAsString(user1);
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
                .body("size()", is(3))
                .body("username", Matchers.equalTo(user1.getUsername()))
                .body("firstName", Matchers.equalTo(user1.getFirstName()))
                .body("lastName", Matchers.equalTo(user1.getLastName()));
    }

    @Test
    public void deleteUser() {
        long id = userRepository.findByUsername(user1.getUsername()).getId();
        String path = "users/delete/" + id;

        given()
                .when()
                .delete(path)
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED).extract().response();

        String token = login(user1.getUsername(), sifra);

        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .delete(path)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .log().all()
                .contentType(ContentType.TEXT)
                .body(Matchers.equalTo("User Deleted!"));
    }

    @After
    public void tearDown() {
        userRepository.deleteAll();
    }

}
