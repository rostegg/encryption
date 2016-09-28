package my.encrypt.elgamal;

import java.math.BigInteger;

public class Decrypter {
	private BigInteger p, a;
	
	public Decrypter(BigInteger p, BigInteger a) {
	    this.p = p; this.a = a;
	    System.out.println("Decryption key:");
	    System.out.println("p = " + p.toString(16));
	    System.out.println("a = " + a.toString(16));
	}
	  
	public byte[] decrypt(byte[] msg){ 
		long startTm = System.currentTimeMillis();
	    int blkSz = (p.bitLength()-1)/8 + 1;
	    byte[][] ba2 = Transform.block(msg, blkSz);
	    byte[][] ba  = new byte[ba2.length/2][];
	    BigInteger m, c0, c1, c;
	    System.err.println("" + ba.length + " blocks");
	    for (int i=0; i<ba.length; i++) {
	      c0 = new BigInteger(1, ba2[2*i]);  
	      c1 = new BigInteger(1, ba2[2*i+1]); 
	      c = c0.modPow(a, p).modInverse(p);  
	      m = c.multiply(c1).mod(p);          
	      ba[i] = Transform.getBytes(m);      
	      if (i%10 == 0) System.err.print("\rBlock " + i);
	    }
	    //System.err.println("\nDecryption took " + 
	    //  (System.currentTimeMillis()-startTm) + " ms");
	    return Transform.unpad(Transform.unblock(ba, blkSz-1), blkSz-1);
	  }
}
