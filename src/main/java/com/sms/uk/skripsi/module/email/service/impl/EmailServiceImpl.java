package com.sms.uk.skripsi.module.email.service.impl;

import com.sms.uk.skripsi.core.properties.ConfigAppProperties;
import com.sms.uk.skripsi.module.authentication.constant.AuthConstant;
import com.sms.uk.skripsi.module.email.constant.EmailConstant;
import com.sms.uk.skripsi.module.email.service.EmailService;
import com.sms.uk.skripsi.module.user.entities.MasterUser;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;
    private final ConfigAppProperties configAppProperties;

    @Async
    @Override
    public void sendEmailRegistration(MasterUser user) throws MessagingException {
        try {
            log.info("Sending approval status email for user: {}", user.getEmail());

            String verificationLink = buildVerificationLink(user.getVerificationCode());
            Context context = new Context(LocaleContextHolder.getLocale());
            context.setVariable("link", verificationLink);

            // Convert the image to Base64
            byte[] imageBytes = Files.readAllBytes(Paths.get(EmailConstant.TEMPLATE_IMAGE_PATH + "logo-uk.png"));
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);

            // Add the Base64 string to the context
            context.setVariable("logoBase64", base64Image);

            // Process the HTML template
            String bodyHtml = templateEngine.process("create-password-email.html", context);

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.name());

            // Set email details
            helper.setTo(new InternetAddress(user.getEmail()));
            helper.setSubject("Create Password");
            helper.setFrom(new InternetAddress(configAppProperties.mailFrom()));
            helper.setText(bodyHtml, true); // Enable HTML content

            // Send the email
            log.info("SEND EMAIL IN EMAIL SERVICE");
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new MessagingException("Error occurred while sending the registration email", e);
        }
    }

    private String buildVerificationLink(String verificationCode) {
        return configAppProperties.baseUrlFe() + AuthConstant.BASE_URL_CREATE_PASSWORD_FE + verificationCode;
    }

    @Override
    @Async
    public void sendEmailApprovalNotification(MasterUser user, boolean isApproved) throws MessagingException {

        try {
            log.info("Sending registration email for user: {}", user.getEmail());

            String verificationLink = buildVerificationLink(user.getVerificationCode());
            Context context = new Context(LocaleContextHolder.getLocale());
            context.setVariable("link", verificationLink);

            // Convert the image to Base64
            byte[] imageBytes = Files.readAllBytes(Paths.get(EmailConstant.TEMPLATE_IMAGE_PATH + "logo-uk.png"));
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);

            // Add the Base64 string to the context
            context.setVariable("logoBase64", base64Image);

            // Process the HTML template
            String bodyHtml;
            if (isApproved){

                bodyHtml = templateEngine.process("scholarship-approved-email.html", context);
            }else {

                bodyHtml = templateEngine.process("scholarship-rejected-email.html", context);
            }

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.name());

            // Set email details
            helper.setTo(new InternetAddress(user.getEmail()));
            helper.setSubject("Pemberitahuan status penerimaan beasiswa");
            helper.setFrom(new InternetAddress(configAppProperties.mailFrom()));
            helper.setText(bodyHtml, true); // Enable HTML content

            // Send the email
            log.info("SEND EMAIL IN EMAIL SERVICE");
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new MessagingException("Error occurred while sending the approval information email", e);
        }
    }

    @Override
    @Async
    public void sendForgotPasswordHtmlEmail(String to, Context context) {
        try {
            // Convert the image to Base64
            byte[] imageBytes = Files.readAllBytes(Paths.get(EmailConstant.TEMPLATE_IMAGE_PATH + "logo-uk.png"));
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);

            // Add the Base64 string to the context
            context.setVariable("logoBase64", base64Image);

            // Process the HTML template
            String bodyHtml = templateEngine.process("forgot-password-email.html", context);

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.name());

            // Set email details
            helper.setTo(new InternetAddress(to));
            helper.setSubject("Reset Password");
            helper.setFrom(new InternetAddress(configAppProperties.mailFrom()));
            helper.setText(bodyHtml, true); // Enable HTML content

            // Send the email
            log.info("SEND EMAIL IN EMAIL SERVICE");
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            log.error("Failed to send forgot password email to {}", to, e);
        }


    }

}
