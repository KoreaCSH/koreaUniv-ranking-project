package koreaUniv.koreaUnivRankSys.domain.auth.service;

import koreaUniv.koreaUnivRankSys.web.auth.dto.AccessTokenDto;
import koreaUniv.koreaUnivRankSys.global.exception.CustomException;
import koreaUniv.koreaUnivRankSys.global.exception.ErrorCode;
import koreaUniv.koreaUnivRankSys.global.jwt.JwtProvider;
import koreaUniv.koreaUnivRankSys.global.redis.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final JwtProvider jwtProvider;
    private final RedisService redisService;

    public AccessTokenDto refresh(String refreshToken) {

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

        return AccessTokenDto.builder().accessToken(newAccessToken).build();
    }

    public AccessTokenDto logout(AccessTokenDto accessTokenDto) {
        String accessToken = accessTokenDto.getAccessToken();
        Authentication authentication = jwtProvider.getAuthentication(accessToken);
        MemberAdapter memberAdapter = (MemberAdapter) authentication.getPrincipal();

        // 이미 로그아웃한 사용자 예외 처리
        String refreshToken = redisService.getRefreshToken(memberAdapter.getMember().getUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.REFRESH_TOKEN_NOTFOUND));

        // redis 에서 해당 사용자의 refreshToken 삭제
        redisService.deleteData("RefreshToken:" + memberAdapter.getMember().getUserId());

        // remainingTime 만큼 BlackList 에 AccessToken 을 저장해서, 해당 토큰으로 접근할 수 없도록 해야 한다.
        Long remainingTime = jwtProvider.getRemainingTime(accessToken);
        redisService.setData("BlackList:" + accessToken, "logout", remainingTime);

        return AccessTokenDto.builder().accessToken(accessToken).build();
    }

    public ResponseCookie removeRefreshTokenCookie() {
        return ResponseCookie.from("refreshToken", null)
                .path("/")
                .maxAge(0)
                // https 관련 설정들
                //.secure(true)
                //.httpOnly(true)
                .build();
    }

}
