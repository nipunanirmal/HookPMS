package com.huruwo.pmshook.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.security.MessageDigest;

public class SignatureUtils {
    /**
     * 签名获取
     */
    public static void getSignature(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            LogXUtils.e("len:"+packageInfo.signatures.length);
            if (packageInfo.signatures != null) {
                LogXUtils.e("sig:"+packageInfo.signatures[0].toCharsString());
                LogXUtils.e( "sig md5:"+ printHexBinary(MessageDigest.getInstance("md5").digest(packageInfo.signatures[0].toCharsString().getBytes())));

            }
        } catch (Exception e) {
        }
    }

    private static final char[] hexCode = "0123456789ABCDEF".toCharArray();

    public static String printHexBinary(byte[] data) {
        StringBuilder r = new StringBuilder(data.length * 2);
        for (byte b : data) {
            r.append(hexCode[(b >> 4) & 0xF]);
            r.append(hexCode[(b & 0xF)]);
        }
        return r.toString();
    }
}
