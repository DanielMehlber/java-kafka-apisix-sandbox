package com.danielmehlber.sandbox.kafka.apisix.restApi;

import com.danielmehlber.sandbox.kafka.apisix.entities.User;
import com.danielmehlber.sandbox.kafka.apisix.exceptions.UserAlreadyExistsException;
import com.danielmehlber.sandbox.kafka.apisix.logic.UserLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserAPI {

    @Autowired
    UserLogic userLogic;

    @GetMapping("/user/list")
    public List<String> listAllUsers() {
        List<String> usernames = new ArrayList<>();

        userLogic.listAllUsers().forEach((user) -> usernames.add(user.getUsername()));

        return usernames;
    }

    @PostMapping(value="/registration", consumes={"application/json", "text/plain"})
    public String registerUser(@RequestBody final User user) throws UserAlreadyExistsException {
        userLogic.registerUser(user);

        return user.getUsername();
    }
}
