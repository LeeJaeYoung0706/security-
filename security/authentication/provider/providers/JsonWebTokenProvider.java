package newws.client_gateway.security.authentication.provider.providers;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import newws.client_gateway.security.authentication.GrantedAuthority;
import newws.client_gateway.security.authentication.client.ClientCredentials;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Component
public class JsonWebTokenProvider {

    @Value("${spring.jwt.secret}")
    private String SPRING_JWT_SECRET;

    // Token 생성
    public String generateToken(String username, List<GrantedAuthority> authorities) {
        return Jwts.builder()
                .claim("username", username)
                .claim("authorities", authorities)
                .setIssuedAt(new Date())
                .setExpiration(getExpiration())
                .signWith(getKey())
                .compact();
    }


    public String getUsername(String token) {
        Claims claims = getClaims(token);
        return claims.get("username" , String.class);
    }

    public List<GrantedAuthority> getAuthorities(String token) {
        Claims claims = getClaims(token);
        return claims.get("authorities" , List.class);
    }

    // 토큰 만료
    public boolean validateToken(String token) {
        try {
            //  토큰 만료
            final Date expiration = getClaims(token).getExpiration();
            return !expiration.before(new Date());
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 토큰 만료 시간
    private Date getExpiration() {
        return Date.from(LocalDateTime.now().with(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant());
    }

    // 키 값
    private Key getKey() {
        return Keys.hmacShaKeyFor(SPRING_JWT_SECRET.getBytes(StandardCharsets.UTF_8));
    }

    // Jwt get Claims in Body
    private Claims getClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
    }
}
