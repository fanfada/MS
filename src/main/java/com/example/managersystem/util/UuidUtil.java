package com.example.managersystem.util;

import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

public class UuidUtil {

    public static String uuid() {
        return StringUtils.replace(UUID.randomUUID().toString(), "-", "");
    }
}
