package com.danielmehlber.sandbox.kafka.apisix.logic;

import com.danielmehlber.sandbox.kafka.apisix.entities.User;
import com.danielmehlber.sandbox.kafka.apisix.exceptions.NoSuchUserException;
import com.danielmehlber.sandbox.kafka.apisix.exceptions.UserAlreadyExistsException;
import com.danielmehlber.sandbox.kafka.apisix.exceptions.WrongPasswordException;
import com.danielmehlber.sandbox.kafka.apisix.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;

@Component
public class UserLogic {

    @Autowired
    private UserRepository userRepository;

    public User getUserByUsername(final String username) throws NoSuchUserException {

        return userRepository.findById(username)
                .orElseThrow(() -> new NoSuchUserException(username));
    }

    public User authenticateUser(final String username, final String password) throws NoSuchUserException, NoSuchAlgorithmException, WrongPasswordException {
        User user = getUserByUsername(username);

        if(!user.checkPassword(password)) {
            throw new WrongPasswordException(username);
        }

        return user;
    }

    public Iterable<User> listAllUsers() {
        return userRepository.findAll();
    }

    public void registerUser(final User newUser) throws RuntimeException, UserAlreadyExistsException {
        if(userRepository.existsById(newUser.getUsername()))
            throw new UserAlreadyExistsException(newUser.getUsername());

        userRepository.save(newUser);
    }

    public void deleteAllUsers() throws RuntimeException{
        userRepository.deleteAll();
    }

}
