package com.danielmehlber.sandbox.kafka.apisix.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Entity
@Table(name = "users")
public class User {

    @Id
    private String username;

    @JsonProperty("password")
    @Column(name="passwordhash")
    private String passwordHash;

    // THIS CONSTRUCTOR IN MEANT TO BE PRIVATE
    private User() {}

    /**
     * Constructor of user
     * @param username unique username is used as ID of userdata
     * @param password password in plain text format, will be converted into Hash by this constructor
     */
    @JsonCreator
    public User(@JsonProperty("username") final String username,
                @JsonProperty("password") final String password) throws NoSuchAlgorithmException {
        this.username = username;

        // convert password in plain text into hash and store
        this.passwordHash = convertPlainTextToHash(password);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean checkPassword(final String password) throws NoSuchAlgorithmException {
        return this.passwordHash.equals(convertPlainTextToHash(password));
    }

    private static String convertPlainTextToHash(final String plaintext) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(plaintext.getBytes());
        return new String(messageDigest.digest());
    }
}
