
package com.mycompany.onlinebankapi.service;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * Sources:
 *	https://github.com/defuse/password-hashing/blob/master/PasswordStorage.java
 *	http://www.adeveloperdiary.com/java/how-to-easily-encrypt-and-decrypt-text-in-java/
 *	https://stackoverflow.com/questions/9098022/problems-converting-byte-array-to-string-and-back-to-byte-array
 */
public class Hasher {

	public static final String FAILED_ENC = "ENCRYPTION_FAILED";
	public static final int FAILED_DEC = -1;
	
	private static final String ENCODING = "ISO-8859-1";
	private static final String PADDING = "#";
	private static Key aesKey;
	private static Cipher cipher;
	
	static {
		aesKey = new SecretKeySpec("#&Df8[n6%z0xVeEi".getBytes(), "AES");
		try {
			cipher = Cipher.getInstance("AES");
		} catch (Exception e) {}
	}
	
	public static String encryptId(int id) {
		String text = Integer.toString(id);
		int toLoop = 16 - (text.length() % 16);
		for(int i = 0; i < toLoop; i++)
			text+=PADDING;
		try {
			cipher.init(Cipher.ENCRYPT_MODE, aesKey);
			byte[] encrypted = cipher.doFinal(text.getBytes());
//			return new String(encrypted, ENCODING);
			return toBase64(encrypted);
		} catch (Exception ex) {
			return text;
		}
	}
	
	public static int decryptId(String encrypted) {
		try {
			cipher.init(Cipher.DECRYPT_MODE, aesKey);
//			String decrypted = new String(cipher.doFinal(encrypted.getBytes(ENCODING))).replaceAll(PADDING, "");
			String decrypted = new String(cipher.doFinal(fromBase64(encrypted))).replaceAll(PADDING, "");
			return Integer.parseInt(decrypted);
		} catch (Exception e) {
			return FAILED_DEC;
		}
	}
	
//	public static String encryptId(int id) throws Exception {
//		String strData = "";
//
//		try {
//			SecretKeySpec skeyspec = new SecretKeySpec(KEY, "Blowfish");
//			Cipher cipher = Cipher.getInstance("Blowfish");
//			cipher.init(Cipher.ENCRYPT_MODE, skeyspec);
//			byte[] encrypted = cipher.doFinal(Integer.toString(id).getBytes());
//			strData = new String(encrypted);
//			return strData;
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new Exception(e);
//		}
//	}
//	
//	public static int decryptId(String encryptedId) throws Exception {
//		String strData = "";
//
//		try {
//			SecretKeySpec skeyspec = new SecretKeySpec(KEY, "Blowfish");
//			Cipher cipher = Cipher.getInstance("Blowfish");
//			cipher.init(Cipher.DECRYPT_MODE, skeyspec);
//			byte[] decrypted = cipher.doFinal(encryptedId.getBytes());
//			strData = new String(decrypted);
//			return Integer.parseInt(strData);
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new Exception(e);
//		}
//	}

	    @SuppressWarnings("serial")
    static public class InvalidHashException extends Exception {
        public InvalidHashException(String message) {
            super(message);
        }
        public InvalidHashException(String message, Throwable source) {
            super(message, source);
        }
    }

    @SuppressWarnings("serial")
    static public class CannotPerformOperationException extends Exception {
        public CannotPerformOperationException(String message) {
            super(message);
        }
        public CannotPerformOperationException(String message, Throwable source) {
            super(message, source);
        }
    }

    public static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA1";

    // These constants may be changed without breaking existing hashes.
    public static final int SALT_BYTE_SIZE = 24;
    public static final int HASH_BYTE_SIZE = 18;
    public static final int PBKDF2_ITERATIONS = 64000;

    // These constants define the encoding and may not be changed.
    public static final int HASH_SECTIONS = 5;
    public static final int HASH_ALGORITHM_INDEX = 0;
    public static final int ITERATION_INDEX = 1;
    public static final int HASH_SIZE_INDEX = 2;
    public static final int SALT_INDEX = 3;
    public static final int PBKDF2_INDEX = 4;

    public static String createHash(String password)
        throws CannotPerformOperationException
    {
        return createHash(password.toCharArray());
    }

    public static String createHash(char[] password)
        throws CannotPerformOperationException
    {
        // Generate a random salt
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_BYTE_SIZE];
        random.nextBytes(salt);

        // Hash the password
        byte[] hash = pbkdf2(password, salt, PBKDF2_ITERATIONS, HASH_BYTE_SIZE);
        int hashSize = hash.length;

        // format: algorithm:iterations:hashSize:salt:hash
        String parts = "sha1:" +
            PBKDF2_ITERATIONS +
            ":" + hashSize +
            ":" +
            toBase64(salt) +
            ":" +
            toBase64(hash);
        return parts;
    }

    public static boolean verifyPassword(String password, String correctHash)
        throws CannotPerformOperationException, InvalidHashException
    {
        return verifyPassword(password.toCharArray(), correctHash);
    }

    public static boolean verifyPassword(char[] password, String correctHash)
        throws CannotPerformOperationException, InvalidHashException
    {
        // Decode the hash into its parameters
        String[] params = correctHash.split(":");
        if (params.length != HASH_SECTIONS) {
            throw new InvalidHashException(
                "Fields are missing from the password hash."
            );
        }

        // Currently, Java only supports SHA1.
        if (!params[HASH_ALGORITHM_INDEX].equals("sha1")) {
            throw new CannotPerformOperationException(
                "Unsupported hash type."
            );
        }

        int iterations = 0;
        try {
            iterations = Integer.parseInt(params[ITERATION_INDEX]);
        } catch (NumberFormatException ex) {
            throw new InvalidHashException(
                "Could not parse the iteration count as an integer.",
                ex
            );
        }

        if (iterations < 1) {
            throw new InvalidHashException(
                "Invalid number of iterations. Must be >= 1."
            );
        }


        byte[] salt = null;
        try {
            salt = fromBase64(params[SALT_INDEX]);
        } catch (IllegalArgumentException ex) {
            throw new InvalidHashException(
                "Base64 decoding of salt failed.",
                ex
            );
        }

        byte[] hash = null;
        try {
            hash = fromBase64(params[PBKDF2_INDEX]);
        } catch (IllegalArgumentException ex) {
            throw new InvalidHashException(
                "Base64 decoding of pbkdf2 output failed.",
                ex
            );
        }


        int storedHashSize = 0;
        try {
            storedHashSize = Integer.parseInt(params[HASH_SIZE_INDEX]);
        } catch (NumberFormatException ex) {
            throw new InvalidHashException(
                "Could not parse the hash size as an integer.",
                ex
            );
        }

        if (storedHashSize != hash.length) {
            throw new InvalidHashException(
                "Hash length doesn't match stored hash length."
            );
        }

        // Compute the hash of the provided password, using the same salt, 
        // iteration count, and hash length
        byte[] testHash = pbkdf2(password, salt, iterations, hash.length);
        // Compare the hashes in constant time. The password is correct if
        // both hashes match.
        return slowEquals(hash, testHash);
    }

    private static boolean slowEquals(byte[] a, byte[] b)
    {
        int diff = a.length ^ b.length;
        for(int i = 0; i < a.length && i < b.length; i++)
            diff |= a[i] ^ b[i];
        return diff == 0;
    }

    private static byte[] pbkdf2(char[] password, byte[] salt, int iterations, int bytes)
        throws CannotPerformOperationException
    {
        try {
            PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, bytes * 8);
            SecretKeyFactory skf = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException ex) {
            throw new CannotPerformOperationException(
                "Hash algorithm not supported.",
                ex
            );
        } catch (InvalidKeySpecException ex) {
            throw new CannotPerformOperationException(
                "Invalid key spec.",
                ex
            );
        }
    }

    private static byte[] fromBase64(String hex)
        throws IllegalArgumentException
    {
        return DatatypeConverter.parseBase64Binary(hex);
    }

    private static String toBase64(byte[] array)
    {
        return DatatypeConverter.printBase64Binary(array);
	}
	
}
