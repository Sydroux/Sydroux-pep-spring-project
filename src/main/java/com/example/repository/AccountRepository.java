package com.example.repository;

import com.example.entity.Account;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query("FROM Account WHERE username = :varName")
    Optional<Account> findByUsername (@Param("varName") String username);

    @Query("FROM Account WHERE username = :varName AND password = :varPass")
    Optional<Account> findByUsernameAndPassword (@Param("varName") String username, @Param("varPass") String password);
}
