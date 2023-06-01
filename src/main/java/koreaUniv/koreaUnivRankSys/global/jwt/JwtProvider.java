package koreaUniv.koreaUnivRankSys.global.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import koreaUniv.koreaUnivRankSys.domain.member.service.MemberAdapter;
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
    private final Long expireTimeNs = 1000 * 60 * 60L;

    public String createToken(String userId) {

        Claims claims = Jwts.claims(); // 일종의 map
        claims.put("roles", "ROLE_USER");
        claims.setIssuedAt(new Date(System.currentTimeMillis()));
        claims.setExpiration(new Date(System.currentTimeMillis() + expireTimeNs));

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
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(stringToKey(secretKey))
                    .build()
                    .parseClaimsJws(jwt).getBody();

            return !claims.getExpiration().before(new Date());
        } catch (JwtException | NullPointerException exception) {
            return false;
        }

    }

    public Authentication getAuthentication(String jwt) {

        String stringId = Jwts.parserBuilder()
                .setSigningKey(stringToKey(secretKey))
                .build()
                .parseClaimsJws(jwt).getBody().getSubject();

        MemberAdapter memberAdapter = (MemberAdapter) userDetailsService.loadUserByUsername(stringId);

        return new UsernamePasswordAuthenticationToken(memberAdapter, null, memberAdapter.getAuthorities());
    }
}
