package ru.ki.service.exception;

import org.apache.commons.lang3.exception.ExceptionUtils;

import java.text.MessageFormat;

/**
 * @author ikozar
 * date    23.06.13
 */
public class ExceptionWithCode extends RuntimeException {

    private ExceptionCode exceptionCode;

    public ExceptionWithCode(Throwable cause, ExceptionCode exceptionCode) {
        super(ExceptionUtils.getRootCauseMessage(cause));
        this.exceptionCode = exceptionCode;
    }

    public ExceptionWithCode(ExceptionCode exceptionCode, String message, String ... param) {
        super(MessageFormat.format(message, param));
        this.exceptionCode = exceptionCode;
    }

    public ExceptionWithCode(ExceptionCode exceptionCode) {
        super(exceptionCode.getTitle());
        this.exceptionCode = exceptionCode;
    }

    public ExceptionCode getExceptionCode() {
        return exceptionCode;
    }
}
