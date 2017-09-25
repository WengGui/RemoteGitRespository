package JavaRSA;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import javax.crypto.Cipher;
import javax.naming.ConfigurationException;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class RSATest {
	
	private KeyPair keyPair;
	private PublicKey publicKey;
	private PrivateKey privateKey;
	private PrivateKeyMap privateKeyMap;
	private PublicKeyMap publicKeyMap;

	private static char[] HEXCHAR = { '0', '1', '2', '3', '4', '5', '6', '7',   
            '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' }; 
	
	/**
	 * 生成密钥对
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public KeyPair generateKeyPair() throws NoSuchAlgorithmException {
		if(this.keyPair==null){
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			keyPairGenerator.initialize(1024,new SecureRandom());
			this.keyPair = keyPairGenerator.generateKeyPair();
		}
		return this.keyPair;
	}
	
	public void saveKey(){
		this.publicKey = (RSAPublicKey)this.keyPair.getPublic();
		this.privateKey = (RSAPrivateKey)this.keyPair.getPrivate();
		//此处应将公钥和密钥的信息写入文件
	}
	
	//加密操作
	public byte[] encypt(RSAPublicKey publicKey,byte[] data){
		if (publicKey != null) { 
			try {
				Cipher cipher = Cipher.getInstance("RSA",new BouncyCastleProvider());
				cipher.init(Cipher.ENCRYPT_MODE, publicKey);
				return cipher.doFinal(data);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	//解密操作
	public  byte[] decrypt(RSAPrivateKey privateKey, byte[] data) {  
		if (privateKey != null) {   
			try {
				Cipher cipher = Cipher.getInstance("RSA",new BouncyCastleProvider());
				cipher.init(Cipher.DECRYPT_MODE, privateKey);
				return cipher.doFinal(data);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static String toHexString(byte[] b){
		StringBuilder sb = new StringBuilder(b.length*2);
		for(int i = 0; i<b.length;i++){
			sb.append(HEXCHAR[(b[i] & 0xf0) >>> 4]);
			sb.append(HEXCHAR[b[i] & 0x0f]);
		}
		return sb.toString();
	}
	
	public static final byte[] toBytes(String string){
		byte[] bytes;
		bytes = new byte[string.length() / 2];
		for(int i = 0 ; i < bytes.length /2 ; i++){
			bytes[i] = (byte) Integer.parseInt(string.substring(2*i,2*i+2),16);
		}
		return bytes;
	}

	
	
	
	
	
	
	public KeyPair getKeyPair() {
		return keyPair;
	}

	public void setKeyPair(KeyPair keyPair) {
		this.keyPair = keyPair;
	}

	public PublicKey getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(PublicKey publicKey) {
		this.publicKey = publicKey;
	}

	public PrivateKey getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(PrivateKey privateKey) {
		this.privateKey = privateKey;
	}

	public PrivateKeyMap getPrivateKeyMap() {
		return privateKeyMap;
	}

	public void setPrivateKeyMap(PrivateKeyMap privateKeyMap) {
		this.privateKeyMap = privateKeyMap;
	}

	public PublicKeyMap getPublicKeyMap() {
		return publicKeyMap;
	}

	public void setPublicKeyMap(PublicKeyMap publicKeyMap) {
		this.publicKeyMap = publicKeyMap;
	}
}
