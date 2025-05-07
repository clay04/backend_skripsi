package com.sms.uk.skripsi.module.authentication.services.impl;

import com.sms.uk.skripsi.base.exception.BaseException;
import com.sms.uk.skripsi.config.response_messages.localization_messages.EnumMessagesKey;
import com.sms.uk.skripsi.config.security.JwtUtil;
import com.sms.uk.skripsi.core.properties.ConfigAppProperties;
import com.sms.uk.skripsi.core.util.DateTimeUtil;
import com.sms.uk.skripsi.core.util.PasswordUtil;
import com.sms.uk.skripsi.module.authentication.constant.AuthConstant;
import com.sms.uk.skripsi.module.authentication.dtos.CreatePasswordRequest;
import com.sms.uk.skripsi.module.authentication.dtos.ForgotPasswordRequest;
import com.sms.uk.skripsi.module.authentication.dtos.LoginRequest;
import com.sms.uk.skripsi.module.authentication.mapper.AuthenticationMapper;
import com.sms.uk.skripsi.module.user.entities.MasterUser;
import com.sms.uk.skripsi.module.user.repositories.MasterUserRepository;
import com.sms.uk.skripsi.module.authentication.services.AuthenticationService;
import com.sms.uk.skripsi.module.email.service.EmailService;
import com.sms.uk.skripsi.module.user.dtos.MasterUserRequest;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final MasterUserRepository repository;
    private final JwtUtil jwtUtil;
    private final ConfigAppProperties appProperties;
    private final EmailService emailService;
    private final AuthenticationMapper mapper;


    @Override
    @Transactional
    public MasterUser signup(MasterUserRequest request) throws MessagingException {

        log.info("Sign up request: {}", request.toString());
        validateRequest(request);

        MasterUser user = createUserFromRequest(request);

        // Send email registration
        emailService.sendEmailRegistration(user);

        return repository.save(user);
    }

    private void validateRequest(MasterUserRequest request) {
        if (repository.existsByEmailIgnoreCase(request.getEmail())) {
            throw new BaseException(EnumMessagesKey.EMAIL_ALREADY_EXISTS);
        }

        if (repository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new BaseException(EnumMessagesKey.PHONE_NUMBER_ALREADY_EXIST);
        }

        if (repository.existsByUsernameIgnoreCase(request.getUsername())) {
            throw new BaseException(EnumMessagesKey.USER_NAME_ALREADY_EXISTS);
        }
    }

    private MasterUser createUserFromRequest(MasterUserRequest request) {

        log.info("Create request: {}", request.toString());
        MasterUser user = mapper.convertUserRequestToEntity(request);
        String createPasswordCode = generateVerificationCode();
        user.setVerificationCode(createPasswordCode);
        user.setExpVerificationCode(DateTimeUtil.getCurrentDateTimePlusOneHour());

        return user;
    }

    private String generateVerificationCode() {
        return java.util.UUID.randomUUID().toString();
    }

    @Override
    @Transactional
    public MasterUser login(LoginRequest request) {
        log.info("Login request: {}", request.toString());
        MasterUser user = repository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BaseException(EnumMessagesKey.ERROR_UNAUTHORIZED));

        validateUserStatus(user);
        validatePassword(request.getPassword(), user.getPassword());

        String token = jwtUtil.generate(request.getUsername());
        LocalDateTime expToken = getTokenExpiration(token);

        user.setToken(token);
        user.setExpToken(expToken);

        return repository.save(user);
    }

    private void validateUserStatus(MasterUser user) {
        if (!user.isStatus()) {
            throw new BaseException(EnumMessagesKey.USER_INACTIVE, user.getUsername());
        }
    }

    private void validatePassword(String inputPassword, String storedPassword) {
        if (!PasswordUtil.verifyPassword(inputPassword, storedPassword)) {
            throw new BaseException(EnumMessagesKey.ERROR_UNAUTHORIZED);
        }
    }

    private LocalDateTime getTokenExpiration(String token) {
        Date expiration = jwtUtil.getClaims(token).getExpiration();
        return DateTimeUtil.getLocalDateOfInstant(expiration);
    }

    @Override
    @Transactional
    public boolean logout(String uuid) {

        MasterUser user = this.detail(uuid);
        String token = jwtUtil.generateTokenLogout(user);
        user.setToken(token);
        user.setExpToken(LocalDateTime.now());
        repository.save(user);

        return true;
    }

    @Override
    @Transactional
    public boolean forgotPassword(ForgotPasswordRequest request) throws MessagingException {
        log.info("Forgot password request: {}", request.toString());
        // GET USER BY EMAIL
        MasterUser user = findByEmail(request.getEmail().toLowerCase());

        // GENERATE EMAIL VERIFICATION UUID
        String forgotPasswordCode = generateVerificationCode();
        // UPDATE USER
        user.setVerificationCode(forgotPasswordCode);
        user.setExpVerificationCode(DateTimeUtil.getCurrentDateTimePlusOneHour());

        repository.save(user);

        // SEND EMAIL
        Context context = new Context(LocaleContextHolder.getLocale());
        context.setVariable("name", user.getFirstName() + " " + user.getLastName());
        context.setVariable("link",
                appProperties.baseUrlFe() + AuthConstant.BASE_URL_CREATE_PASSWORD_FE + forgotPasswordCode);

        try {

            emailService.sendForgotPasswordHtmlEmail(user.getEmail(), context);
        } catch (Exception e) {
            throw new MessagingException("Error occurred while sending the forgot password email", e);
        }

        return true;
    }

    @Override
    @Transactional
    public boolean createPassword(String token, CreatePasswordRequest request) {
        log.info("Create password request: {}", request.toString());
        // Validate token and fetch user
        MasterUser user = repository.findByVerificationCode(token).orElseThrow(
                () -> new BaseException(EnumMessagesKey.USER_NOT_FOUND));

        // Check token expiration
        if (user.getExpVerificationCode().isBefore(DateTimeUtil.getCurrentDateTime())) {
            throw new BaseException(EnumMessagesKey.USER_SESSION_EXPIRED);
        }

        // Validate password match
        if (!request.getNewPassword().equals(request.getConfirmNewPassword())) {
            throw new BaseException(EnumMessagesKey.NEW_PASSWORD_AND_CONFIRMED_PASSWORD_ARE_NOT_MATCH);
        }

        // Update user password and reset verification data
        user.setPassword(PasswordUtil.encryptPassword(request.getNewPassword()));
        user.setStatus(true);
        user.setVerificationCode(null);
        user.setExpVerificationCode(null);
        repository.save(user);

        return true;
    }

    private MasterUser detail(String uuid){
        return repository.findById(uuid)
                .orElseThrow(() -> new BaseException(EnumMessagesKey.USER_MASTER_NOT_FOUND));
    }

    private MasterUser findByEmail(String email) {
        Optional<MasterUser> user = repository.findByEmail(email);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new BaseException(EnumMessagesKey.EMAIL_NOT_REGISTERED);
        }
    }
}
