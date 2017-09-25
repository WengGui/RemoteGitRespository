package JavaRSA;

import java.math.BigInteger;

public class PrivateKeyMap {
	private BigInteger modulus;
	private BigInteger exponent;
	private byte[] encoded;

	public BigInteger getModulus() {
		return modulus;
	}

	public void setModulus(BigInteger modulus) {
		this.modulus = modulus;
	}

	public BigInteger getExponent() {
		return exponent;
	}

	public void setExponent(BigInteger exponent) {
		this.exponent = exponent;
	}

	public byte[] getEncoded() {
		return encoded;
	}

	public void setEncoded(byte[] encoded) {
		this.encoded = encoded;
	}

	@Override
	public String toString() {
		return "PublicKeyMap [modulus=" + modulus + ", exponent=" + exponent + "]";
	}
}
