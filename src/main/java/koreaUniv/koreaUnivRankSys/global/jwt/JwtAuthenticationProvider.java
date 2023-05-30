package koreaUniv.koreaUnivRankSys.global.jwt;

import koreaUniv.koreaUnivRankSys.domain.auth.service.MemberAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String stringId = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        MemberAdapter memberAdapter = (MemberAdapter) userDetailsService.loadUserByUsername(stringId);

        if(!passwordEncoder.matches(password, memberAdapter.getMember().getPassword())) {
            throw new BadCredentialsException("BadCredentialsException");
        }

        return new UsernamePasswordAuthenticationToken(memberAdapter, null, memberAdapter.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
