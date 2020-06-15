package inter.venture.integration.rest;


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

    @Before
    public void setUp() {
        String encodedPassword = new BCryptPasswordEncoder().encode("milos");
        user1 = new User("Milos", encodedPassword, "Milos", "Milosevic");
        userRepository.save(user1);
    }

    @Test
    public void getUser() {
        long id = userRepository.findByUsername(user1.getUsername()).getId();
        String path = "http://localhost:8080/users/get/"+id;
        given()
                .contentType(ContentType.JSON)
                .when()
                .get(path)
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED).extract().response();

        String token = login(user1.getUsername(), "milos");

        //when().request("GET", path).then().statusCode(200);
        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .when()
                .get(path)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("size()", is(3))
                .body("username", Matchers.equalTo(user1.getUsername()))
                .body("firstName", Matchers.equalTo(user1.getFirstName()))
                .body("lastName", Matchers.equalTo(user1.getLastName()));

    }

    @After
    public void tearDown() {
        userRepository.delete(user1);
    }

}
