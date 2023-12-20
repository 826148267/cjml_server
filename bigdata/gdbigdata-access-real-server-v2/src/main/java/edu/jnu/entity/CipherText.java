package edu.jnu.entity;

import java.math.BigInteger;
import java.util.ArrayList;

public class CipherText {

    private BigInteger cp;
    private int l;
    private ArrayList<Integer> s;

    public CipherText() {
		super();
		this.s = new ArrayList<Integer>();
	}

	public BigInteger getCp() {
		return cp;
	}
	public void setCp(BigInteger cp) {
		this.cp = cp;
		this.s = new ArrayList<Integer>();
	}
	public CipherText(BigInteger cp, int l, ArrayList<Integer> s) {
		super();
		this.cp = cp;
		this.l = l;
		this.s = s;
	}
	@Override
	public String toString() {
		return "CipherText [cp=" + cp + ", l=" + l + ", s=" + s + "]";
	}
	public int getL() {
		return l;
	}
	public void setL(int l) {
		this.l = l;
	}
	public ArrayList<Integer> getS() {
		return s;
	}
	public void setS(ArrayList<Integer> s) {
		this.s = s;
	}
	
}
