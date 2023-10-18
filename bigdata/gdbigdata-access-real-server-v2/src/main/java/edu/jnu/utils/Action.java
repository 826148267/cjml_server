package edu.jnu.utils;


import edu.jnu.entity.CipherText;
import edu.jnu.entity.PublicKey;

import java.math.BigInteger;
import java.util.ArrayList;

public class Action {
	
	static public BigInteger[] N_ = new BigInteger[10]; // N={n^0, n^1,……, n^9}

	public static CipherText Enc(PublicKey pk, BigInteger m, int l, int s) {
		for (int i = 0; i < N_.length; i++) {
			N_[i] = pk.getN().pow(i);
		}
		BigInteger n_s = N_[s]; // n_s = n^s
		BigInteger n_sp = N_[s + 1]; // n_sp = n^(s+1)
		BigInteger r = Tools.getRandomBigInteger(n_sp); // （用安全随机数类随机生产）r∈Z[n^(s+1)]^*
		BigInteger g = pk.getG(); // g=1+n
		
		ArrayList<Integer> sList = new ArrayList<>();
		
		sList.add(s);
		
		CipherText cText = new CipherText();
		cText.setCp(g.modPow(m, n_sp).multiply(r.modPow(n_s, n_sp)).mod(n_sp)); // 密文 = [(1+n)^m mod n^(s+1)]·[r^(n^s) mod n^(s+1)] mod n^(s+1) = {[(1+n)^m][r^(n^s)]} mod n^(s+1)
		cText.setL(l); // 这里的l是用于标识当前密文的层数，方便日后做同态加
		cText.setS(sList); // 这里s指要把明文加密到第s层
		return cText;
	}
	
	public static CipherText Enc(PublicKey pk, CipherText ct, int s) throws Exception {
		ArrayList<Integer> sList = ct.getS();
		if (sList.isEmpty() || s <= sList.get(sList.size() - 1)) { // 如果不是密文||妄想从高层加密到低层，报错
			throw new Exception("Can't re-encrypt the ciphertext!!");
		}
		CipherText cText = Action.Enc(pk, ct.getCp(), ct.getL(), s); // 归到明文加密时的操作
		
		sList.add(s);
		cText.setS(sList);
		return cText;
	}

	private static BigInteger find_i(BigInteger c, int s, BigInteger n) {

		BigInteger t, t1, t2, nj, f, i = BigInteger.ZERO;

		//System.out.println("inside find_i");

		for (int j = 1; j <= s; j++) {

			//System.out.println("j: " + j);

			t = c.mod(N_[j + 1]);
			//System.out.println("t: " + t + "\n");

			t1 = t.subtract(BigInteger.ONE).divide(n);
			//System.out.println("t1: " + t1 + "\n");

			t2 = i;
			//System.out.println("t2: " + t2 + "\n");

			nj = N_[j];
			//System.out.println("nj: " + nj + "\n");

			for (int k = 2; k <= j; k++) {

				//System.out.println("k: " + k);

				i = i.subtract(BigInteger.ONE);
				//System.out.println("i: " + i + "\n");

				t2 = t2.multiply(i).mod(nj);
				//System.out.println("t2: " + t2 + "\n");

				f = factorial(k);
				f = f.modInverse(nj);
				//System.out.println("f inverse: " + f + "\n");

				t1 = t1.subtract(n.pow(k - 1).multiply(f).multiply(t2)).mod(nj);
				//System.out.println("t1: " + t1 + "\n");
			}
			i = t1;
		}

		return i;
	}
	
	private static BigInteger factorial(int k) {

		BigInteger res = BigInteger.valueOf(k);

		k--;

		while (k > 1) {
			res = res.multiply(BigInteger.valueOf(k));
			k--;
		}

		return res;
	}

	public static CipherText HomoAdd(PublicKey pk, CipherText ct1, CipherText ct2) {
		
		int s = ct1.getS().get(ct1.getS().size() - 1);
		
		BigInteger temp = ct1.getCp().multiply(ct2.getCp()).mod(N_[s + 1]);
		
		return new CipherText(temp, ct1.getL(), ct1.getS());
	}

	//ct1^ct2
	public static CipherText powCip(PublicKey pk, CipherText ct1, CipherText ct2) {
		
		BigInteger cp = ct1.getCp().modPow(ct2.getCp(), N_[ct1.getL()+1]);
		
		ArrayList<Integer> sList = ct2.getS();
		sList.add(ct1.getL());
		
		return new CipherText(cp, ct2.getL(), sList);
	}

}
