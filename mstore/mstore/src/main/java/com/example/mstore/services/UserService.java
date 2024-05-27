package com.example.mstore.services;

import com.example.mstore.models.User;
import com.example.mstore.models.enums.Role;
import com.example.mstore.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean createUser(User user) {
        String email = user.getEmail();
        if (userRepository.findByEmail(email) != null) return false;
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(Role.ROLE_ADMIN);
        log.info("Saving new User with email: {}", email);
        userRepository.save(user);
        return true;
    }

    public List<User> list() {
        return userRepository.findAll();
    }

    public void banUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            if (user.isActive()) {
                user.setActive(false);
                log.info("Ban user with id = {}; email: {}", user.getId(), user.getEmail());
            } else {
                user.setActive(true);
                log.info("Unban user with id = {}; email: {}", user.getId(), user.getEmail());
            }
        }
        userRepository.save(user);
    }

    public void changeUserRoles(User user, Map<String, String> form) {
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());
        user.getRoles().clear();
        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }
        userRepository.save(user);
    }
    public User getUserByPrincipal(Principal principal) {
        if (principal == null) return new User();
        return userRepository.findByEmail(principal.getName());
    }

    public void subscribe(User currentUser, Long id) {
        this.userRepository.findById(id).ifPresent(user -> {
            user.getSubscribers().add(currentUser);

            userRepository.save(user);
        });
    }

    public void unsubscribe(User currentUser, Long id) {
        this.userRepository.findById(id).ifPresent(user -> {
            log.info("Unsubscribe user with id = {}; email: {}", id, user.getEmail());
            user.getSubscribers().remove(currentUser);
            log.info("size" + user.getSubscribers().size());
            userRepository.save(user);
        });
    }
    public User getUserById(Long id){
        return userRepository.findUserById(id);
    }

    public void setSubscriptoinsCount(Long id1, Long id2){
        User user1 = userRepository.findById(id1).orElse(null);
        User user2 = userRepository.findById(id2).orElse(null);
        user1.setSubscriptionsCount(user1.getSubscriptionsCount() + 1);
        user2.setSubscribersCount(user2.getSubscribersCount() + 1);
        userRepository.save(user1);
        userRepository.save(user2);

    }

    public void setUnsubscriptoinsCount(Long id1, Long id2){
        User user1 = userRepository.findById(id1).orElse(null);
        User user2 = userRepository.findById(id2).orElse(null);
        user1.setSubscriptionsCount(user1.getSubscriptionsCount() - 1);
        user2.setSubscribersCount(user2.getSubscribersCount() - 1);
        userRepository.save(user1);
        userRepository.save(user2);

    }

}