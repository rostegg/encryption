package my.encrypt.elgamal;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.*;

public class GeneratorFactory {
	private int minBits, crtty;
	private SecureRandom srng;
	private BigInteger p, g;
	@SuppressWarnings("unused")
	private static final BigInteger 
		ZERO = BigInteger.ZERO,   ONE = BigInteger.ONE,
	    TWO  = ONE.add(ONE),    THREE = TWO.add(ONE);
	
	public GeneratorFactory (int bits) { this(bits, 300); } 
	public GeneratorFactory (int bits, int crtty) {
		this(bits, crtty, new SecureRandom()); 
	}
	
	public GeneratorFactory (int bits, int crtty, SecureRandom sr) {
		if (bits < 512)
			System.err.println("Safe primes should be >= 512 bits long");
		this.minBits = bits;
		this.crtty   = crtty;
		this.srng    = sr;
		System.out.printf("Making a safe prime of at least %d bits...\n", bits); 
		long startTm = System.currentTimeMillis(), endTm;
		makeSafePrimeAndGenerator();
		endTm = System.currentTimeMillis();
		//System.err.printf("Generating p, g took %d ms\n", endTm - startTm);
		System.out.printf("p = %x (%d bits)\n", p, p.bitLength());
		System.out.printf("g = %x (%d bits)\n", g, g.bitLength());
	}
	
	public void makeSafePrimeAndGenerator() {
		BigInteger r = BigInteger.valueOf(0x7fffffff),
		t = new BigInteger(minBits, crtty, srng);
	    do {
	      r = r.add(ONE);
	      p = TWO.multiply(r).multiply(t).add(ONE);
	    } while (!p.isProbablePrime(crtty));
	    HashSet<BigInteger> factors = new HashSet<BigInteger>();
	    factors.add(t); 
	    factors.add(TWO);
	    if (r.isProbablePrime(crtty))
	      factors.add(r);
	    else 
	      factors.addAll(primeFact(r));
	    BigInteger pMinusOne = p.subtract(ONE), z, lnr;
	    boolean isGen;
	    do {
	      isGen = true;
	      g = new BigInteger(p.bitLength()-1, srng); 
	      for (BigInteger f: factors) { 
	        z = pMinusOne.divide(f);
	        lnr = g.modPow(z, p);
	        if (lnr.equals(ONE)) {
	          isGen = false;
	          break;
	        }
	      }
	    } while (!isGen);
	  
	} 
	public static HashSet<BigInteger> primeFact(BigInteger n) {
		BigInteger nn = new BigInteger(n.toByteArray()); //clone n
		HashSet<BigInteger> factors = new HashSet<BigInteger>();
		BigInteger dvsr = TWO,
			dvsrSq = dvsr.multiply(dvsr);
		while (dvsrSq.compareTo(nn) <= 0) {   
			if (nn.mod(dvsr).equals(ZERO)) {  
				factors.add(dvsr);              
		        while (nn.mod(dvsr).equals(ZERO))
		          nn = nn.divide(dvsr);       
		      }
		      dvsr = dvsr.add(ONE);           
		      dvsrSq = dvsr.multiply(dvsr);
		    }
		if (nn.compareTo(ONE) > 0)
			factors.add(nn);
		return factors; 
	}
	public BigInteger getP() { return p; }
	public BigInteger getG() { return g; }
	
	public static boolean isGenerator(BigInteger p, BigInteger g, int crtty) {
		System.err.printf("Testing p = %s,\ng = %s\n",
                  p.toString(16), g.toString(16));
		if (!p.isProbablePrime(crtty)) {
			System.err.println("p is not prime.");
		return false;
		}
		if (g.mod(p).equals(ZERO)) {
			System.err.println("p divides g.");
			return false;
		}
		
		BigInteger pMinusOne = p.subtract(ONE), z;
		System.err.println("Finding prime factors of p-1 ...");     
		HashSet<BigInteger> factors = primeFact(pMinusOne);
		boolean isGen = true;
		for (BigInteger f: factors) { 
			z = pMinusOne.divide(f);
			if (g.modPow(z, p).equals(ONE)) {
				isGen = false;
				System.err.println("g is not a generator mod p.");
				break;
			}
		}
		return isGen;
	}
}
