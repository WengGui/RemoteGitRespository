package JavaRSA;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class MyRSA {

	private final static String ALGORITHOM = "RSA";
	/** 保存生成的密钥对的文件名称。 */
	// private static final String RSA_PAIR_FILENAME = "/__RSA_PAIR.txt";
	/** 密钥大小 */
	private final int KEY_SIZE = 1024;

	/**
	 * RSA的公钥和私钥是由KeyPairGenerator生成的， 获取KeyPairGenerator的实例后还需要设置其密钥位数。
	 * 设置密钥位数越高，加密过程越安全，一般使用1024位。如下代码：
	 * 
	 * @return 返回密钥对
	 */
	public KeyPair generateKeyPair() {
		try {
			KeyPairGenerator generator = KeyPairGenerator.getInstance(ALGORITHOM);
			generator.initialize(KEY_SIZE);
			KeyPair pair = generator.generateKeyPair();
			return pair;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 公钥和私钥可以通过KeyPairGenerator执行generateKeyPair()后生成密钥对KeyPair，
	 * 通过KeyPair.getPublic()和KeyPair.getPrivate()来获取。如下代码：
	 */
	public PublicKey getPublicKey() {
		// 获取公钥
		KeyPair pair = generateKeyPair();
		PublicKey key = (RSAPublicKey) pair.getPublic();
		return key;
	}

	public byte[] getPublicKeyBytes(PublicKey key) {
		// 获得公钥的独特的比特编码
		return key.getEncoded();
	}

	public PrivateKey getPrivateKey() {
		// 获取密钥
		KeyPair pair = generateKeyPair();
		PrivateKey key = (RSAPrivateKey) pair.getPrivate();
		return key;
	}

	public byte[] getPrivateKeyBytes(PrivateKey key) {
		// 获得密钥的独特的比特编码
		return key.getEncoded();
	}

	// 通过公钥byte[]将公钥还原，适用于RSA算法
	public PublicKey getPublicKey(byte[] keyBytes) throws NoSuchAlgorithmException, InvalidKeySpecException {
		X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
		KeyFactory factory = KeyFactory.getInstance(ALGORITHOM);
		PublicKey publicKey = factory.generatePublic(spec);
		return publicKey;
	}

	// 通过私钥byte[]将密钥还原，适用于RSA算法
	public static PrivateKey getPrivateKey(byte[] keyBytes) throws NoSuchAlgorithmException, InvalidKeySpecException {
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		return privateKey;
	}

	/**
	 * 在上文讲到的RSA算法实现过程中提到(N,e)是公钥，(N,d)是私钥。既然已经获取到了PublicKey和PrivateKey了，
	 * 那如何取到N、e、d这三个值呢。要取到这三个值，
	 * 首先要将PublicKey和PrivateKey强制转换成RSAPublicKey和RSAPrivateKey。
	 * 共同的N值可以通过getModulus()获取。 执行RSAPublicKey.getPublicExponent()可以获取到公钥中的e值，
	 * 执行RSAPrivateKey.getPrivateExponent()可以获取私钥中的d值。 这三者返回类型都是BigInteger。代码如下：
	 */
	public PublicKeyMap printPublicKeyInfo(PublicKey key) {
		RSAPublicKey rsaPublicKey = (RSAPublicKey) key;
		PublicKeyMap keyMap = new PublicKeyMap();
		keyMap.setModulus(rsaPublicKey.getModulus());
		return keyMap;
	}

	public PrivateKeyMap printPrivateKeyInfo(PrivateKey key) {
		RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) key;
		PrivateKeyMap keyMap = new PrivateKeyMap();
		keyMap.setModulus(rsaPrivateKey.getModulus());
		return keyMap;
	}

	/**
	 * 由于程序中动态生成KeyPair对明文加密后生成的密文是不可测的，所以在实际开发中通常在生成一个KeyPair后,
	 * 将公钥和私钥的N、e、d这三个特征值记录下来，在真实的开发中使用这三个特征值再去将PublicKey和PrivateKey还原出来。
	 */
	public static PublicKey getPublicKey(String modulus, String pulblicExponent)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		BigInteger bigModulus = new BigInteger(modulus);
		BigInteger bigPublicExponent = new BigInteger(pulblicExponent);
		RSAPublicKeySpec keySpec = new RSAPublicKeySpec(bigModulus, bigPublicExponent);
		KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHOM);
		PublicKey publicKey = keyFactory.generatePublic(keySpec);
		return publicKey;
	}

	public static PrivateKey getPrivateKey(String modulus, String privateExponent)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		BigInteger bigIntModulus = new BigInteger(modulus);
		BigInteger bigIntPrivateExponent = new BigInteger(privateExponent);
		RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(bigIntModulus, bigIntPrivateExponent);
		KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHOM);
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		return privateKey;
	}

	// 打印公钥信息
	public static void printPublicKeyInfo2(PublicKey key) {
		RSAPublicKey rsaPublicKey = (RSAPublicKey) key;
		System.out.println("RSAPublicKey:");
		System.out.println("Modulus.length=" + rsaPublicKey.getModulus().bitLength());
		System.out.println("Modulus=" + rsaPublicKey.getModulus().toString());
		System.out.println("PublicExponent.length=" + rsaPublicKey.getPublicExponent().bitLength());
		System.out.println("PublicExponent=" + rsaPublicKey.getPublicExponent().toString());
	}

	// 打印私钥信息
	public static void printPrivateKeyInfo2(PrivateKey key) {
		RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) key;
		System.out.println("RSAPrivateKey:");
		System.out.println( "Modulus.length=" + rsaPrivateKey.getModulus().bitLength());
		System.out.println("Modulus=" + rsaPrivateKey.getModulus().toString());
		System.out.println("PublicExponent.length=" + rsaPrivateKey.getPrivateExponent().bitLength());
		System.out.println( "PublicExponent=" + rsaPrivateKey.getPrivateExponent().toString());

	}

}
