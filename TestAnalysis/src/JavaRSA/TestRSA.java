package JavaRSA;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.Random;

import org.apache.commons.lang3.time.DateFormatUtils;

public class TestRSA {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		KeyPair keyPair = RSAUtils.generateKeyPair();
//		System.out.println(keyPair);
		
//		String string = DateFormatUtils.format(new Date(), "yyyyMMdd").toString();
//		System.out.println(string);
		
//		PublicKeyMap publicKeyMap = RSAUtils.getPublicKeyMap();
		
//		MyRSA myRSA = new MyRSA();
//		PublicKey publicKey = myRSA.getPublicKey();
//		byte[] publicKeyCode = myRSA.getPublicKeyBytes(publicKey);
//		for (byte b : publicKeyCode) {
//			System.out.println(b);
//		}
//		myRSA.printPublicKeyInfo2(publicKey);
//		System.out.println();
		
		RSATest rsa = new RSATest();
		String string = "encryptText";
	//	String string = "1238238437120";
		
		// Generate keys
		try {
			//获取密钥对
			KeyPair keyPair = rsa.generateKeyPair();
			//生成密钥和公钥
			RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
			RSAPublicKey  publicKey  = (RSAPublicKey)  keyPair.getPublic();
			
			System.out.println("********************byte数据**************************");
			System.out.println("加密前数据..............");
			for (byte b : string.getBytes()) {
				System.out.print(b);
			}
			System.out.println();
			byte[] e = rsa.encypt(publicKey, string.getBytes());
			System.out.println("加密后数据..............");
			for (byte b : e) {
				System.out.print(b);
			}
			System.out.println();
			byte[] de = rsa.decrypt(privateKey, e);
			System.out.println("解密后数据..............");
			for (byte b : de) {
				System.out.print(b);
			}
			
			System.out.println();
			System.out.println("********************String数据**************************");
			System.out.println("加密前.....................");
			System.out.println(string);
			System.out.println("加密后.....................");
			String eString = new String(e);
			System.out.println(eString);
			System.out.println("解密后......................");
			String deString = new String(de);
			System.out.println(deString);
			
			System.out.println("HexString映射...............");
			System.out.println(RSATest.toHexString(string.getBytes()));
			System.out.println(RSATest.toHexString(e));   
            System.out.println(RSATest.toHexString(de));
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
}
