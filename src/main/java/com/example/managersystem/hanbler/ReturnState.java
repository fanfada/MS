package com.example.managersystem.hanbler;

/**
 * 全局返回状态类
 */
public enum ReturnState {
    OK("OK"),
    ERROR("ERROR"),
    EXCEPTION("EXCEPTION"),
    FORBIDDEN("FORBIDDEN"),
    CHECK_EXCEPTION("CHECK_EXCEPTION"),
    INVALID_PARAMETER("INVALID_PARAMETER");


    private String value;

    private ReturnState(final String value) {

        this.value = value;
    }

    public String getValue() {

        return this.value;
    }

    public void setValue(final String value) {

        this.value = value;
    }

    @Override
    public String toString() {

        return this.value;
    }
}
