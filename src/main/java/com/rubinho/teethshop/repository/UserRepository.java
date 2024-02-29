package com.rubinho.teethshop.repository;

import com.rubinho.teethshop.model.Role;
import com.rubinho.teethshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String login);

    Optional<User> findByCode(String code);

    @Modifying
    @Transactional
    @Query("""
            UPDATE User u SET u.role = :role WHERE u.id = :id""")
    void changeRoleById(@Param("id") Long id, @Param("role") Role role);


    @Modifying
    @Transactional
    @Query("""
            UPDATE User u SET u.password = :password WHERE u.code = :code""")
    void changePasswordByCode(@Param("password") String password, @Param("code") String code);
}
