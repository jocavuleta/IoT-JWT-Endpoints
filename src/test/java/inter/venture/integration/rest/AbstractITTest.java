package inter.venture.integration.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import inter.venture.project.InterVentureProject;
import inter.venture.project.domain.user.request.JwtRequest;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = DEFINED_PORT, classes = InterVentureProject.class)
public abstract class AbstractITTest {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @Value("${server.port:8080}")
    private int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }


    protected String login(String username, String password) {
        JwtRequest jwtRequest = new JwtRequest(username, password);
        HttpEntity entity = new HttpEntity<>(jwtRequest);
        String loginBot = "/authenticate";
        ResponseEntity<String> responseEntity = restTemplate.exchange(RestAssured.baseURI+":"+RestAssured.port  + loginBot, HttpMethod.POST, entity, String.class);
        return responseEntity.getBody().split("\"")[3];
    }
}
