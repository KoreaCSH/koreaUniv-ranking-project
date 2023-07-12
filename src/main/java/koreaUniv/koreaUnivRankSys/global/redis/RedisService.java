package koreaUniv.koreaUnivRankSys.global.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    public void setData(String key, String value, Long expiredTime) {
        redisTemplate.opsForValue().set(key, value, expiredTime, TimeUnit.MILLISECONDS);
    }

    public String getData(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    public void deleteData(String key) {
        redisTemplate.delete(key);
    }

    public Optional<String> getRefreshToken(String userId) {
        return Optional.ofNullable(getData("RefreshToken:" + userId));
    }

    public Optional<String> getBlackList(String accessToken) {
        return Optional.ofNullable(getData("BlackList:" + accessToken));
    }

}
