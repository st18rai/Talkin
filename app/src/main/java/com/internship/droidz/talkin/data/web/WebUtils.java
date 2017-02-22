package com.internship.droidz.talkin.data.web;

import android.util.Log;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Novak Alexandr on 23.01.2017.
 */

public class WebUtils {

    private static Random randomizer = new Random();

    public static Integer getNonce()
    {
        return randomizer.nextInt();
    }

    public static String calcSignature(int nonce, long timestamp)
    {
        String data = composeParametrs(nonce, timestamp);
        try {
            SecretKeySpec signingKey = new SecretKeySpec(ApiRetrofit.APP_SECRET.getBytes(),
                    "HmacSHA1");
            Mac mac = Mac.getInstance ("HmacSHA1");
            mac.init(signingKey);
            return toHexString(mac.doFinal(data.getBytes()));
        } catch(NoSuchAlgorithmException | InvalidKeyException ex){
            // TODO: 2/20/17 [Code Review] there's a very bad practice to leave catch clause empty
        }
        return null;
    }

    public static String calcSignature(int nonce, long timestamp,String email,String password)
    {
        String data = composeParametrs(nonce, timestamp,email,password);
        try {
            SecretKeySpec signingKey = new SecretKeySpec(ApiRetrofit.APP_SECRET.getBytes(),
                    "HmacSHA1");
            Mac mac = Mac.getInstance ("HmacSHA1");
            mac.init(signingKey);
            return toHexString(mac.doFinal(data.getBytes()));
        } catch(NoSuchAlgorithmException | InvalidKeyException ex){
            Log.i("Error","calcSignature: "+ex.getMessage());

        }
        return null;
    }

    private static String composeParametrs(int nonce, long timestamp) {
        StringBuilder sb = new StringBuilder("");
        sb.append("application_id=").append(ApiRetrofit.APP_ID);
        sb.append("&auth_key=").append(ApiRetrofit.APP_AUTH_KEY);
        sb.append("&nonce=").append(nonce);
        sb.append("&timestamp=").append(timestamp);
        return sb.toString();
    }

    private static String composeParametrs(int nonce, long timestamp,String email, String password) {
        StringBuilder sb = new StringBuilder("");
        sb.append("application_id=").append(ApiRetrofit.APP_ID);
        sb.append("&auth_key=").append(ApiRetrofit.APP_AUTH_KEY);
        sb.append("&nonce=").append(nonce);
        sb.append("&timestamp=").append(timestamp);
        sb.append("&user[email]=").append(email);
        sb.append("&user[password]=").append(password);
        return sb.toString();
    }

    private static String toHexString(byte[] bytes) {
        Formatter formatter = new Formatter();

        for (byte b : bytes) {
            formatter.format("%02x", b);
        }

        return formatter.toString();
    }

}
