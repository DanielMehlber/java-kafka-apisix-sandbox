package com.danielmehlber.sandbox.kafka.apisix.repositories;

import com.danielmehlber.sandbox.kafka.apisix.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, String> {}
