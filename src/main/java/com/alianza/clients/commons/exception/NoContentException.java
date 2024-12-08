package com.alianza.clients.commons.exception;

import com.alianza.clients.commons.domains.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NO_CONTENT)
public class NoContentException extends ClientsRuntimeException {
    public NoContentException(ErrorCode code) {
        super(code);
    }

}
