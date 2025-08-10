package com.unir.digiturno.auth.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.unir.digiturno.auth.models.entities.User;

public interface UserRepository extends CrudRepository<User, Long>{
    
    Optional<User> findByCorreo(String correo);
}
