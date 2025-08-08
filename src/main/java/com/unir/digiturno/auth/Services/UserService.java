package com.unir.digiturno.auth.Services;

import java.util.List;
import java.util.Optional;

import com.unir.digiturno.auth.models.entities.User;


public interface UserService {

    List<User> finAll();
    
    Optional<User> findById(Long id);

    User save(User user);

    Optional<User> update(User user, Long id);

    void remove(Long id);

    Optional<User> findByCorreo(String correo);

}
