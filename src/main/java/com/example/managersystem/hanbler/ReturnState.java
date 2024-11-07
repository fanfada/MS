/**
 * Copyright 2014 China Mobile. All Right Reserved
 * <p>
 * This file is owned by China Mobile and you may not use, modify, copy,
 * redistribute this file without written permissions.
 * <p>
 * These Terms of Use define legal use of this file, all updates, revisions,
 * substitutions for you. All rights not expressly granted to you are reserved
 * by China Mobile.
 */
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
