package com.lsj.luoapi.utils;

import jakarta.servlet.http.HttpServletRequest;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class AwsV4Verifier {

    private static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";
    private static final String SCHEME = "AWS4";
    private static final String ALGORITHM = "AWS4-HMAC-SHA256";
    private static final String TERMINATOR = "aws4_request";

    // Mock method to get SK from AK (Replace with actual implementation)
    private static String getSecretKey(String accessKey) {
        // In a real scenario, you would fetch the secret key from a secure storage
        // This is a mock implementation
        if ("yourAccessKey".equals(accessKey)) {
            return "yourSecretKey";
        }
        throw new IllegalArgumentException("Invalid Access Key");
    }

    public static boolean verifySignature(HttpServletRequest request, String authorizationHeader) throws Exception {
        String[] authParts = authorizationHeader.split(" ");
        if (authParts.length != 2 || !ALGORITHM.equals(authParts[0])) {
            throw new IllegalArgumentException("Invalid Authorization header");
        }

        Map<String, String> authParams = parseAuthorizationHeader(authParts[1]);
        String accessKey = authParams.get("Credential").split("/")[0];
        String signedHeaders = authParams.get("SignedHeaders");
        String signature = authParams.get("Signature");

        String secretKey = getSecretKey(accessKey);

        String date = request.getHeader("x-amz-date");
        String region = "us-east-1";  // This should be parsed or predefined
        String service = "s3";        // This should be parsed or predefined

        String canonicalRequest = createCanonicalRequest(request, signedHeaders);
        String credentialScope = String.format("%s/%s/%s/%s", date.substring(0, 8), region, service, TERMINATOR);
        String stringToSign = buildStringToSign(canonicalRequest, credentialScope, date);
        byte[] signingKey = getSignatureKey(secretKey, date.substring(0, 8), region, service);
        byte[] signatureBytes = hmacSHA256(signingKey, stringToSign);
        String expectedSignature = bytesToHex(signatureBytes);

        return expectedSignature.equals(signature);
    }

    private static String createCanonicalRequest(HttpServletRequest request, String signedHeaders) throws Exception {
        String method = request.getMethod();
        String canonicalURI = request.getRequestURI();
        String canonicalQueryString = getCanonicalQueryString(request);
        String canonicalHeaders = getCanonicalHeaders(request, signedHeaders);
        String payloadHash = hash(getRequestBody(request));

        return String.join("\n",
                method,
                canonicalURI,
                canonicalQueryString,
                canonicalHeaders,
                signedHeaders,
                payloadHash
        );
    }

    private static String getCanonicalQueryString(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        return parameterMap.keySet().stream()
                .sorted()
                .map(key -> key + "=" + Arrays.stream(parameterMap.get(key)).sorted().collect(Collectors.joining(",")))
                .collect(Collectors.joining("&"));
    }

    private static String getCanonicalHeaders(HttpServletRequest request, String signedHeaders) {
        List<String> headers = Arrays.asList(signedHeaders.split(";"));
        StringBuilder canonicalHeaders = new StringBuilder();
        for (String header : headers) {
            String value = request.getHeader(header).trim().replaceAll("\\s+", " ");
            canonicalHeaders.append(header.toLowerCase()).append(":").append(value).append("\n");
        }
        return canonicalHeaders.toString();
    }

    private static String getRequestBody(HttpServletRequest request) throws Exception {
        if ("POST".equalsIgnoreCase(request.getMethod()) || "PUT".equalsIgnoreCase(request.getMethod())) {
            StringBuilder body = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    body.append(line);
                }
            }
            return body.toString();
        }
        return "";
    }

    private static String buildStringToSign(String canonicalRequest, String credentialScope, String date) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(canonicalRequest.getBytes(StandardCharsets.UTF_8));
        String hashedCanonicalRequest = bytesToHex(md.digest());

        return String.join("\n",
                ALGORITHM,
                date,
                credentialScope,
                hashedCanonicalRequest
        );
    }

    private static byte[] getSignatureKey(String key, String dateStamp, String regionName, String serviceName) throws Exception {
        byte[] kDate = hmacSHA256(("AWS4" + key).getBytes(StandardCharsets.UTF_8), dateStamp);
        byte[] kRegion = hmacSHA256(kDate, regionName);
        byte[] kService = hmacSHA256(kRegion, serviceName);
        return hmacSHA256(kService, TERMINATOR);
    }

    private static byte[] hmacSHA256(byte[] key, String data) throws Exception {
        Mac mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
        mac.init(new SecretKeySpec(key, HMAC_SHA256_ALGORITHM));
        return mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
    }

    private static String hash(String data) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(data.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(md.digest());
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

    private static Map<String, String> parseAuthorizationHeader(String authorizationHeader) {
        Map<String, String> params = new HashMap<>();
        String[] parts = authorizationHeader.split(", ");
        for (String part : parts) {
            String[] keyValue = part.split("=");
            params.put(keyValue[0], keyValue[1]);
        }
        return params;
    }

    public static void main(String[] args) throws Exception {
        // 示例使用HttpServletRequest创建请求
        // HttpServletRequest request = ...
        String authorizationHeader = "AWS4-HMAC-SHA256 Credential=yourAccessKey/20230512/us-east-1/s3/aws4_request, SignedHeaders=host;x-amz-date, Signature=abcd1234";
        
        // Verify the signature
        // boolean isValid = verifySignature(request, authorizationHeader);
        // System.out.println("Is signature valid: " + isValid);
    }
}
