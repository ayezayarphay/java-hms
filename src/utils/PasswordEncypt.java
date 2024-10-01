package utils;

import java.util.Base64;

public class PasswordEncypt {
	public static void main(String[] args) {
		String password = "iwkalocuse";
		String encrypt = Base64.getEncoder().encodeToString(password.getBytes());
		System.out.println(encrypt.length());

		byte a[] = Base64.getDecoder().decode(encrypt);
		System.out.println(new String(a));
	}

	public static String encode(String password) {
		return Base64.getEncoder().encodeToString(password.getBytes());
	}

	public static String decode(String encrypt) {
		return new String(Base64.getDecoder().decode(encrypt));
	}

}
