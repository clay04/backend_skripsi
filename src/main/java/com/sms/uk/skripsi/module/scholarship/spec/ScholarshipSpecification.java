package com.sms.uk.skripsi.module.scholarship.spec;

import com.sms.uk.skripsi.module.scholarship.dtos.ScholarshipFilterRequest;
import com.sms.uk.skripsi.module.scholarship.entities.Scholarship;
import jakarta.persistence.criteria.Predicate;
import org.apache.poi.util.StringUtil;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ScholarshipSpecification {

    public Specification<Scholarship> search(ScholarshipFilterRequest request){

        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (!StringUtil.isBlank(request.getSearchByNim())){

                Predicate nim = criteriaBuilder.like(criteriaBuilder.lower(root.get("nim")), "%" + request.getSearchByNim().toLowerCase() + "%");

                predicates.add(criteriaBuilder.or(nim));
            }

            if (!StringUtil.isBlank(request.getSearchByNoRegis())){

                Predicate noRegis = criteriaBuilder.like(criteriaBuilder.lower(root.get("noRegistration")), "%" + request.getSearchByNoRegis().toLowerCase() + "%");

                predicates.add(criteriaBuilder.or(noRegis));
            }

            if (!StringUtil.isBlank(request.getSearchByType())){

                Predicate type = criteriaBuilder.like(criteriaBuilder.lower(root.get("scholarshipType")), "%" + request.getSearchByType().toLowerCase() + "%");

                predicates.add(criteriaBuilder.or(type));
            }

            if (!StringUtil.isBlank(request.getSearchByStatus())){

                Predicate status = criteriaBuilder.like(criteriaBuilder.lower(root.get("status")), "%" + request.getSearchByStatus().toLowerCase() + "%");

                predicates.add(criteriaBuilder.or(status));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public Specification<Scholarship> searchByUser(ScholarshipFilterRequest request){

        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (!StringUtil.isBlank(request.getSearchByLastName())){

                Predicate lastName = criteriaBuilder.like(criteriaBuilder.lower(root.get("user").get("lastName")), "%" + request.getSearchByLastName().toLowerCase() + "%");

                predicates.add(criteriaBuilder.or(lastName));
            }

            if (!StringUtil.isBlank(request.getSearchByFirstName())){

                Predicate firstName = criteriaBuilder.like(criteriaBuilder.lower(root.get("user").get("firstName")), "%" + request.getSearchByFirstName().toLowerCase() + "%");

                predicates.add(criteriaBuilder.or(firstName));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public Specification<Scholarship> searchByMajor(ScholarshipFilterRequest request){

        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (!StringUtil.isBlank(request.getSearchByMajor())){

                Predicate major = criteriaBuilder.like(criteriaBuilder.lower(root.get("major")), "%" + request.getSearchByMajor().toLowerCase() + "%");

                predicates.add(criteriaBuilder.or(major));
            }

            if (!StringUtil.isBlank(request.getSearchByFaculty())){

                Predicate faculty = criteriaBuilder.like(criteriaBuilder.lower(root.get("major").get("faculty")), "%" + request.getSearchByFirstName().toLowerCase() + "%");

                predicates.add(criteriaBuilder.or(faculty));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
