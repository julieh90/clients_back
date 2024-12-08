package com.alianza.clients.commons.exception;

import com.alianza.clients.commons.domains.ErrorCode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = true)
@Getter
@Data
public class ClientsRuntimeException extends RuntimeException {
    private final ErrorCode code;

    public ClientsRuntimeException(ErrorCode code) {
        super(code.getMessage());
        this.code = code;
    }

    public ClientsRuntimeException(ErrorCode code, Throwable cause) {
        super(code.getMessage(), cause);
        this.code = code;
    }

    public ClientsRuntimeException(String message) {
        super(message);
        this.code = null;
    }
}

