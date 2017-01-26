package com.internship.droidz.talkin.data.web;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Novak Alexandr on 23.01.2017.
 */

public class WebUtils {

    private static Random randomizer = new Random();
    public static String calcSignature(int nonce, Long timestamp) {
        try {
            String data = composeParametrs(nonce, timestamp);
            SecretKeySpec key = new SecretKeySpec((ApiRetrofit.APP_SECRET).getBytes("UTF-8"), "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(key);
            byte[] bytes = mac.doFinal(data.getBytes("UTF-8"));
            return new String(Base64.encode(bytes, Base64.DEFAULT));
        } catch (NoSuchAlgorithmException  | UnsupportedEncodingException | InvalidKeyException ex ) {
            ex.printStackTrace();
        }

        return null;
    }

    public static Integer getNonce()
    {
        return randomizer.nextInt();
    }



    private static String composeParametrs(int nonce, long timestamp) {
        StringBuilder sb = new StringBuilder("");
        sb.append("application_id=").append(ApiRetrofit.APP_ID);
        sb.append("&auth_key=").append(ApiRetrofit.APP_AUTH_KEY);
        sb.append("&nonce=").append(nonce);
        sb.append("&timestamp=").append(timestamp);
        return sb.toString();
    }
}
