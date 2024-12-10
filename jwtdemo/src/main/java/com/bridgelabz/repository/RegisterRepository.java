package com.bridgelabz.repository;

import com.bridgelabz.model.Register;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegisterRepository extends JpaRepository<Register,Long> {
    boolean existsByUsername(String username);

    Optional<Register> findByUsernameAndPassword(String username, String password);
}
