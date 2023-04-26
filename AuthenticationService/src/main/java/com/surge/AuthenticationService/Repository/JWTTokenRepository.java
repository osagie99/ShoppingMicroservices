package com.surge.AuthenticationService.Repository;

import com.surge.AuthenticationService.entity.JWTToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JWTTokenRepository extends JpaRepository<JWTToken, Long> {

    @Query("""
    select t from JWTToken t inner join User u on t.user.id = u.id where
    u.id = :userId and (t.expired = false or t.revoked = false)
""")
    List<JWTToken> findAllValidTokenByUser(Long userId);

    Optional<JWTToken> findByToken(String token);
}
