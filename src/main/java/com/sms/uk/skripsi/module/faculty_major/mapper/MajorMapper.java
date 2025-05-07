package com.sms.uk.skripsi.module.faculty_major.mapper;

import com.sms.uk.skripsi.base.exception.BaseException;
import com.sms.uk.skripsi.config.response_messages.localization_messages.EnumMessagesKey;
import com.sms.uk.skripsi.core.util.DateTimeUtil;
import com.sms.uk.skripsi.module.faculty_major.dtos.MajorRequest;
import com.sms.uk.skripsi.module.faculty_major.dtos.MajorResponse;
import com.sms.uk.skripsi.module.faculty_major.entities.Faculty;
import com.sms.uk.skripsi.module.faculty_major.entities.Major;
import com.sms.uk.skripsi.module.faculty_major.repositories.FacultyRepository;
import org.springframework.stereotype.Component;

@Component
public class MajorMapper {

    private final FacultyRepository facultyRepository;

    public MajorMapper(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Major convertRequestToEntity(MajorRequest request){

        Faculty faculty = facultyRepository.findById(request.getFacultyUuid())
                .orElseThrow(() -> new BaseException(EnumMessagesKey.ERROR_NOT_FOUND));

        return Major.builder()
                .majorCode(request.getMajorCode())
                .name(request.getMajorName())
                .faculty(faculty)
                .build();
    }

    public Major convertRequestToUpdateEntity(MajorRequest request, Major major){

        return Major.builder()
                .uuid(major.getUuid())
                .majorCode(request.getMajorCode())
                .name(request.getMajorName())
                .faculty(major.getFaculty())
                .createdAt(major.getCreatedAt())
                .createdBy(major.getCreatedBy())
                .isDeleted(major.isDeleted())
                .deletedAt(major.getDeletedAt())
                .deletedBy(major.getDeletedBy())
                .build();
    }

    public MajorResponse convertEntityToResponse(Major major){

        return MajorResponse.builder()
                .uuid(major.getUuid())
                .facultyCode(major.getFaculty().getFacultyCode())
                .facultyName(major.getFaculty().getName())
                .majorName(major.getName())
                .majorCode(major.getMajorCode())
                .createdAt(DateTimeUtil.convertToDetailDateTime(major.getCreatedAt()))
                .createdBy(major.getCreatedBy())
                .updatedAt(DateTimeUtil.convertToDetailDateTime(major.getUpdatedAt()))
                .updatedBy(major.getUpdatedBy())
                .build();
    }
}
