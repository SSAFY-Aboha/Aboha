package com.ssafy.aboha.user.exception;

import com.ssafy.aboha.common.exception.ConflictException;

public class DuplicateNicknameException extends ConflictException {

    public DuplicateNicknameException(String message) {
        super(message);
    }
}
