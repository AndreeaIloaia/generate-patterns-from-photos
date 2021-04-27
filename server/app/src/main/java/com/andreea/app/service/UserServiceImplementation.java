package com.andreea.app.service;

import com.andreea.app.repository.UserRepository;
import com.andreea.app.models.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Clasa menita sa acopere nevoiele din business layer pentru un user, incluzand metodele folosite pentru autentificare
 */
@Service
public class UserServiceImplementation {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Verifica daca exista vreun utilizator cu un username dat
     * @param username - String
     * @return true - daca exista, false - altfel
     */
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    /**
     * Verifica daca exista vreun utilizator cu un email dat
     * @param email - String
     * @return true - daca exista, false - altfel
     */
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    /**
     * Verifica daca exista vreun utilizator cu un id dat
     * @param id - Long
     * @return Optional<UserEntity> - daca exista un astfel de utilizator
     */
    public Optional<UserEntity> findById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Salveaza un nou user
     * @param userEntity - UserEntity
     * @return UserEntity - daca s-a salvat, null - altfel
     */
    public UserEntity save(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    /**
     * Sterge un utilizator dupa email sau username
     * @param userEntity - UserEntity
     */
    public void delete(UserEntity userEntity) {
        if(userRepository.existsByUsername(userEntity.getUsername())) {
            userRepository.deleteByUsername(userEntity.getUsername());
        } else {
            userRepository.deleteByEmail(userEntity.getEmail());
        }
    }

    /**
     * Verifica daca un utilizator este admin sau nu, folosind username-ul sau email-ul (care e unic)
     * @param usernameOrEmail - String
     * @return true - daca e admin, false - altfel
     */
    public boolean checkIfAdmin(String usernameOrEmail) {
        Optional<UserEntity> user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
        if(user.isEmpty()) {
            throw new BadCredentialsException("No username or email");
        }
        return user.get().isAdmin();
    }
}
