package com.sms.uk.skripsi.config.security;

import com.sms.uk.skripsi.base.exception.BaseException;
import com.sms.uk.skripsi.config.response_messages.localization_messages.EnumMessagesKey;
import com.sms.uk.skripsi.module.user.entities.MasterUser;
import com.sms.uk.skripsi.module.user.repositories.MasterUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    private final MasterUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        // trying to find Authorization header
        final String authorizationHeader = httpServletRequest.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer")) {
            // if Authorization header does not exist, then skip this filter
            // and continue to execute next filter class
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        final String token = authorizationHeader.split(" ")[1].trim();
        if (!jwtUtil.validate(token)) {
            // if token is not valid, then skip this filter
            // and continue to execute next filter class.
            // This means authentication is not successful since token is invalid.
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        // Authorization header exists, token is valid. So, we can authenticate.
        String username = jwtUtil.getUsername(token);
        MasterUser user = userRepository.findByUsernameIgnoreCase(username).orElseThrow(() -> new BaseException(EnumMessagesKey.USER_NOT_FOUND, username));

        if (!user.getToken().equals(token)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        // initializing UsernamePasswordAuthenticationToken with its 3 parameter constructor
        // because it sets super.setAuthenticated(true); in that constructor.
        UsernamePasswordAuthenticationToken upassToken = new UsernamePasswordAuthenticationToken(
                username, null, Collections.emptyList());
        upassToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
        // finally, give the authentication token to Spring Security Context
        SecurityContextHolder.getContext().setAuthentication(upassToken);

        // end of the method, so go for next filter class
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
