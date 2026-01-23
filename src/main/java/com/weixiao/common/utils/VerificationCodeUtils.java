package com.weixiao.common.utils;

import java.util.Random;

/**
 * 验证码工具类
 */
public class VerificationCodeUtils {
    
    private static final String NUMERIC_CHARS = "0123456789";
    private static final String LETTER_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String ALPHANUMERIC_CHARS = NUMERIC_CHARS + LETTER_CHARS;
    
    private static final Random RANDOM = new Random();
    
    /**
     * 生成数字验证码
     * @param length 验证码长度
     * @return 数字验证码
     */
    public static String generateNumericCode(int length) {
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < length; i++) {
            code.append(NUMERIC_CHARS.charAt(RANDOM.nextInt(NUMERIC_CHARS.length())));
        }
        return code.toString();
    }
    
    /**
     * 生成字母验证码
     * @param length 验证码长度
     * @return 字母验证码
     */
    public static String generateLetterCode(int length) {
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < length; i++) {
            code.append(LETTER_CHARS.charAt(RANDOM.nextInt(LETTER_CHARS.length())));
        }
        return code.toString();
    }
    
    /**
     * 生成字母数字验证码
     * @param length 验证码长度
     * @return 字母数字验证码
     */
    public static String generateAlphanumericCode(int length) {
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < length; i++) {
            code.append(ALPHANUMERIC_CHARS.charAt(RANDOM.nextInt(ALPHANUMERIC_CHARS.length())));
        }
        return code.toString();
    }
}