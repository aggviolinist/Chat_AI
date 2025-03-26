package com.apps.imageAI.components;

import android.os.Build;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class JavaUtils {

    public static String generateDeviceId() {
        String deviceIndustrialName = "";
        String deviceSerialNumber = "";
        String androidDeviceId = "";

        try {
            deviceSerialNumber = "" + Build.BOARD;
            deviceIndustrialName = "" + Build.DEVICE;
            androidDeviceId = "" + Build.ID + Build.MANUFACTURER + Build.MODEL + Build.PRODUCT;
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        UUID deviceUuid = new UUID((long)androidDeviceId.hashCode(), (long)deviceIndustrialName.hashCode() << 32 | (long)deviceSerialNumber.hashCode());
        return deviceUuid.toString();
    }

    public static String computeMD5Hash(String password) {
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(password.getBytes());
            byte messageDigest[] = digest.digest();

            StringBuffer MD5Hash = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {

                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2)
                    h = "0" + h;
                MD5Hash.append(h);
            }

            return MD5Hash.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
