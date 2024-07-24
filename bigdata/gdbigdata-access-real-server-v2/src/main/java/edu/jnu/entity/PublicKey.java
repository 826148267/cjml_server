package edu.jnu.entity;

import java.math.BigInteger;

public class PublicKey {
    private BigInteger n;
    private BigInteger g;
	public BigInteger getN() {
		return n;
	}
	public void setN(BigInteger n) {
		this.n = n;
	}
	public BigInteger getG() {
		return g;
	}
	public PublicKey(BigInteger n, BigInteger g) {
		super();
		this.n = n;
		this.g = g;
	}
	public void setG(BigInteger g) {
		this.g = g;
	}
}
