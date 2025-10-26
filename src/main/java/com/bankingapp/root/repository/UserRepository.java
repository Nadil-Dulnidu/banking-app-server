package com.bankingapp.root.repository;

import com.bankingapp.root.common.Constants;
import com.bankingapp.root.entity.AccountEntity;
import com.bankingapp.root.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    /**
     * Finds a user entity by their unique username.
     *
     * @param username the username of the user to retrieve; must not be {@code null}.
     * @return the {@link UserEntity} with the specified username, or {@code null} if not found.
     */
    Optional<UserEntity> findByUsername(String username);

    /**
     * Finds a user entity by their unique email address.
     *
     * @param email the email of the user to retrieve; must not be {@code null}.
     * @return the {@link UserEntity} with the specified email, or {@code null} if not found.
     */
    Optional<UserEntity> findByEmail(String email);



    List<UserEntity> findAllByUserRole(Constants.UserRoles userRole);
}
