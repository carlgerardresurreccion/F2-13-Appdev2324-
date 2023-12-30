package com.example.furryfound;

import java.security.SecureRandom;

public class SecureRandomIdGenerator {

    public static String generateSecureRandomId() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[16];
        secureRandom.nextBytes(randomBytes);

        // Convert bytes to a String
        String randomId = bytesToHex(randomBytes);

        return randomId;
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexStringBuilder = new StringBuilder();
        for (byte b : bytes) {
            hexStringBuilder.append(String.format("%02x", b));
        }
        return hexStringBuilder.toString();
    }
}
