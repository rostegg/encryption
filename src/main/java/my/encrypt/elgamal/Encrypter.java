package my.encrypt.elgamal;

import java.math.BigInteger;
import java.security.SecureRandom;
public 
class Encrypter  {
  private BigInteger p, g, r, pMinus2;
  private SecureRandom srng;
  private static final BigInteger ONE = BigInteger.ONE, TWO = ONE.add(ONE);
  public Encrypter(BigInteger p, BigInteger g, BigInteger r) {
    srng = new SecureRandom();
    this.p = p; this.g = g;  this.r = r;
    pMinus2 = p.subtract(TWO);
    System.out.println("Encryption key:");
    System.out.println("p = " + p.toString(16));
    System.out.println("g = " + g.toString(16));
    System.out.println("r = " + r.toString(16));
  }
  public byte[] encrypt(byte[] msg) {
    long startTm = System.currentTimeMillis();
    int blkSz = (p.bitLength() - 1)/8;
    byte[][] ba = Transform.block(Transform.pad(msg, blkSz), blkSz);
    byte[][] ba2 = new byte[2*ba.length][];
    BigInteger m, c0, c1, k;
    System.err.println("" + ba.length + " blocks");
    for (int i=0; i<ba.length; i++) {
      m = new BigInteger(1, ba[i]);     
      k = new BigInteger(p.bitLength(), srng);
      k = k.mod(pMinus2).add(ONE);      
      c0 = g.modPow(k, p);              
      c1 = r.modPow(k, p).multiply(m).mod(p);
      ba2[2*i]   = Transform.getBytes(c0);  
      ba2[2*i+1] = Transform.getBytes(c1);
      //if (i%10 == 0) System.err.print("\rBlock " + i);
    }
    //System.err.println("\rEncryption took " + 
    //  (System.currentTimeMillis()-startTm) + " ms");
    return Transform.unblock(ba2, blkSz+1);
  }
} 
