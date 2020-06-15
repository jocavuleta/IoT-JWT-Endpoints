package inter.venture.unit.rest;

import inter.venture.project.domain.user.dto.publicDto.UserDto;
import inter.venture.project.domain.user.dto.privateDto.UserDtoPrivate;
import inter.venture.project.domain.user.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserRestUnitTest extends AbstractUnitRestTest {

    @MockBean
    private UserService userService;

    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void getUser() throws Exception {
        UserDto user = new UserDto();
        user.setUsername("Jovan");
        user.setFirstName("Jovan");
        user.setLastName("Jovan");

        when(userService.get(any(Long.class))).thenReturn(user);

        mockMvc.perform(get("/users/get/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").exists())
                .andExpect(jsonPath("$.username").isString())
                .andExpect(jsonPath("$.firstName").exists())
                .andExpect(jsonPath("$.firstName").isString())
                .andExpect(jsonPath("$.lastName").exists())
                .andExpect(jsonPath("$.lastName").isString())
                .andReturn();
    }

    @Test
    public void postUser() throws Exception {
        UserDtoPrivate userRequest = new UserDtoPrivate();
        userRequest.setUsername("Jovan");
        userRequest.setFirstName("Jovan");
        userRequest.setLastName("Jovan");
        userRequest.setPassword("Jovan");

        UserDto userResponse = new UserDto();
        userResponse.setUsername("Jovan");
        userResponse.setFirstName("Jovan");
        userResponse.setLastName("Jovan");

        when(userService.create(userRequest)).thenReturn(userResponse);

        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").exists())
                .andExpect(jsonPath("$.username").isString())
                .andExpect(jsonPath("$.firstName").exists())
                .andExpect(jsonPath("$.firstName").isString())
                .andExpect(jsonPath("$.lastName").exists())
                .andExpect(jsonPath("$.lastName").isString())
                .andReturn();
    }
}
