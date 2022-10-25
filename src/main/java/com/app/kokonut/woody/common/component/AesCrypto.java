package com.app.kokonut.woody.common.component;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

public class AesCrypto {

	private static final String TRANSFORM = "AES/ECB/PKCS5Padding";
	
	public static String encrypt(String plainText, String key) throws Exception {
		if(plainText == null || plainText.isEmpty()) return plainText;
		
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		kgen.init(256);
		
		byte[] raw = key.getBytes();
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance(TRANSFORM);

		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		byte[] encrypted = cipher.doFinal(plainText.getBytes());
		return asHex(encrypted);
	}
	
	public static byte[] encrypt(String plainText, byte[] key) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		kgen.init(256);
		
//		byte[] raw = key.getBytes();
		SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
		Cipher cipher = Cipher.getInstance(TRANSFORM);

		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		byte[] encrypted = cipher.doFinal(plainText.getBytes());
		
		return encrypted;
	}

	public static String decrypt(String cipherText, String key) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		kgen.init(256);

		byte[] raw = key.getBytes();
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance(TRANSFORM);

		cipher.init(Cipher.DECRYPT_MODE, skeySpec);
		byte[] original = cipher.doFinal(fromString(cipherText));
		String originalString = new String(original);
		return originalString;
	}
	
	public static String decrypt(byte[] dec, byte[] key) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		kgen.init(256);

//		byte[] raw = key.getBytes();
		SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
		Cipher cipher = Cipher.getInstance(TRANSFORM);

		cipher.init(Cipher.DECRYPT_MODE, skeySpec);
		byte[] original = cipher.doFinal(dec);
		String originalString = new String(original);
		return originalString;
	}

	private static String asHex(byte buf[]) {
		StringBuffer strbuf = new StringBuffer(buf.length * 2);
		int i;

		for (i = 0; i < buf.length; i++) {
			if (((int) buf[i] & 0xff) < 0x10)
				strbuf.append("0");

			strbuf.append(Long.toString((int) buf[i] & 0xff, 16));
		}

		return strbuf.toString();
	}

	private static byte[] fromString(String hex) {
		int len = hex.length();
		byte[] buf = new byte[((len + 1) / 2)];

		int i = 0, j = 0;
		if ((len % 2) == 1)
			buf[j++] = (byte) fromDigit(hex.charAt(i++));

		while (i < len) {
			buf[j++] = (byte) ((fromDigit(hex.charAt(i++)) << 4) | fromDigit(hex
					.charAt(i++)));
		}
		return buf;
	}

	private static int fromDigit(char ch) {
		if (ch >= '0' && ch <= '9')
			return ch - '0';
		if (ch >= 'A' && ch <= 'F')
			return ch - 'A' + 10;
		if (ch >= 'a' && ch <= 'f')
			return ch - 'a' + 10;

		throw new IllegalArgumentException("invalid hex digit '" + ch + "'");
	}
	
	public static String lpda(String inVal, String charStr, int totalSize)
	{
		String rtVal = "";
		
		if(inVal == null) inVal = "";
		if(inVal.length() > totalSize)
		{
			rtVal = inVal.substring(0, totalSize);	
		}
		else
		{
			for(int i = 0 ; i < (totalSize - inVal.length()) ; i++)
			{
				rtVal = rtVal + charStr;
			}
			
			rtVal = rtVal + inVal;
		}
		
		return rtVal;
	}
	
    public static void main(String[] args) throws Exception 
    {
    	String target = "test";
    	String key = "123";
    	key = lpda(key, "*", 16);
    	
    	System.out.println("Target = " + target + ", Key = " + key);
    	
    	String encryptStr = encrypt(target, key);
    	System.out.println("Encrypted Text = " + encryptStr);
    	
    	String decryptStr = decrypt(encryptStr, key);
        System.out.println("Decrypted Text : " + decryptStr);
    }
}
