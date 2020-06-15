package inter.venture.unit.rest;

import inter.venture.project.domain.user.dto.publicDto.UserDto;
import inter.venture.project.domain.user.dto.privateDto.UserDtoPrivate;
import inter.venture.project.domain.user.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserUnitRestTest extends AbstractUnitRestTest {

    @MockBean
    private UserService userService;

    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void getListUsers() throws Exception {
        UserDto user = new UserDto();
        user.setUsername("Jovan");
        user.setFirstName("Jovan");
        user.setLastName("Jovan");
        List<UserDto> users = new ArrayList<>();
        users.add(user);

        when(userService.list()).thenReturn(users);

        mockMvc.perform(get("/users/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].username").exists())
                .andExpect(jsonPath("$.[0].username").isString())
                .andExpect(jsonPath("$.[0].firstName").exists())
                .andExpect(jsonPath("$.[0].firstName").isString())
                .andExpect(jsonPath("$.[0].lastName").exists())
                .andExpect(jsonPath("$.[0].lastName").isString())
                .andReturn();
    }

    @Test
    public void createUser() throws Exception {
        UserDtoPrivate userRequest = new UserDtoPrivate();
        userRequest.setUsername("Jovan");
        userRequest.setFirstName("Jovan");
        userRequest.setLastName("Jovan");
        userRequest.setPassword("Jovan");

        UserDto userResponse = new UserDto();
        userResponse.setUsername("Jovan");
        userResponse.setFirstName("Jovan");
        userResponse.setLastName("Jovan");

        when(userService.create(any(UserDtoPrivate.class))).thenReturn(userResponse);

        String jsonObject = objectMapper.writeValueAsString(userRequest);

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObject))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").exists())
                .andExpect(jsonPath("$.username").isString())
                .andExpect(jsonPath("$.firstName").exists())
                .andExpect(jsonPath("$.firstName").isString())
                .andExpect(jsonPath("$.lastName").exists())
                .andExpect(jsonPath("$.lastName").isString())
                .andReturn();
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
    public void updateUser() throws Exception {
        UserDtoPrivate userRequest = new UserDtoPrivate();
        userRequest.setUsername("Jovan");
        userRequest.setFirstName("Jovan");
        userRequest.setLastName("Jovan");
        userRequest.setPassword("Jovan");

        UserDto userResponse = new UserDto();
        userResponse.setUsername("Jovan");
        userResponse.setFirstName("Jovan");
        userResponse.setLastName("Jovan");

        when(userService.update(any(Long.class),any(UserDtoPrivate.class))).thenReturn(userResponse);

        String jsonObject = objectMapper.writeValueAsString(userRequest);

        mockMvc.perform(put("/users/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObject))
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
    public void deleteUser() throws Exception {

        when(userService.delete(any(Long.class))).thenReturn(true);

        mockMvc.perform(delete("/users/delete/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$").isString())
                .andReturn();
    }
}
