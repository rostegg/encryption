package my.encrypt.Encryption;

import java.util.ArrayList;
import java.util.List;

public class Encryption {

	static List<Character> alphabet = new ArrayList<Character>();
	static
	{
		for (int i = 1072;i < 1104;i++)
		{
			alphabet.add((char)i);
		}
	}
	public void printAlphabet()
	{
		for (char symbol : alphabet)
		{
			System.out.print(symbol);
		}
		System.out.print("\n");
	}
	
	private int getFunction(int x)
	{
		return (3*x)+2;
	}
	
	public String trithemius(String input)
	{
		StringBuilder result = new StringBuilder();
		List<Character> local = new ArrayList<Character>();
		for (char c : alphabet) {
			 local.add(c);
	    }
		char [] symbols = input.toCharArray();
		for(int i =0; i < symbols.length;i++)
		{
			int t = getFunction(i);
			int point =  local.indexOf(symbols[i]);
			result.append(alphabet.get((t+point)%33));
		}
		return result.toString();
	}
	
	public String caesar(String input,int shift)
	{
		
		List<Character> local = new ArrayList<Character>();
		for (char c : alphabet) {
			 local .add(c);
	    }
		StringBuilder result = new StringBuilder();
		for (char symbol : input.toCharArray())
		{
			int point =  local.indexOf(symbol);
			result.append(point+shift > alphabet.size() ? 
					alphabet.get((point+shift)-alphabet.size())
					:alphabet.get(point+shift) );
		}
		return result.toString();
	}
	
	public byte[] encryptXor(String msg, int key)
	{
		int A = 3, C = 2,m = 101;
	    int actualKey = key;
        byte[] arr = msg.getBytes();
        byte[] result = new byte[arr.length];
        for(int i = 0; i< arr.length; i++)
        {
            result[i] = (byte) (arr[i] ^ actualKey);
            actualKey = (actualKey*A + C)%m;
        }
        return result;
	}
	
	public String decryptXor(byte[] msg, int key)
    {
		int A = 3, C = 2,m = 101;
        byte[] result  = new byte[msg.length];
        int actualKey = key;
        for(int i = 0; i < msg.length;i++)
        {
            result[i] = (byte) (msg[i] ^ actualKey);
            actualKey = (actualKey*A + C)%m;
        }
        return new String(result);
    }
	
	
}
