package koreaUniv.koreaUnivRankSys.domain.auth.service;

import koreaUniv.koreaUnivRankSys.domain.auth.dto.AccessTokenResponse;
import koreaUniv.koreaUnivRankSys.global.exception.CustomException;
import koreaUniv.koreaUnivRankSys.global.exception.ErrorCode;
import koreaUniv.koreaUnivRankSys.global.jwt.JwtProvider;
import koreaUniv.koreaUnivRankSys.global.redis.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final JwtProvider jwtProvider;
    private final RedisService redisService;

    public AccessTokenResponse refresh(String refreshToken) {

        if(!jwtProvider.validateToken(refreshToken)) {
            throw new CustomException(ErrorCode.REFRESH_TOKEN_NOTFOUND);
        }

        Authentication authentication = jwtProvider.getAuthentication(refreshToken);
        MemberAdapter memberAdapter = (MemberAdapter) authentication.getPrincipal();

        String findRefreshToken = redisService.getRefreshToken(memberAdapter.getMember().getUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.REFRESH_TOKEN_NOTFOUND));

        if(!refreshToken.equals(findRefreshToken)) {
            throw new CustomException(ErrorCode.REFRESH_TOKEN_NOT_MATCH);
        }

        String newAccessToken = jwtProvider.createAccessToken(memberAdapter.getMember().getUserId());

        return AccessTokenResponse.builder().accessToken(newAccessToken).build();
    }

}
