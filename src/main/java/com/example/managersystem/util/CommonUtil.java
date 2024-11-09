package com.example.managersystem.util;


import com.example.managersystem.common.GlobalConstants;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author fanfada
 * @date 2024/11/08 15:19
 */
public class CommonUtil {

    public static boolean isIgnoredURL(String url) {
        // 遍历INGORE_URLS，检查url是否包含其中的任意一个元素
        for (String ignoreUrl : GlobalConstants.INGORE_URLS) {
            if (url.contains(ignoreUrl)) {
                return true;  // 如果url包含ignoreUrl中的某个字符串，返回true
            }
        }
        return false;  // 如果url不包含任何一个忽略的URL，返回false
    }


    // 校验手机号码格式的方法
    public static boolean isValidPhoneNumber(String phoneNumber) {
        // 定义正则表达式，校验中国大陆的手机号格式
        String regex = "^1[3-9]\\d{9}$";
        // 创建Pattern对象
        Pattern pattern = Pattern.compile(regex);
        // 创建matcher对象
        Matcher matcher = pattern.matcher(phoneNumber);
        // 返回验证结果
        return matcher.matches();
    }


    // 校验邮箱格式的方法
    public static boolean isValidEmail(String email) {
        // 定义正则表达式，校验常见的邮箱格式
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        // 创建Pattern对象
        Pattern pattern = Pattern.compile(regex);
        // 创建matcher对象
        Matcher matcher = pattern.matcher(email);
        // 返回验证结果
        return matcher.matches();
    }

    public static void main(String[] args) {
        // 测试邮箱
        String email = "18751889883@163.com";
        if (isValidEmail(email)) {
            System.out.println("邮箱格式有效");
        } else {
            System.out.println("邮箱格式无效");
        }
    }

}
