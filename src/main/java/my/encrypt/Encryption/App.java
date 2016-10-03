package my.encrypt.Encryption;

import my.encrypt.aes.Aes;
import my.encrypt.blowfish.Blowfish;
import my.encrypt.des.Des;
import my.encrypt.des.DesImp;
import my.encrypt.diffehellman.*;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import my.encrypt.elgamal.*;
import my.encrypt.format_preserving.Feistel;

public class App 
{
    public static void main( String[] args ) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, DecoderException
    {

    	Encryption en = new Encryption();
    	Feistel fstl = new Feistel();
    	fstl.encode(new int[] {0,1,2,3},4);
    	//en.printAlphabet();
    	
    }
    @SuppressWarnings("unused")
	private static String toHex(String arg) {
        return String.format("%040x", new BigInteger(1, arg.getBytes())).toUpperCase();
    }
    
    @SuppressWarnings("unused")
	private static String hex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<bytes.length; i++) {
            sb.append(String.format("%02X ",bytes[i]));
        }
        return sb.toString();
    }
    
    @SuppressWarnings("unused")
	private static void xor() throws UnsupportedEncodingException
    {
    	Encryption en = new Encryption();
    	String msg = "всего навсего простая строка";
    	byte [] bytes = en.encryptXor(msg, 134);
    	
    	System.out.printf("In : %s \nOut : %s", new String(bytes,"ASCII"),en.decryptXor(bytes, 134) );
    }
}
    
  
