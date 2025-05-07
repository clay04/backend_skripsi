package com.sms.uk.skripsi.module.faculty_major.spec;

import com.sms.uk.skripsi.module.faculty_major.entities.Faculty;
import jakarta.persistence.criteria.Predicate;
import org.apache.poi.util.StringUtil;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FacultySpecification {

    public Specification<Faculty> search(String searchByCode, String searchByName){

        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (!StringUtil.isBlank(searchByCode)){

                Predicate code = criteriaBuilder.like(criteriaBuilder.lower(root.get("facultyCode")), "%" + searchByCode.toLowerCase() + "%");

                predicates.add(criteriaBuilder.or(code));
            }

            if (!StringUtil.isBlank(searchByName)){

                Predicate name = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + searchByName.toLowerCase() + "%");

                predicates.add(criteriaBuilder.or(name));
            }

            predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("isDeleted"), false)));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
