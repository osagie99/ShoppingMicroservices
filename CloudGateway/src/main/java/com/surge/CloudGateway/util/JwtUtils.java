package com.surge.CloudGateway.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class JwtUtils {

    private static final String SECRET_KEY = "3677397A24432646294A404E635266556A586E327235753778214125442A472D";

    public void validateToken(String token) {
            Jwts
                .parserBuilder()
                .setSigningKey(getSingingKey())
                .build()
                .parseClaimsJws(token);
    }

    private Key getSingingKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
