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

public class AwsV4Signer {

    private static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";
    private static final String SCHEME = "AWS4";
    private static final String ALGORITHM = "AWS4-HMAC-SHA256";
    private static final String TERMINATOR = "aws4_request";

    public static String generateSignature(HttpServletRequest request, String accessKey, String secretKey, String region, String service) throws Exception {
        String date = getDateString();
        String dateStamp = date.substring(0, 8);

        String canonicalRequest = createCanonicalRequest(request);
        String credentialScope = String.format("%s/%s/%s/%s", dateStamp, region, service, TERMINATOR);
        String stringToSign = buildStringToSign(canonicalRequest, credentialScope, date);
        byte[] signingKey = getSignatureKey(secretKey, dateStamp, region, service);
        byte[] signatureBytes = hmacSHA256(signingKey, stringToSign);
        String signature = bytesToHex(signatureBytes);

        String signedHeaders = getSignedHeaders(request);

        return String.format("%s Credential=%s/%s, SignedHeaders=%s, Signature=%s",
                ALGORITHM, accessKey, credentialScope, signedHeaders, signature);
    }

    private static String createCanonicalRequest(HttpServletRequest request) throws Exception {
        String method = request.getMethod();
        String canonicalURI = request.getRequestURI();
        String canonicalQueryString = getCanonicalQueryString(request);
        String canonicalHeaders = getCanonicalHeaders(request);
        String signedHeaders = getSignedHeaders(request);
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

    private static String getCanonicalHeaders(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        List<String> headers = Collections.list(headerNames);
        headers.sort(String.CASE_INSENSITIVE_ORDER);

        StringBuilder canonicalHeaders = new StringBuilder();
        for (String header : headers) {
            String value = request.getHeader(header).trim().replaceAll("\\s+", " ");
            canonicalHeaders.append(header.toLowerCase()).append(":").append(value).append("\n");
        }
        return canonicalHeaders.toString();
    }

    private static String getSignedHeaders(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        List<String> headers = Collections.list(headerNames);
        headers.sort(String.CASE_INSENSITIVE_ORDER);

        return headers.stream()
                .map(String::toLowerCase)
                .collect(Collectors.joining(";"));
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

    public static String getDateString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat.format(new Date());
    }

    public static void main(String[] args) throws Exception {
        // 示例使用HttpServletRequest创建请求
        // HttpServletRequest request = ...
        String accessKey = "yourAccessKey";
        String secretKey = "yourSecretKey";
        String region = "us-east-1";
        String service = "s3";

        // 生成签名并获取Authorization头
        // String authorizationHeader = generateSignature(request, accessKey, secretKey, region, service);
        // System.out.println("Authorization Header: " + authorizationHeader);
    }
}
