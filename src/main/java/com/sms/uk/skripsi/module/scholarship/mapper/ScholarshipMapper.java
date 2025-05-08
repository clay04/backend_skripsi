package com.sms.uk.skripsi.module.scholarship.mapper;

import com.sms.uk.skripsi.base.exception.BaseException;
import com.sms.uk.skripsi.config.response_messages.localization_messages.EnumMessagesKey;
import com.sms.uk.skripsi.core.util.DateTimeUtil;
import com.sms.uk.skripsi.core.util.UserUtil;
import com.sms.uk.skripsi.module.faculty_major.entities.Major;
import com.sms.uk.skripsi.module.faculty_major.mapper.MajorMapper;
import com.sms.uk.skripsi.module.faculty_major.repositories.MajorRepository;
import com.sms.uk.skripsi.module.scholarship.constant.ScholarshipConstant;
import com.sms.uk.skripsi.module.scholarship.dtos.ScholarshipRequest;
import com.sms.uk.skripsi.module.scholarship.dtos.ScholarshipResponse;
import com.sms.uk.skripsi.module.scholarship.entities.Scholarship;
import com.sms.uk.skripsi.module.user.entities.MasterUser;
import com.sms.uk.skripsi.module.user.mapper.UserManagementMapper;
import com.sms.uk.skripsi.module.user.repositories.MasterUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScholarshipMapper {

    private final MajorRepository majorRepository;
    private final MasterUserRepository masterUserRepository;
    private final UserManagementMapper userManagementMapper;
    private final MajorMapper majorMapper;

    public Scholarship convertRequestToEntity(ScholarshipRequest request){

        Major major = majorRepository.findById(request.getMajorId())
                .orElseThrow(() -> new BaseException(EnumMessagesKey.ERROR_NOT_FOUND));

        MasterUser masterUser = masterUserRepository.findByUsername(UserUtil.principal().getName())
                .orElseThrow(() -> new BaseException(EnumMessagesKey.ERROR_NOT_FOUND));


        return Scholarship.builder()
                .nim(request.getNim())
                .noRegistration(request.getNoRegistration())
                .scholarshipType(request.getScholarshipType())
                .status(ScholarshipConstant.STATUS_IN_PROGRESS)
                .addressLine1(request.getAddressLine1())
                .addressLine2(request.getAddressLine2())
                .highschoolName(request.getHighSchoolName())
                .fatherName(request.getFatherName())
                .motherName(request.getMotherName())
                .user(masterUser)
                .major(major)
                .documentCompletionStatus(request.getDocumentCompletionStatus())
                .build();
    }

    public Scholarship convertRequestToUpdateEntity(ScholarshipRequest request, Scholarship scholarship){

        return Scholarship.builder()
                .uuid(scholarship.getUuid())
                .nim(request.getNim())
                .noRegistration(request.getNoRegistration())
                .scholarshipType(request.getScholarshipType())
                .addressLine1(request.getAddressLine1())
                .addressLine2(request.getAddressLine2())
                .highschoolName(request.getHighSchoolName())
                .fatherName(request.getFatherName())
                .motherName(request.getMotherName())
                .major(scholarship.getMajor())
                .user(scholarship.getUser())
                .status(scholarship.getStatus())
                .createdAt(scholarship.getCreatedAt())
                .createdBy(scholarship.getCreatedBy())
                .isDeleted(scholarship.isDeleted())
                .deletedAt(scholarship.getDeletedAt())
                .deletedBy(scholarship.getDeletedBy())
                .build();
    }

    public ScholarshipResponse convertEntityToResponse(Scholarship scholarship) {

        return ScholarshipResponse.builder()
                .uuid(scholarship.getUuid())
                .nim(scholarship.getNim())
                .noRegistration(scholarship.getNoRegistration())
                .scholarshipType(scholarship.getScholarshipType())
                .status(scholarship.getStatus())
                .addressLine1(scholarship.getAddressLine1())
                .addressLine2(scholarship.getAddressLine2())
                .highSchoolName(scholarship.getHighschoolName())
                .fatherName(scholarship.getFatherName())
                .motherName(scholarship.getMotherName())
                .major(majorMapper.convertEntityToResponse(scholarship.getMajor()))
                .documentCompletionStatus(scholarship.getDocumentCompletionStatus() != null ? scholarship.getDocumentCompletionStatus() : false)
                .masterUser(userManagementMapper.convertEntityToResponse(scholarship.getUser()))
                .createdAt(DateTimeUtil.convertToDetailDateTime(scholarship.getCreatedAt()))
                .createdBy(scholarship.getCreatedBy())
                .updatedAt(DateTimeUtil.convertToDetailDateTime(scholarship.getUpdatedAt()))
                .updatedBy(scholarship.getUpdatedBy())
                .build();
    }
}
