package edu.jnu.utils;

import edu.jnu.domain.CipherText;
import edu.jnu.domain.KeyPair;
import edu.jnu.domain.PrivateKey;
import edu.jnu.domain.PublicKey;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Action {
	
	static public BigInteger[] N_ = new BigInteger[10]; // N={n^0, n^1,……, n^9}

	public static KeyPair Gen(int bits) {
		BigInteger p = BigInteger.valueOf(2).pow(bits / 2);
		p = p.nextProbablePrime(); // p是素数

		BigInteger q = p.nextProbablePrime();
		q = q.nextProbablePrime(); // q是p之后的第二个素数
		BigInteger n = p.multiply(q); // n=pq

		BigInteger g = n.add(BigInteger.ONE); // g=n+1=pq+1
		BigInteger d = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));  // 因为有定理ab=gcd·lcm，所以ab和lcm是倍数关系（即d=kλ）
		
		for (int i = 0; i < N_.length; i++) {
			N_[i] = n.pow(i);
		}
		
		return new KeyPair(new PublicKey(n, g), new PrivateKey(d, n, g)); // 私钥不一定要λ，它的因子也可以，不影响正确性
		
	}

	public static CipherText Enc(PublicKey pk, BigInteger m, int l, int s) {
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

	public static BigInteger find_i(BigInteger c, int s, BigInteger n) {

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
	
	public static BigInteger factorial(int k) {

		BigInteger res = BigInteger.valueOf(k);

		k--;

		while (k > 1) {
			res = res.multiply(BigInteger.valueOf(k));
			k--;
		}

		return res;
	}
	
	public static CipherText Dec(PrivateKey sk, CipherText cipherText) throws Exception{
		
		ArrayList<Integer> sList = cipherText.getS();
		
		if (sList.isEmpty() || cipherText.getL()>sList.get(sList.size()-1)) { // 如果不是密文||如果最里面那层的层数>当前密文层数，解密失败
			throw new Exception("Wrong ciphertext!");
		}
		
		int s = sList.get(sList.size() - 1); // s=当前层数
		
		BigInteger n_s = N_[s];
		BigInteger n_sp = N_[s + 1];
		BigInteger mu = sk.getD().modInverse(n_s); // 得到私钥d的模逆元（即求d=1 mod n^s的逆）
		BigInteger temp = cipherText.getCp().modPow(sk.getD(), n_sp); // temp = c^d mod n^(s+1)  （此处的d是私钥d，即kλ--满足d=0 mod λ）
		// 接下来利用Damg ?ard-Jurik的算法从temp中检索出m mod n^s
		temp = find_i(temp, s,sk.getN());
		ArrayList<Integer> sList2 = (ArrayList<Integer>) sList.clone();
		sList2.remove(sList2.size() - 1);
		return new CipherText(temp.multiply(mu).mod(n_s), cipherText.getL(), sList2);
	}

    public static CipherText Enc2SPlus2(PublicKey pk, int s, String asciiStr) throws Exception {
		BigInteger b = new BigInteger(asciiStr);
		return Enc2SPlus2(pk, s, b);
    }
	public static CipherText Enc2SPlus2(PublicKey pk, int s, BigInteger b) throws Exception {
		return Action.Enc(pk, Action.Enc(pk, b, s, s), s+1);
	}

    public static CipherText[] createTaoVector(PublicKey pk, int s, int randomSequenceSize, int i) {
		// 创建一个randomSequenceSize大小的CipherText数组
		CipherText[] taoVector = new CipherText[randomSequenceSize];
		// 初始化密文CipherText数组的密文全为0，除第i个为1
		// 加密数组中所有密文
		for (int j = 0; j < randomSequenceSize; j++) {
			if (i == j) {
				taoVector[j] = Action.Enc(pk, BigInteger.ONE, s+2, s+2);
			} else {
				taoVector[j] = Action.Enc(pk, BigInteger.ZERO, s+2, s+2);
			}
		}
		// 返回加密后的密文数组
		return taoVector;
    }

	public static String getPlainFromV(PrivateKey sk, CipherText v) throws Exception {
		return Tools.decode(Action.Dec(sk, v).getCp().toString());
	}

	public static CipherText getVFromR(PrivateKey sk, CipherText r) throws Exception {
		return Action.Dec(sk, Action.Dec(sk, r));
	}

	public static CipherText createDelta(PublicKey pk, int s, CipherText V, String freshValue) {
		BigInteger b_freshValue = new BigInteger(freshValue);
		CipherText v_freshValue = Action.Enc(pk, b_freshValue, s, s);
		CipherText delta = new CipherText();
		BigInteger tmp = v_freshValue.getCp().subtract(V.getCp());
		delta.setCp(tmp.mod(pk.getN().pow(s+1)));
		delta.setL(s);
		ArrayList<Integer> S = new ArrayList<>();
		S.add(s);
		delta.setS(S);
		return delta;
	}
}
