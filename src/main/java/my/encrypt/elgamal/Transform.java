package my.encrypt.elgamal;

import java.math.BigInteger;

public class Transform {
	public static byte[] pad(byte[] msg, int blkSz) {
		if (blkSz < 1 || blkSz > 255)
			throw new IllegalArgumentException("Block size out of range");
		int nToPd = blkSz - msg.length%blkSz;
		byte[] pdMsg = new byte[msg.length + nToPd];
		System.arraycopy(msg, 0, pdMsg, 0, msg.length);
		for (int i=msg.length; i< pdMsg.length; i++)
			pdMsg[i] = (byte)nToPd;
		return pdMsg;
	}

    protected static byte[] unpad(byte[] msg, int blkSz) {
    	int nPd = (msg[msg.length - 1] + 256) % 256; //unsigned val in last byte
	    byte[] rslt = new byte[msg.length - nPd];
	    System.arraycopy(msg, 0, rslt, 0, rslt.length);
	    return rslt; 
    }
    
	protected static byte[][] block(byte[] msg, int blkSz) {
		int nBlks = msg.length / blkSz;
		byte[][] ba = new byte[nBlks][blkSz];
		for (int i=0; i < nBlks; i++)
			for (int j=0; j < blkSz; j++)
		    	ba[i][j] = msg[i*blkSz + j];
		return ba;
    }
	
	protected static byte[] unblock(byte[][] ba, int blkSz) {
		byte[] ub = new byte [ba.length * blkSz];
		for (int i=0; i<ba.length; i++) {
			int j = blkSz-1, k = ba[i].length-1;
		    while (k >= 0) {
		    	ub[i*blkSz+j] = ba[i][k];
		        k--; j--;
		    }
		}
		return ub;
	}
	
	public static void display(byte[] b) {
		String digits = "0123456789abcdef";
		for (int i=0; i<b.length; i++) {
			System.out.print(digits.charAt((0xff&b[i]) >>> 4));
		    System.out.print(digits.charAt(b[i] & 0x0f));
		}
		System.out.println(" - "+ b.length + " bytes");
	}
	
	public static byte[] getBytes(BigInteger bg) {
		byte[] bts = bg.toByteArray();
		if (bg.bitLength()%8 != 0)
			return bts;
		else {
			byte[] sbts = new byte[bg.bitLength()/8];
		    System.arraycopy(bts, 1, sbts, 0, sbts.length);
		    return sbts;
		}
	}
	
}
