package com.sms.uk.skripsi.module.user.dtos;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sms.uk.skripsi.module.authentication.constant.AuthConstant;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class MasterUserRequest {

    private String uuid;

    @NotBlank(message = "error.username.cannot.blank")
    private String username;

    @JsonProperty("first_name")
    @NotBlank(message = "error.firstname.cannot.blank")
    private String firstName;

    @JsonProperty("last_name")
    @NotBlank(message = "error.lastname.cannot.blank")
    private String lastName;

    @JsonProperty("phone_number")
    @NotBlank(message = "error.phone.number.cannot.blank")
    private String phoneNumber;

    @Pattern(regexp = AuthConstant.REGEX_EMAIL_FORMAT_UK, message = "error.email.format.notValid")
    private String email;

    @JsonProperty("non_student_email")
    private String nonStudentEmail;

    @JsonProperty("role_id")
    private String roleId;

    private Boolean status;
}
