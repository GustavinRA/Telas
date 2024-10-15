package com.example.telas.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Util {
    public static String converteMD5(String senha) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest codificador = MessageDigest.getInstance("MD5");
        byte[] array = codificador.digest(senha.getBytes("UTF-8"));
        StringBuffer senhaEmMD5 = new StringBuffer();
        for (int i=0; i< array.length; i++){
            senhaEmMD5.append(Integer.toHexString(array[i] & 0xFF | 0X100).substring(1,3));
        }
        return senhaEmMD5.toString();
    }
}