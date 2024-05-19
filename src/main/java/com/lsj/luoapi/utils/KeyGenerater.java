package com.lsj.luoapi.utils;

import com.lsj.luoapi.model.common.BusinessExecption;
import com.lsj.luoapi.model.common.ErrCode;
import com.lsj.luoapi.model.entity.User;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

public class KeyGenerater {

    public static class Keys {
        public String accessKey;
        public String secretKey;

        public Keys(String accessKey, String secretKey) {
            this.accessKey = accessKey;
            this.secretKey = secretKey;
        }
    }
    public static Keys generateAkSk(User user) {
        try {
            // 使用用户信息生成一个唯一的字符串
            String userInfoStr = String.join("|",
                    String.valueOf(user.getId()),
                    user.getUsername(),
                    user.getRoles()
            );

            // 生成一个随机的盐值
            byte[] salt = new byte[16];
            SecureRandom secureRandom = new SecureRandom();
            secureRandom.nextBytes(salt);

            // 生成 AK（Access Key），这里简单使用用户信息的哈希值
            MessageDigest sha256Digest = MessageDigest.getInstance("SHA-256");
            byte[] akHash = sha256Digest.digest(userInfoStr.getBytes(StandardCharsets.UTF_8));
            String accessKey = bytesToHex(akHash);

            // 使用 HMAC-SHA256 生成 SK（Secret Key）
            Mac hmacSha256 = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(salt, "HmacSHA256");
            hmacSha256.init(secretKeySpec);
            byte[] skHash = hmacSha256.doFinal(userInfoStr.getBytes(StandardCharsets.UTF_8));
            String secretKey = Base64.getUrlEncoder().encodeToString(skHash);

            return new Keys(accessKey, secretKey);

        } catch (Exception e) {
            throw new BusinessExecption(ErrCode.SYSTEM_ERROR, "Error generating AK/SK");
        }


    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
