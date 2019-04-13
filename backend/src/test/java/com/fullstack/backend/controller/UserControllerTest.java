package com.fullstack.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fullstack.backend.model.User;
import com.fullstack.backend.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    private final String BASE_URL = "/api/user";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService service;

    private User createUser() {
        return new User("Test", "User" , 23);
    }

    public String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void createUser_HttpStatusShouldOk() throws Exception {
        User user = createUser();
        user.setId(1l);

        mockMvc.perform(post(BASE_URL + "/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(user)))

                .andExpect(status().isOk());
    }

    @Test
    public void updateUser_HttpStatusShouldOk() throws Exception {
        User user = createUser();
        user.setId(1l);

        mockMvc.perform(put(BASE_URL + "/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(user)))

                .andExpect(status().isOk());
    }

    @Test
    public void deleteUser_HttpStatusShouldOk() throws Exception {
        User user = createUser();
        user.setId(1l);

        mockMvc.perform(delete(BASE_URL + "/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", user.getId().toString()))

                .andExpect(status().isOk());
    }

    @Test
    public void getUserById_HttpStatusShouldOk() throws Exception {
        User user = createUser();
        user.setId(1l);

        mockMvc.perform(get(BASE_URL + "/get")
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", user.getId().toString()))

                .andExpect(status().isOk());
    }

    @Test
    public void listUsers_HttpStatusShouldOk() throws Exception {

        mockMvc.perform(get(BASE_URL + "/list")
                .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk());
    }


}
