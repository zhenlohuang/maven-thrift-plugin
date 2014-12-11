package org.apache.maven.plugin.exception;


public class ThriftCompileException extends Exception {

    public ThriftCompileException(String message) {
        super(message);
    }

    public ThriftCompileException(String message, Throwable cause) {
        super(message, cause);
    }

    public ThriftCompileException(String message, Exception cause) {
        super(message, cause);
    }
}
