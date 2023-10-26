package edu.jnu.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.SecureRandom;

public class Tools {
    public static BigInteger getRandomBigInteger(BigInteger nSquare) {
        SecureRandom random = new SecureRandom();

        byte[] rbyte = new byte[nSquare.bitLength()];
        random.nextBytes(rbyte);

        return new BigInteger(rbyte).mod(nSquare);
    }

	public static boolean isNumeric(String str) {
		try {
			new BigDecimal(str);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
