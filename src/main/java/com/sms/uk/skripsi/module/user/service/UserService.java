package com.sms.uk.skripsi.module.user.service;

import com.sms.uk.skripsi.module.user.dtos.MasterUserRequest;
import com.sms.uk.skripsi.module.user.entities.MasterUser;
import jakarta.mail.MessagingException;
import org.springframework.data.domain.Page;

public interface UserService {

    MasterUser create(MasterUserRequest request) throws MessagingException;

    MasterUser update(MasterUserRequest request);

    Page<MasterUser> paging(Integer page, Integer size, String sortBy, String orderBy, String searchByRoleId, String searchByFirstName, String searchByLastName);

    boolean delete(String uuid);

    MasterUser detail(String uuid);
}
