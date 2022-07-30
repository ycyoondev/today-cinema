package com.todaycinema.v2.config.exception;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public enum ExceptionEnum {
    // common exception
    RUNTIME_EXCEPTION(HttpStatus.BAD_REQUEST, "E0001"), // 400
    ACCESS_DENIED_EXCEPTION(HttpStatus.UNAUTHORIZED, "E0002"), // 401
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "E0003"), // 500
    VALIDATION_FAIL_EXCEPTION(HttpStatus.BAD_REQUEST, "E0004"), // 500
    // custom exception
    NO_SEARCH_RESOURCE(HttpStatus.NOT_FOUND, "C0001", "ID를 찾을 수 없습니다."), // 404
    NO_TARGET_NAME(HttpStatus.UNAUTHORIZED, "C0002", "대상이 없습니다."), // 401
    ALREADY_PROCESSED(HttpStatus.BAD_REQUEST, "C0003", "이미 처리된 요청입니다."), // 400
    ALREADY_WITHDRAWN(HttpStatus.FORBIDDEN, "C0004", "권한이 없습니다. (탈퇴한 회원)"); // 403

    private final HttpStatus status;
    private final String code;
    private String message;

    ExceptionEnum(HttpStatus status, String code) {
        this.status = status;
        this.code = code;
    }

    ExceptionEnum(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
