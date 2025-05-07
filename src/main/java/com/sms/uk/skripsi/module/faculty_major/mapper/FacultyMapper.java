package com.sms.uk.skripsi.module.faculty_major.mapper;

import com.sms.uk.skripsi.core.util.DateTimeUtil;
import com.sms.uk.skripsi.module.faculty_major.dtos.FacultyRequest;
import com.sms.uk.skripsi.module.faculty_major.dtos.FacultyResponse;
import com.sms.uk.skripsi.module.faculty_major.entities.Faculty;
import org.springframework.stereotype.Component;

@Component
public class FacultyMapper {

    public Faculty convertRequestToEntity(FacultyRequest request){

        return Faculty.builder()
                .facultyCode(request.getFacultyCode())
                .name(request.getFacultyName())
                .build();
    }

    public Faculty convertRequestToUpdateEntity(FacultyRequest request, Faculty faculty){

        return Faculty.builder()
                .uuid(faculty.getUuid())
                .facultyCode(request.getFacultyCode())
                .name(request.getFacultyName())
                .createdAt(faculty.getCreatedAt())
                .createdBy(faculty.getCreatedBy())
                .isDeleted(faculty.isDeleted())
                .deletedAt(faculty.getDeletedAt())
                .deletedBy(faculty.getDeletedBy())
                .build();
    }

    public FacultyResponse convertEntityToResponse(Faculty faculty){

        return FacultyResponse.builder()
                .uuid(faculty.getUuid())
                .facultyName(faculty.getName())
                .facultyCode(faculty.getFacultyCode())
                .createdAt(DateTimeUtil.convertToDetailDateTime(faculty.getCreatedAt()))
                .createdBy(faculty.getCreatedBy())
                .updatedAt(DateTimeUtil.convertToDetailDateTime(faculty.getUpdatedAt()))
                .updatedBy(faculty.getUpdatedBy())
                .build();
    }
}
