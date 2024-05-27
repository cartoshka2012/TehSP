package com.example.mstore.repositories;

import com.example.mstore.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findUserById(Long id);

/*    @Query(nativeQuery = true, value = "SELECT COUNT(my_subscribers) FROM user_subscriptions WHERE my_subscribers = User.id")
    int subsribersCount;*/
}
