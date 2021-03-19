package com.andreea.app.auth;

import com.andreea.app.repository.UserRepository;
import com.andreea.app.models.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Clasa speciala care implementeaza interfata UserDetailsService din Spring Security
 * 2 metode folosite de framework pentru a folosi la autentificare si validari
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    /**
     * Functie folosita in WebSecurityConfigurerAdapter
     * @param usernameOrEmail - String care reprezinta username-ul sau email-ul utilizatorului
     * @return UserDetails
     * @throws UsernameNotFoundException - daca nu exista niciun user
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username or email: " + usernameOrEmail));

        return UserPrincipal.create(userEntity);
    }


    /**
     * Functie folosita in clasa JWTAuthenticationFilter
     * @param id - Long care reprezinta id-ul utilizatorului
     * @return UserDetails
     */
    @Transactional
    public UserDetails loadUserById(Long id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User not found with id : " + id)
        );

        return UserPrincipal.create(userEntity);
    }
}
