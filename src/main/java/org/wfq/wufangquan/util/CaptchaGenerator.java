package org.wfq.wufangquan.util;

import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

public class CaptchaGenerator {
    // 生成指定长度的数字验证码
    public static String generateNumericCaptcha(int length) {
        StringBuilder captcha = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            captcha.append(random.nextInt(10)); // 生成0-9之间的数字
        }

        return captcha.toString();
    }

    public static String generateHexSalt(int hexLength) {
        int byteLength = hexLength / 2; // 每个字节 = 2 位 hex
        byte[] salt = new byte[byteLength];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(salt);

        // 转为 Hex 字符串
        StringBuilder sb = new StringBuilder();
        for (byte b : salt) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static String generateUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
