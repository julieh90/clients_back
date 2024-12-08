package com.alianza.clients.commons.domains;

import lombok.Getter;


    @Getter
    public enum ErrorCode {

        DATA_NOT_FOUND("data not found"),
        ERROR_GENERIC("Error to save"),
        FILE_NOT_FOUND("file not found"),
        ERROR_FILE("Error to generate file"),
        ERROR_CLIENT("Client already exists, not saved.");

        private final String message;

        ErrorCode(String message) {
            this.message = message;
        }

    }

