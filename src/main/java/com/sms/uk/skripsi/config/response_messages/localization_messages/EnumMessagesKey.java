package com.sms.uk.skripsi.config.response_messages.localization_messages;

import com.sms.uk.skripsi.base.constant.BaseEnumMessageKey;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum EnumMessagesKey implements BaseEnumMessageKey {
    // 200
    SUCCESS("SMS-D-2001", HttpStatus.OK, "success.default"),
    SUCCESS_INSERT("SMS-D-2002", HttpStatus.OK, "success.insert"),
    SUCCESS_UPDATE("SMS-D-2003", HttpStatus.OK, "success.update"),
    SUCCESS_DELETE("SMS-D-2004", HttpStatus.OK, "success.delete"),

    //400
    ERROR_DEFAULT("SMS-D-4001", HttpStatus.BAD_REQUEST, "error.default"),
    ERROR_BAD_REQUEST("SMS-D-4002", HttpStatus.BAD_REQUEST, "error.badRequest"),
    ERROR_UNAUTHORIZED("SMS-D-4003", HttpStatus.UNAUTHORIZED,"error.unauthorized"),
    ERROR_FORBIDDEN("SMS-D-4004", HttpStatus.FORBIDDEN,"error.forbidden"),
    ERROR_NOT_FOUND("SMS-D-4005", HttpStatus.NOT_FOUND,"error.notFound"),
    ERROR_UNSUPPORTED_MEDIA_TYPE("SMS-D-4006", HttpStatus.UNSUPPORTED_MEDIA_TYPE,"error.unsupportedMediaType"),
    ERROR_FILE_SIZE_LIMIT_EXCEEDED("SMS-D-4007", HttpStatus.PAYLOAD_TOO_LARGE, "error.overPayload"),
    ERROR_UNPROCESSABLE_ENTITY("SMS-D-4008", HttpStatus.UNPROCESSABLE_ENTITY, "error.unprocessableEntity"),
    ERROR_ENTITY_NOT_FOUND("SMS-D-4009", HttpStatus.NOT_FOUND,"error.entity.notFound"),
    ERROR_NULL("SMS-D-4010", HttpStatus.NOT_FOUND,"error.notNull"),
    ERROR_GENERATE_SSO_TOKEN_FAILED("SMS-D-4011", HttpStatus.BAD_REQUEST,"error.generate.token.failed"),

    // 400 AUTHENTICATION & USER MANAGEMENT
    USER_NOT_FOUND("SMS-AU-4001", HttpStatus.NOT_FOUND,"error.user.notFound"),
    USER_NOT_FOUND_WITH_EMAIL("SMS-AU-4002", HttpStatus.NOT_FOUND,"error.user.with.email.notFound"),
    USER_INACTIVE("SMS-AU-4003", HttpStatus.BAD_REQUEST,"error.user.inactive"),
    USER_SESSION_EXPIRED("SMS-AU-4004", HttpStatus.BAD_REQUEST,"error.user.session.expired"),
    NEW_PASSWORD_AND_CONFIRMED_PASSWORD_ARE_NOT_MATCH("SMS-AU-4005", HttpStatus.BAD_REQUEST,"error.new.pass.and.confirmed.not.match"),

    // 400 USER MANAGEMENT
    USER_MASTER_NOT_FOUND("SMS-UM-4001", HttpStatus.NOT_FOUND,"error.user.master.notFound"),
    EMAIL_NOT_REGISTERED("SMS-UM-4002", HttpStatus.NOT_FOUND,"error.not.registered"),
    EMAIL_ALREADY_EXISTS("SMS-UM-4003", HttpStatus.UNPROCESSABLE_ENTITY, "error.email.already.exists"),
    PHONE_NUMBER_ALREADY_EXIST("SMS-UM-4004", HttpStatus.UNPROCESSABLE_ENTITY, "error.phone.number.already.exists"),
    USER_NAME_ALREADY_EXISTS("SMS-UM-4005", HttpStatus.UNPROCESSABLE_ENTITY, "error.username.already.exists"),


    //GLOBAL SMS 400
    ERROR_CANT_IMPORT_BLANK_CELL("SMS-V-4001", HttpStatus.BAD_REQUEST, "error.import.blank.cell"),
    ERROR_FILE_TYPE_NOT_INVALID("SMS-V-4002", HttpStatus.BAD_REQUEST, "error.file.type.invalid"),

    //400 FACULTY & MAJOR
    ERROR_DUPLICATED_FACULTY_CODE("SMS-FM-4001", HttpStatus.UNPROCESSABLE_ENTITY, "error.duplicated.faculty.code"),
    ERROR_DUPLICATED_MAJOR_CODE("SMS-FM-4002", HttpStatus.UNPROCESSABLE_ENTITY, "error.duplicated.major.code"),

    //400 SCHOLARSHIP
    SCHOLARSHIP_APPLICATION_ALREADY_EXIST("SMS-SCH-4001", HttpStatus.UNPROCESSABLE_ENTITY, "scholarship.application.already.exists"),


    // 500
    ERROR_INTERNAL_SERVER("SMS-D-5001", HttpStatus.INTERNAL_SERVER_ERROR, "error.internalServer"),
    ERROR_EXPORT_FAILED("SMS-D-5002", HttpStatus.INTERNAL_SERVER_ERROR, "error.export.failed"),

    //info type 500
    ERROR_UPLOAD_DELETE_FAILED_AUTHORITY_ATTACHMENT("SMS-D-5001", HttpStatus.INTERNAL_SERVER_ERROR, "error.upload.delete.authority.attachment"), ERROR_DUPLICATED_ANNOUNCEMENT_TITLE("Judul pengumuman sudah digunakan", HttpStatus.CONFLICT, "ERR_ANN_001");

    private final String code;
    private final HttpStatus httpStatus;
    private final String messageKey;
}
