package my.encrypt.format_preserving;

import java.math.BigInteger;
import java.util.Arrays;

import my.encrypt.aes.Aes;

public final class Feistel {
	private static BigInteger num(int[] X, int radix) {
		BigInteger x = BigInteger.ZERO;
		BigInteger rdx = BigInteger.valueOf(radix);
		for (int i = 0; i < X.length; i++) {
			if (X[i] < 0 || X[i] >= radix)
				throw new IllegalArgumentException(
						String.format("Value index %s misses in the interval", i));
			x = x.multiply(rdx).add(BigInteger.valueOf(X[i]));
		}
		return x;
	}
	
	
	public static void encode(int[] msg, int radix)
	{
		Aes enc = new Aes();
		// Asunder input array
		int [] A = Arrays.copyOfRange(msg, 0, msg.length/2);
		int [] B = Arrays.copyOfRange(msg, msg.length/2, msg.length);		
		// 8 rounds
		for (int i = 0; i < 8; i ++)
		{
			byte [] input ={0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,num(B,radix).toByteArray()[0]};
			byte [] value = enc.encryptBlockWithoutKey(input);
			BigInteger tmp = new BigInteger(value).add(num(A,radix));
			
			BigInteger pow = BigInteger.valueOf((long) Math.pow(radix, A.length));
			@SuppressWarnings("unused")
			BigInteger rest  = tmp.mod(pow);
			
		}
	}
	
	
}
