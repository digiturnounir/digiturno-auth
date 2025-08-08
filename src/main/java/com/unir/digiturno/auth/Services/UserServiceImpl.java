package com.unir.digiturno.auth.Services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.unir.digiturno.auth.models.entities.User;
import com.unir.digiturno.auth.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> finAll() {
        return (List<User>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Optional<User> update(User user, Long id) {
        Optional<User> o = this.findById(id);
        if (o.isPresent()) {
            User userDb = o.get();
            userDb.setNombre(user.getNombre());
            userDb.setCorreo(user.getCorreo());
            userDb.setContrasena(passwordEncoder.encode(user.getContrasena()));
            userDb.setRolId(user.getRolId());
            userDb.setCreadoEn(LocalDateTime.now());
            // fechCrea no se actualiza, se mantiene la fecha original de creación
            return Optional.of(repository.save(userDb));
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public User save(User user) {
        if (repository.findByCorreo(user.getCorreo()).isPresent()) {
            throw new IllegalArgumentException("El correo ya está registrado.");
        }
        user.setContrasena(passwordEncoder.encode(user.getContrasena()));
        user.setCreadoEn(LocalDateTime.now());
        return repository.save(user);
    }


    @Override
    @Transactional
    public void remove(Long id) {
        repository.deleteById(id);
    }

    public Optional<User> findByCorreo(String correo) {
        return repository.findByCorreo(correo);
    }
}
