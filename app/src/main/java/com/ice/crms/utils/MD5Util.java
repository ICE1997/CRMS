package com.ice.crms.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
    public static String toMD5L32(String s){
        String result = null;
        if(s.isEmpty()){
            return null;
        }
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] byt = s.getBytes();
            md.update(byt);
            byte[] bytresult = md.digest();
            StringBuilder sb = new StringBuilder();
            for(byte b : bytresult) {
                int bt = b & 0xff;
                if(bt < 16 ){
                    sb.append(0);
                }
                sb.append(Integer.toHexString(bt));
            }
            result = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }
}
