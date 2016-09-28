package my.encrypt.elgamal;

import java.math.*;
import java.security.SecureRandom;
import java.io.*;

public class Elgamal {
	  private BigInteger p, g, a, r, pMinus2;
	  private SecureRandom srng;
	  private static final int CRTTY = 300; 
	  private static final String configPath = "config.txt";
	  @SuppressWarnings("unused")
	  private static final BigInteger 
	     ZERO = BigInteger.ZERO,   ONE = BigInteger.ONE,
	     TWO  = ONE.add(ONE),    THREE = TWO.add(ONE);
	  
	  public Elgamal(int kSz) {
	    srng = new SecureRandom();
	    GeneratorFactory fact = new GeneratorFactory(kSz, CRTTY, srng);
	    p = fact.getP(); 
	    pMinus2 = p.subtract(TWO);
	    g = fact.getG();
	    BigInteger pmt = p.subtract(THREE);
	    a = (new BigInteger(p.bitLength(), srng)).mod(pmt).add(TWO);
	    r = g.modPow(a, p);
	    saveConfig();
	  }
	 
	  public void saveConfig() {
	    try {
	      PrintWriter out = new PrintWriter(new FileWriter(configPath));
	      out.println(p.toString(16));      out.println(g.toString(16));
	      out.println(a.toString(16));
	      out.close();
	    } catch (IOException ex) {
	      System.err.println("Could not save the config");
	    }
	  }
	  public BigInteger getP() { return p; }
	  public BigInteger getG() { return g; }
	  public BigInteger getR() { return r; }
	  
	  public BigInteger[] encrypt(BigInteger m) {
	    BigInteger k = new BigInteger(p.bitLength(), srng);
	    k = k.mod(pMinus2).add(ONE);
	    BigInteger[] cipher = new BigInteger[2];
	    cipher[0] = g.modPow(k, p);
	    cipher[1] = r.modPow(k, p).multiply(m).mod(p);
	    return cipher;
	  }
	  public BigInteger decrypt(BigInteger c0, BigInteger c1) {
	    BigInteger c = c0.modPow(a, p).modInverse(p); //c0^-a mod p
	    return c.multiply(c1).mod(p);
	  }
	  public Encrypter getEncrypter() {
		  return new Encrypter(p, g, r);
	  }
	  
	  public Decrypter getDecrypter() {
		  return new Decrypter(p, a);
	  }
}
