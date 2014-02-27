package com.appsack.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import org.apache.catalina.util.HexUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HashUtil {
	private static Log logger = LogFactory.getLog(HashUtil.class);

	public static final String SALT_1 = "EKJH(*&^$HF^FH*VCHC^&";
	public static final String SALT_2 = "98daghwiubdw78gcisuhdewr33423d";

	private static MessageDigest md;

	private static Base64 encoder;

	/**
	 * @return the salt
	 */
	private static String getSalt() {
		return SALT_1;
	}

	/**
	 * @return the md
	 */
	private static MessageDigest getMd() {
		if (md == null) {
			try {
				setMd(MessageDigest.getInstance("MD5"));
			} catch (NoSuchAlgorithmException e) {
				logger.error(e);
			}
		}
		return md;
	}

	/**
	 * @param md
	 *            the md to set
	 */
	private static void setMd(MessageDigest md) {
		HashUtil.md = md;
	}

	private static Base64 getEncoder() {

		if (encoder == null) {

			setEncoder(new Base64());
		}

		return encoder;
	}

	private static void setEncoder(Base64 encoder) {
		HashUtil.encoder = encoder;
	}

	/**
	 * Generate an automatic hash. It's a combination of the salt plus the
	 * current timestamp
	 * 
	 * @return
	 */
	public static String generateHash() {
		Date date = new Date();
		return HashUtil.generateHash(date.getTime() + "");
	}

	/**
	 * Generate a hash value using the salt plus the given value
	 * 
	 * @param value
	 * @return
	 */
	public static String generateHash(String value) {
		if (value == null) {
			Date date = new Date();
			value = date.getTime() + "";
		}

		String saltedValue = value + getSalt();

		byte[] raw = null;
		try {
			raw = getMd().digest(saltedValue.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			logger.error(e);
		}
		return HexUtils.convert(raw);
	}

	/**
	 * Generate a hash value using the salt plus the given value
	 * 
	 * @param value
	 * @return
	 */
	public static String generateHash(String value, String salt) {

		String encryptedValue, saltedValue = value + salt + getSalt();

		byte[] raw = null;
		try {
			raw = getMd().digest(saltedValue.getBytes("UTF-8"));

			encryptedValue = new String(getEncoder().encode(raw));

			return encryptedValue;
		} catch (UnsupportedEncodingException e) {
			logger.error(e);
		}
		return HexUtils.convert(raw);
	}

	public static String getEncryptedPassword(String password, Date insertedDate) {

		if (password == null)
			return null;

		String saltedPassword = password + insertedDate.toString() + SALT_2;

		try {
			String encryptedPassword = saltedPassword;
			for (int i = 0; i < 1000; i++) {

				byte[] raw = getMd().digest(encryptedPassword.getBytes("UTF-8"));
				encryptedPassword = new String(getEncoder().encode(raw));
			}
			return encryptedPassword;
		} catch (UnsupportedEncodingException e) {
			// logger.error(e);
			throw new RuntimeException(e.getMessage());
		}

	}
}
