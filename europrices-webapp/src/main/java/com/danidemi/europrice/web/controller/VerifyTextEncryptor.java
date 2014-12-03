package com.danidemi.europrice.web.controller;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.keygen.KeyGenerators;
public class VerifyTextEncryptor {
		
	public static void main(String[] args) {
//		<bean class="org.springframework.security.crypto.encrypt.HexEncodingTextEncryptor">
//			<constructor-arg>
//				<bean class="org.springframework.security.crypto.encrypt.AesBytesEncryptor">
//					<constructor-arg value="aes-password" />
//					<constructor-arg value="ABCD1234" />
//					</bean>
//			</constructor-arg>
//		</bean>
		
		// new org.springframework.security.crypto.encrypt.HexEncodingTextEncryptor();
		
		CharSequence password = (CharSequence)"01234567890123456";
		CharSequence hexEncodedSalt = new String(new Hex().encode("Salt".getBytes()));
		
		String password2 = "password";
		//String validSalt = KeyGenerators.string().generateKey();
		String validSalt = new String(new Hex().encode("01234567".getBytes()));

		System.out.println(password2);
		System.out.println(validSalt);
		
		
		Encryptors.queryableText(password2, validSalt);
		
		
//		TextEncryptor te = Encryptors.queryableText(password, hexEncodedSalt);
//		te.encrypt("wdwjdowjdojwdowj");
		
		
	}
}
