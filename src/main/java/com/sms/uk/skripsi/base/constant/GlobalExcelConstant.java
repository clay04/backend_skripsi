package com.sms.uk.skripsi.base.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GlobalExcelConstant {

    public static final String EXCEL_FORMAT = ".xlsx";

    public static final String MEDIA_TYPE = "application/vnd.ms-excel";

    public static final String CONTENT_DISPOSITION = "attachment; filename=";

    public static final List<String> BASE_FIELD_COLUMNS_NAME = List.of("is_deleted", "updated_by");
}
