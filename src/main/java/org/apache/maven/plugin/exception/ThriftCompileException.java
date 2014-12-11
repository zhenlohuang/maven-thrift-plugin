package org.apache.maven.plugin.exception;


public class ThriftCompileException extends RuntimeException{

    public ThriftCompileException() {
        super();
    }

    public ThriftCompileException(String message) {
        super(message);
    }

    public ThriftCompileException(String message, Throwable cause) {
        super(message, cause);
    }

    public ThriftCompileException(Throwable cause) {
        super(cause);
    }

    protected ThriftCompileException(String message, Throwable cause,
                               boolean enableSuppression,
                               boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
