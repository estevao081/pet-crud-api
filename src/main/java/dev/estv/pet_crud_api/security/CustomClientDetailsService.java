package dev.estv.pet_crud_api.security;

import dev.estv.pet_crud_api.model.UserModel;
import dev.estv.pet_crud_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CustomClientDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomClientDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserModel user = this.userRepository.findByUsermail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new org
                .springframework
                .security
                .core
                .userdetails
                .User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }
}
