package com.ssafy.aboha.user.exception;

import com.ssafy.aboha.common.exception.ConflictException;

public class DuplicateEmailException extends ConflictException {

    public DuplicateEmailException(String message) {
        super(message);
    }
}
