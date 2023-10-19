package com.example.server.service;

import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class PasswordEncryptionService {

    public static String hashPassword(String password) {
        try {
            MessageDigest objMessDigest = MessageDigest.getInstance("SHA-512");
            byte[] passwordHashed = objMessDigest.digest(password.getBytes());

            StringBuilder hexStringBuilder = new StringBuilder();

            for (byte myByte : passwordHashed) {
                String hexValue = String.format("%02x", myByte);
                hexStringBuilder.append(hexValue);
            }

            return hexStringBuilder.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();

            return null;
        }
    }
}
