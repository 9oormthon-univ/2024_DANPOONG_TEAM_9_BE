package com.goorm.LocC.member.exception;

import com.goorm.LocC.global.exception.BaseException;
import lombok.Getter;

@Getter
public class MemberException extends BaseException {

    public MemberException(MemberErrorCode errorCode) {
        super(errorCode);
    }
}
