package com.alianza.clients.commons.exception;

import com.alianza.clients.commons.domains.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class ErrorException extends ClientsRuntimeException {
    public ErrorException(ErrorCode code) {
        super(code);
    }

}
