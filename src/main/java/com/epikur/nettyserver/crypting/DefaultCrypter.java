package com.epikur.nettyserver.crypting;

public class DefaultCrypter extends Crypter {

	public DefaultCrypter() {
		super();
	}
	
	public DefaultCrypter(String sKey) {
		super(sKey);
	}
	
	public DefaultCrypter(byte [] bKey) {
		super(bKey);
	}
	
	@Override
	public void setKey(String sKey) {
		// TODO Auto-generated method stub
		super.setKey(sKey);
	}

	@Override
	public void setKey(byte[] bKey) {
		// TODO Auto-generated method stub
		super.setKey(bKey);
	}

	@Override
	public String getKeyString() {
		// TODO Auto-generated method stub
		return super.getKeyString();
	}

	@Override
	public byte[] getKeyBytes() {
		// TODO Auto-generated method stub
		return super.getKeyBytes();
	}

	@Override
	public byte[] crypt(String s) {
		// TODO Auto-generated method stub
		return super.crypt(s);
	}

	@Override
	public byte[] crypt(byte[] b) {
		// TODO Auto-generated method stub
		return super.crypt(b);
	}

	@Override
	public String decrypt(String s) {
		// TODO Auto-generated method stub
		return super.decrypt(s);
	}

	@Override
	public String decrypt(byte[] b) {
		// TODO Auto-generated method stub
		return super.decrypt(b);
	}

}
