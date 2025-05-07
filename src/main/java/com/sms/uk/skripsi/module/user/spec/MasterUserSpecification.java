package com.sms.uk.skripsi.module.user.spec;

import com.sms.uk.skripsi.module.user.entities.MasterUser;
import jakarta.persistence.criteria.Predicate;
import org.apache.poi.util.StringUtil;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MasterUserSpecification {

    public Specification<MasterUser> search(String searchByRoleId, String searchByFirstName, String searchByLastName){

        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (!StringUtil.isBlank(searchByRoleId)){

                Predicate role = criteriaBuilder.like(criteriaBuilder.lower(root.get("roleId")), "%" + searchByRoleId.toLowerCase() + "%");

                predicates.add(criteriaBuilder.or(role));
            }

            if (!StringUtil.isBlank(searchByFirstName)){

                Predicate firstName = criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), "%" + searchByFirstName.toLowerCase() + "%");

                predicates.add(criteriaBuilder.or(firstName));
            }

            if (!StringUtil.isBlank(searchByLastName)){

                Predicate lastName = criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), "%" + searchByLastName.toLowerCase() + "%");

                predicates.add(criteriaBuilder.or(lastName));
            }

            predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("isDeleted"), false)));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
