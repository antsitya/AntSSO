package com.ant.sso.Utils;

import java.math.BigInteger;
import java.security.MessageDigest;

public class Md5Util {

    /**
     *  hash运算md5生成hash值
     */
    public static String generateHash(String input){
        try {
            //参数校验
            if (null == input) {
                return null;
            }
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(input.getBytes());
            byte[] digest = md.digest();
            BigInteger bi = new BigInteger(1, digest);
            String hashText = bi.toString(16);
            while(hashText.length() < 32){
                hashText = "0" + hashText;
            }
            return hashText;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
