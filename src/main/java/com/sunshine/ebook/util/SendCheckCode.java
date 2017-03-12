package com.sunshine.ebook.util;

import java.util.Random;

/**
 * 发送验证码工具类
 */
public class SendCheckCode {

    //邮箱验证码的长度
    private static final int EMAIL_CHECK_CODE_SIZE = 8;

    //手机验证码的长度
    private static final int PHONE_CHECK_CODE_SIZE = 6;

    public static String getCheckCode(int type) {
        //type=0手机注册，type=1邮箱注册
        int codeSize = 0;
        if (0 == type) {
            codeSize = PHONE_CHECK_CODE_SIZE;
        } else if (1 == type) {
            codeSize = EMAIL_CHECK_CODE_SIZE;
        }
        String checkCode = "";
        Random random = new Random();
        for (int i = 0; i < codeSize; i++) {
            String charOrNum = "num";
            if (1 == type) {
                charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            }
            if ("char".equalsIgnoreCase(charOrNum)) {
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                checkCode += (char)(random.nextInt(26) + temp);
            } else {
                checkCode += String.valueOf(random.nextInt(10));
            }
        }
        return checkCode;
    }

    public static void main(String args[]) {
        String code = SendCheckCode.getCheckCode(1);
        System.out.println(code);
    }

}
