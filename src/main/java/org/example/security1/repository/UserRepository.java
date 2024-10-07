package org.example.security1.repository;

import org.example.security1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User,Integer> {
    //이름 찾기
    User findAllByUsername(String username);

    User findByUsername(String username);
}
