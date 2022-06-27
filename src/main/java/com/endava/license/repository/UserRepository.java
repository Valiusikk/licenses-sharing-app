package com.endava.license.repository;

import com.endava.license.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    /**
     * Should return Optional with User Entity if user with such email is registered in the system
     * In another case returns an empty Optional
     *
     * @param email email address of user
     * @return Optional with User or empty Optional
     */
    Optional<UserEntity> findByEmail(String email);
}
