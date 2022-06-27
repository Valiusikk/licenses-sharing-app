package com.endava.license.service.impl;

import com.endava.license.dto.LoginRequest;
import com.endava.license.dto.LoginResponse;
import com.endava.license.dto.RegisterRequest;
import com.endava.license.entity.RoleEntity;
import com.endava.license.entity.UserEntity;
import com.endava.license.exception.ExceptionType;
import com.endava.license.exception.handler.LicensesException;
import com.endava.license.repository.RoleRepository;
import com.endava.license.repository.UserRepository;
import com.endava.license.security.jwt.JwtTokenProvider;
import com.endava.license.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    public static final String ID_FOR_USER_ROLE = "2";

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder cryptPasswordEncoder;
    private final JwtTokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;
    private final ConversionService conversionService;

    @Override
    public LoginResponse registerUser(RegisterRequest registerRequest) throws LicensesException {
        final String email = registerRequest.getEmail();
        final String password = registerRequest.getPassword();
        final List<RoleEntity> roles = List.of(roleRepository.getById(ID_FOR_USER_ROLE));

        isEmailUnique(email);

        UserEntity user = conversionService.convert(registerRequest, UserEntity.class);
        user.setPassword(cryptPasswordEncoder.encode(password));
        user.setRoles(roles);

        userRepository.save(user);
        return generateLoginResponse(email, password);
    }

    @Override
    @Transactional
    public LoginResponse login(LoginRequest request) {
        try {
            return tryLogin(request);
        } catch (AuthenticationException e) {
            throw new LicensesException(ExceptionType.INVALID_USERNAME_OR_PASSWORD);
        }
    }


    private LoginResponse generateLoginResponse(String email, String password){
        final Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        final String token = tokenProvider.generateToken(authentication);

        log.info("Logging user with email " + email);
        return new LoginResponse(email, token);
    }

    private LoginResponse tryLogin(LoginRequest loginRequest){
        final String email = loginRequest.getUsername();
        final String password = loginRequest.getPassword();
        doesUserWithEmailExists(email);

        return generateLoginResponse(email, password);
    }

    private void isEmailUnique(String email){
        userRepository
                .findByEmail(email)
                .ifPresent(user -> {
                    throw new LicensesException(ExceptionType.EMAIL_IS_NOT_UNIQUE);
                });
    }

    private void doesUserWithEmailExists(String email) {
        userRepository
                .findByEmail(email)
                .orElseThrow(() -> {
                            log.error("User with such email " + email + "was not found");
                            return new LicensesException(ExceptionType.INVALID_USERNAME_OR_PASSWORD);
                        });
    }

}