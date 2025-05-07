package com.sms.uk.skripsi.module.authentication.services;

import com.sms.uk.skripsi.base.exception.BaseException;
import com.sms.uk.skripsi.config.response_messages.localization_messages.EnumMessagesKey;
import com.sms.uk.skripsi.module.user.entities.MasterUser;
import com.sms.uk.skripsi.module.user.repositories.MasterUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MasterUserRepository repository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        MasterUser user = repository.findByEmail(email)
                .orElseThrow(() ->
                        new BaseException(EnumMessagesKey.USER_NOT_FOUND_WITH_EMAIL, email));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.emptyList()
        );
    }
}
