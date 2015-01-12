package com.epikur.nettyserver.crypting;

public abstract class Crypter {
	public enum CryptingMethod {
		DEFAULT
		, UNKNOWN;
		
		public static CryptingMethod fromByte(byte bMethod) {
			switch (bMethod) {
			case 010:
				return DEFAULT;
			default:
				return UNKNOWN;
			}
		}
		
		public static byte toByte(CryptingMethod cryptMethod) {
			switch (cryptMethod) {
			case DEFAULT:
				return 010;
			case UNKNOWN:
			default:
				return -1;
			}
		}
	}
	
	private byte [] key;
	
	public Crypter() {};
	
	public Crypter(String sKey) {
		this.setKey(sKey);
	}
	
	public Crypter(byte [] bKey) {
		this.setKey(bKey);
	}
	
	public void setKey(String sKey) {
		key = sKey.getBytes();
	}
	
	public void setKey(byte [] bKey) {
		key = bKey;
	}
	
	public String getKeyString() {
		return key.toString();
	}
	
	public byte [] getKeyBytes() {
		return key;
	}
	
	public byte[] crypt(String s) {
		return s.getBytes();
	}
	
	public byte[] crypt(byte [] b) {
		return b;
	}
	
	public String decrypt(String s) {
		return s;
	}
	
	public String decrypt(byte [] b) {
		return b.toString();
	}
	
	public static Crypter getCrypter(CryptingMethod cryptMethod) {
		switch (cryptMethod) {
		case DEFAULT:
			return new DefaultCrypter();
		case UNKNOWN:
		default:
			return null;
		}
	}
	
	public static Crypter getCrypter(byte bMethod) {
		return getCrypter(CryptingMethod.fromByte(bMethod));
	}
}
