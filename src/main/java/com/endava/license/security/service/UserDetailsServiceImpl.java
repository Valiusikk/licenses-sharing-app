package com.endava.license.security.service;


import com.endava.license.exception.ExceptionType;
import com.endava.license.exception.handler.LicensesException;
import com.endava.license.repository.UserRepository;
import com.endava.license.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .map(UserDetailsImpl::convert)
                .orElseThrow(() -> new LicensesException(ExceptionType.INVALID_USERNAME_OR_PASSWORD));
    }

    public UserDetails loadUserById(UUID id) throws UsernameNotFoundException {
        return userRepository.findById(id)
                .map(UserDetailsImpl::convert)
                .orElseThrow(() -> new LicensesException(ExceptionType.USER_ID_NOT_FOUND));
    }
}
