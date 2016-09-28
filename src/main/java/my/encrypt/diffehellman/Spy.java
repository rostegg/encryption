package my.encrypt.diffehellman;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.KeyAgreement;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;

public class Spy {
	private byte[] hashSecretKey = null;
    
    public void showHashSecretKey() {
    	for (int i = 0; i < hashSecretKey.length; i++) {
    		System.out.print(hashSecretKey[i] + " ");
    	}
    	System.out.println();
    }
    
    public byte[] getSecretKey()
    {
    	return hashSecretKey;
    }
    
    public String generateSecretKey(String pValueAsHexBase64, String gValueAsHexBase64, String publicKeyAsAsHexStringBase64) {
    	BigInteger p = CipherMessage.convertBase64HexStringToBigInteger(pValueAsHexBase64);
        BigInteger g = CipherMessage.convertBase64HexStringToBigInteger(gValueAsHexBase64);
        BigInteger y = CipherMessage.convertBase64HexStringToBigInteger(publicKeyAsAsHexStringBase64);
        PublicKey A = TransportPublicKey.getKey(null, y, p, g, 0);
        PublicKey key = null;
        try {
            DHParameterSpec dhParams = new DHParameterSpec(p, g);
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DH");
             
            keyGen.initialize(dhParams, new SecureRandom());
             
            KeyAgreement keyAgree = KeyAgreement.getInstance("DH");
            KeyPair pair = keyGen.generateKeyPair();
             
            keyAgree.init(pair.getPrivate());
            key = pair.getPublic();
             
            keyAgree.doPhase(A, true);
             
            byte[] secretKey = keyAgree.generateSecret();
             
            MessageDigest hash = MessageDigest.getInstance("SHA-256");
            hashSecretKey = hash.digest(secretKey);
            hashSecretKey = Arrays.copyOf(hashSecretKey, 16);          
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CipherMessage.convertBigIntegerToBase64HexString(((DHPublicKey) key).getY());
    }
    
    public String encipherMessage(String message) {
        return Base64.encode( CipherMessage.encodeMessage(hashSecretKey, message));
    }
    
    
     
    public String decipherMessage(String encipheredMessage) {
    	String decryptedMessage = null;
    	try {
    		decryptedMessage = CipherMessage.decodeMessage(hashSecretKey, Base64.decode(encipheredMessage));
    	} catch (javax.crypto.BadPaddingException e) {
    		decryptedMessage = "Message can not be deciphered";
    	}
        return decryptedMessage;
    }
}