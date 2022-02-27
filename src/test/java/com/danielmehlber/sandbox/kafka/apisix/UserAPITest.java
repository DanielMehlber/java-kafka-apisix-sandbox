package com.danielmehlber.sandbox.kafka.apisix;

import com.danielmehlber.sandbox.kafka.apisix.entities.User;
import com.danielmehlber.sandbox.kafka.apisix.logic.UserLogic;
import org.junit.jupiter.api.Assertions;
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
public class UserAPITest {

    @Autowired
    MockMvc client;

    @Autowired
    UserLogic userLogic;

    @BeforeEach
    public void prepare() {
        userLogic.deleteAllUsers();
    }

    @Test
    public void registerUserValidJson() throws Exception {
        // prepare
        String username = "user";
        String json = "{\n" +
                "   \"username\":\""+username+"\",\n" +
                "   \"password\":\"password123\"\n" +
                "}";

        // perform
        String result = client.perform(post("/registration").content(json).contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        // verify

        // 1) return value of API call must be the username of the added user
        Assertions.assertEquals(username, result);

        // 2) a user must be added
        User added = userLogic.getUserByUsername(username);
        Assertions.assertNotNull(added);
    }

}
