package org.server.services;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import javax.xml.bind.DatatypeConverter;

public class PasswordEncryptionService {
    public static boolean checkPassword(String attemptedPassword, String encryptedPassword, String salt) {
        if (attemptedPassword == null || encryptedPassword == null || salt == null) return false;
        return encryptedPassword.equals(getEncryptedPassword(attemptedPassword, salt));
    }

    public static String getEncryptedPassword(String password, String salt) {
        try {
            KeySpec spec = new PBEKeySpec(password.toCharArray(), DatatypeConverter.parseHexBinary(salt), 20000, 160);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

            return DatatypeConverter.printHexBinary(factory.generateSecret(spec).getEncoded());
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String generateSalt() {
        try {
            byte[] salt = new byte[8];
            SecureRandom.getInstance("SHA1PRNG").nextBytes(salt);

            return DatatypeConverter.printHexBinary(salt);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}