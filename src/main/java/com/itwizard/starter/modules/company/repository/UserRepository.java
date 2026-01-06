package com.itwizard.starter.modules.company.repository;

import com.itwizard.starter.modules.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}

