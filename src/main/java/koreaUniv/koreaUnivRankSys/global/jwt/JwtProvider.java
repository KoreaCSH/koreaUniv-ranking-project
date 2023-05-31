package koreaUniv.koreaUnivRankSys.global.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import koreaUniv.koreaUnivRankSys.domain.auth.service.MemberAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final UserDetailsService userDetailsService;

    @Value("${jwt.token.secret}")
    private String secretKey;
    private final Long accessTokenExpireTimeNs = 1000 * 60L;
    private final Long refreshTokenExpireTimeNs = 1000 * 60 * 3L;

    public Long getRefreshTokenExpireTimeNs() {
        return refreshTokenExpireTimeNs;
    }

    public String createAccessToken(String userId) {

        Claims claims = Jwts.claims(); // 일종의 map
        claims.put("roles", "ROLE_USER");
        claims.setIssuedAt(new Date(System.currentTimeMillis()));
        claims.setExpiration(new Date(System.currentTimeMillis() + accessTokenExpireTimeNs));

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(claims)
                .setSubject(userId)
                .signWith(stringToKey(secretKey), SignatureAlgorithm.HS256)
                .compact();

    }

    public String createRefreshToken(String userId) {

        Claims claims = Jwts.claims(); // 일종의 map
        claims.setIssuedAt(new Date(System.currentTimeMillis()));
        claims.setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpireTimeNs));

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(claims)
                .setSubject(userId)
                .signWith(stringToKey(secretKey), SignatureAlgorithm.HS256)
                .compact();
    }

    // secret key 를 바이트코드로 변경, 시그니처에 Hmac Sha 256 알고리즘 적용
    private Key stringToKey(String secretKey) {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public boolean validateToken(String jwt) {

        try {
            Claims claims = getClaims(jwt);
            return !claims.getExpiration().before(new Date());
        } catch (JwtException | NullPointerException exception) {
            return false;
        }

    }

    public Authentication getAuthentication(String jwt) {

        String stringId = getClaims(jwt).getSubject();
        MemberAdapter memberAdapter = (MemberAdapter) userDetailsService.loadUserByUsername(stringId);

        return new UsernamePasswordAuthenticationToken(memberAdapter, null, memberAdapter.getAuthorities());
    }

    public Long getRemainingTime(String jwt) {
        Date expiration = getClaims(jwt).getExpiration();
        Date now = new Date();
        return expiration.getTime() - now.getTime();
    }

    private Claims getClaims(String jwt) {
        return Jwts.parserBuilder()
                .setSigningKey(stringToKey(secretKey))
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }

}
