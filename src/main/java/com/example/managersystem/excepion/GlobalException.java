package com.example.managersystem.excepion;

/**
 * 全局异常处理类
 */
public class GlobalException extends RuntimeException {

    private static final long serialVersionUID = 4556116344126917890L;

    protected String code;

    public GlobalException(final String message) {

        super(message);
    }

    public GlobalException(final String code, final String message) {

        super(message);
        this.code = code;
    }

    public GlobalException(final String message, final Throwable t) {

        super(message, t);
    }

    public GlobalException(final String code, final String message, final Throwable t) {

        super(message, t);
        this.code = code;
    }

    public String getCode() {

        return this.code;
    }
}
