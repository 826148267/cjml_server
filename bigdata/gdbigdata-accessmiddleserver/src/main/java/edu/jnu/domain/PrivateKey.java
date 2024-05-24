package edu.jnu.domain;

import java.math.BigInteger;

public class PrivateKey {
    private BigInteger d;
    private BigInteger n;
    private BigInteger g;
	public BigInteger getD() {
		return d;
	}
	public void setD(BigInteger d) {
		this.d = d;
	}
	public BigInteger getN() {
		return n;
	}
	public void setN(BigInteger n) {
		this.n = n;
	}
	public BigInteger getG() {
		return g;
	}
	public void setG(BigInteger g) {
		this.g = g;
	}
	public PrivateKey(BigInteger d, BigInteger n, BigInteger g) {
		super();
		this.d = d;
		this.n = n;
		this.g = g;
	}
}
