package my.encrypt.Encryption;

import java.math.BigInteger;
import java.util.Arrays;

import my.encrypt.aes.Aes;
import my.encrypt.des.Des;
import my.encrypt.des.DesImp;
import my.encrypt.diffehellman.Jack;
import my.encrypt.diffehellman.Kelly;
import my.encrypt.diffehellman.Spy;
import my.encrypt.elgamal.Decrypter;
import my.encrypt.elgamal.Elgamal;
import my.encrypt.elgamal.Encrypter;
import junit.framework.TestCase;

public class EncryptionTests extends TestCase {

	public void testDiffe()
    {
		   System.out.println("\n\nStart Diffe- Hellman algorithm.");
    	   Jack jack = new Jack();
           Kelly kelly = new Kelly();
           Spy spy = new Spy();
            
           jack.generateSecretKey(kelly, spy);
           
           System.out.println("Jack generates parameters a, p, g and A and sends them to kelly");
           System.out.println("Spy intercepts parameters a, p, g and A from jack and generates privat parameter c");
           System.out.println("Kelly recieves parameters a, p, g and A from jack and generates privat parameter b");
           
           System.out.println();
           
           System.out.println("Kelly generates key based on parameters p, g, b and A");
           
           System.out.println("Kelly send public key B to side A");
           System.out.println("Spy intercepts public key B from kelly which oriented for jack");
           
           
           System.out.print("jack:\t\t\t");
           jack.showHashSecretKey();
           System.out.print("kelly:\t\t\t");
           kelly.showHashSecretKey();
           System.out.print("spy as it exchanges kelly: ");
           spy.showHashSecretKey();
           
           assertTrue(Arrays.equals(jack.getSecretKey(),kelly.getSecretKey()));
           assertFalse(Arrays.equals(jack.getSecretKey(),spy.getSecretKey()));
           assertFalse(Arrays.equals(spy.getSecretKey(),kelly.getSecretKey()));
           System.out.println();
           
           String msg = "абракадабраеба";
           System.out.println("Jack enciphers message " + msg );
           
           String encipheredMessage = jack.encipherMessage(msg);
           System.out.println();
           System.out.println("Message was enchiphered and now looks like: " + encipheredMessage);
           System.out.println();
           String decipheredMessage = kelly.decipherMessage(encipheredMessage);
           System.out.println("kelly deciphers message: " + decipheredMessage);
           System.out.println();
           assertTrue(msg.equals(decipheredMessage));
           String decipheredMessageBySpy = spy.decipherMessage(encipheredMessage);
           System.out.println("Spy deciphers message as it exchanges kelly: " + decipheredMessageBySpy);
    }

	public void testDes()
	{
		Des des = new Des();
		des.test(
                des.parseBytes("a4b2 c9ef 0876 c1ce 438d e282 3820 dbde"),
                des.parseBytes("fa60 69b9 85fa 1cf7 0bea a041 9137 a6d3"),
                "mypass"
            );
		
		des.test(
				des.parseBytes("f3ed a6dc f8b7 9dd6 5be0 db8b 1e7b a551"),
				des.parseBytes("b669 d033 6c3f 42b7 68e8 e937 b4a5 7546"),
		            "mypass"
		        );
		
		String in = "helloworldman";
        String key = "thismykey";
        byte [] out = DesImp.encrypt(in.getBytes(), key.getBytes());
        assertTrue(new String(DesImp.decrypt(out, key.getBytes())).equals(in));
        
	}
	public void testAes()
	{
		String key = "thisismykey";
		String msg = "приветик, а вот и я";
		System.out.println("\n\nStart Aes algorithm.\nStarting translate : " + msg + "\n\t Common symmetrically key : " + key);
		Aes obj = new Aes();
		byte [] out = obj.encrypt(msg.getBytes(), key.getBytes());
		//System.out.println(new String(out));
		assertTrue(out!=null);
		byte [] back = obj.decrypt(out, key.getBytes());
		String bck = new String(back);
		assertTrue(bck.equals(msg));
	}
	
	
	public void testElgamal()
    {
    	String msg= "helloworldbiach";
		System.out.println("\n\nStart Elgamal algorithm.\nStarting translate : " + msg);
    	byte[] buff = msg.getBytes();
    	Elgamal elgamal = new Elgamal(1024);
    	Encrypter enc = elgamal.getEncrypter();
        byte[] tmsg = enc.encrypt(buff);
        Decrypter dec = elgamal.getDecrypter();
        byte[] out = dec.decrypt(tmsg);
        String bck = new String(out);
        assertTrue(bck.equals(msg));
    }
	
	
	
	@SuppressWarnings("unused")
	private String toHex(String arg) {
        return String.format("%040x", new BigInteger(1, arg.getBytes())).toUpperCase();
    }
}
