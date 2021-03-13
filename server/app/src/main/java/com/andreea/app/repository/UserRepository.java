package com.andreea.app.repository;

import com.andreea.app.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Interfata care implementeaza JpaRepository
 * Persistence layer
 * Lucreaza cu baza de date
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsernameOrEmail(String username, String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    @Transactional
    void deleteByUsername(String username);

    @Transactional
    void deleteByEmail(String email);

}
