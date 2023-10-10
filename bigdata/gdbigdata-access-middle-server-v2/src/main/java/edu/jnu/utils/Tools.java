package edu.jnu.utils;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.*;

public class Tools {

    public static BigInteger getRandomBigInteger(BigInteger nSquare) {
        SecureRandom random = new SecureRandom();

        byte[] rbyte = new byte[nSquare.bitLength()];
        random.nextBytes(rbyte);

        return new BigInteger(rbyte).mod(nSquare);
    }

	public static String encode(String originStr) {
		originStr = Base64.getEncoder().encodeToString(originStr.getBytes());
		String[] strArr = originStr.split("");
		StringBuffer tmp = new StringBuffer("");
		for (int i = 0; i < strArr.length; i++) {
			if (strArr[i].charAt(0) - 'd' < 0) {
				strArr[i] = String.valueOf(200 + (int) strArr[i].charAt(0));
				tmp.append(strArr[i]);
			} else {
				tmp.append(String.valueOf((int) strArr[i].charAt(0)));
			}
		}
		return tmp.toString();
	}

	public static int[] createRandomIndexSet(int randomSequenceSize, int max, int min) {
		TreeSet deduplicate = new TreeSet<>();
		int[] results = new int[randomSequenceSize];
		int i = 0;
		int size = 0;
		while (size < randomSequenceSize) {
			int tmp = Tools.getRandom(max, min);
			deduplicate.add(tmp);
			if (deduplicate.size() == (size + 1)) {
				results[i] = tmp;
				i++;
				size++;
			}
		}
		return results;
	}

	public static int getRandom(int max, int min){
		Random random = new Random();
		int s = random.nextInt(max)%(max-min) + min;
		return s;
	}

	public static String decode(String encodedStr) {
		ArrayList<String> list = Tools.splitStringAsList(encodedStr.toString(), 3);
		byte[] bytes = new byte[list.size()];
		for (int i = 0; i < list.size(); i++) {
			int bt = Integer.parseInt(list.get(i));
			if (bt > 199) {
				bytes[i] = (byte) (bt - 200);
			} else {
				bytes[i] = (byte) bt;
			}
		}
		return new String(Base64.getDecoder().decode(bytes));
	}

	public static ArrayList<String> splitStringAsList(String content, int len){
		int length = content.length();
		List<String> list = new ArrayList<String>();
		if(length%len != 0){
			for(int i=0;i<length/len;i++){
				String newContent = content.substring(i*len, (i+1)*len);

				list.add(newContent);
			}

			list.add(content.substring(length/len*len, length%len+length/len*len));
		}else{
			for(int j=0;j<length/len;j++){
				String newContent = content.substring(j*len, (j+1)*len);

				list.add(newContent);
			}
		}
		return (ArrayList<String>) list;
	}
}
