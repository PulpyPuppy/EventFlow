package ufck.eventflow.eventflow.service;

import ufck.eventflow.eventflow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {
    // God forgive me for this naming...

    private final UserRepository userRepository;

    // The compiler says I do something wrong here but who cares
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ufck.eventflow.eventflow.entity.User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // More dots, more dots, not enough dots!
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                new ArrayList<>()
        );
    }
}
