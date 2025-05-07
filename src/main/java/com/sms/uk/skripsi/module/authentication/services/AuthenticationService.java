package com.sms.uk.skripsi.module.authentication.services;

import com.sms.uk.skripsi.module.authentication.dtos.CreatePasswordRequest;
import com.sms.uk.skripsi.module.authentication.dtos.ForgotPasswordRequest;
import com.sms.uk.skripsi.module.authentication.dtos.LoginRequest;
import com.sms.uk.skripsi.module.user.entities.MasterUser;
import com.sms.uk.skripsi.module.user.dtos.MasterUserRequest;
import jakarta.mail.MessagingException;

public interface AuthenticationService {

    MasterUser signup(MasterUserRequest request) throws MessagingException;

    MasterUser login(LoginRequest request);

    boolean logout(String uuid);

    boolean  forgotPassword(ForgotPasswordRequest request) throws MessagingException;

    boolean createPassword(String token, CreatePasswordRequest request);
}
