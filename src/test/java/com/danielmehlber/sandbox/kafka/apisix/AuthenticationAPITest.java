package com.danielmehlber.sandbox.kafka.apisix;

import com.danielmehlber.sandbox.kafka.apisix.entities.User;
import com.danielmehlber.sandbox.kafka.apisix.logic.UserLogic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationAPITest {

    @Autowired
    MockMvc client;

    @Autowired
    UserLogic userLogic;

    @BeforeEach
    public void prepare() {
        userLogic.deleteAllUsers();
    }

    @Test
    public void authRegisteredUser() throws Exception {
        // prepare
        String password = "password";
        User user = new User("user", password);
        userLogic.registerUser(user);

        // perform
        String url = "/auth/" + user.getUsername();
        client.perform(
                post(url)
                        .content(password))
                .andExpect(status().isOk());
    }

    @Test
    public void authNotRegisteredUser() throws Exception {
        client.perform(post("/auth/not-a-user").content("some-pw")).andExpect(status().isBadRequest());
    }

}
