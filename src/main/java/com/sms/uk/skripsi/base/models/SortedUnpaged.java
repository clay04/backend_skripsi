package com.sms.uk.skripsi.base.models;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class SortedUnpaged implements Pageable {

    private final Sort sort;

    private SortedUnpaged(Sort sort) {
        this.sort = sort;
    }

    public static SortedUnpaged getInstance(Sort sort) {
        return new SortedUnpaged(sort);
    }

    @Override
    public boolean isPaged() {
        return false;
    }

    public Pageable previousOrFirst() {
        return this;
    }

    public Pageable next() {
        return this;
    }

    public boolean hasPrevious() {
        return false;
    }

    public Sort getSort() {
        return sort;
    }

    public int getPageSize() {
        throw new UnsupportedOperationException();
    }

    public int getPageNumber() {
        throw new UnsupportedOperationException();
    }

    public long getOffset() {
        throw new UnsupportedOperationException();
    }

    public Pageable first() {
        return this;
    }

    @Override
    public Pageable withPage(int pageNumber) {
        return null;
    }
}
