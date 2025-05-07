package com.sms.uk.skripsi.core.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.Pair;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PagingUtil {

    public static PageRequest buildPageRequest(Integer page, Integer size){

        int pageRequest = page == null ? 0 : page;
        int sizeRequest = size == null ? 5 : size;

        return PageRequest.of(pageRequest, sizeRequest);
    }

    public static Sort listToSort (List<Pair<String, String>> sorts){

        return Sort.by(sorts
                .stream()
                .map(sort -> {
                    var fieldName = sort.getFirst();
                    var sortType = sort.getSecond();

                    return new Sort.Order(PagingUtil.stringToSortDirection(sortType), fieldName);
                })
                .toList());
    }
    public static Sort.Direction stringToSortDirection(String direction){

        if (direction.equalsIgnoreCase("descend")){
            return Sort.Direction.DESC;
        }else {
            return Sort.Direction.ASC;
        }
    }
}
