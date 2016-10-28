package my.encrypt.main;

import java.util.ArrayList;
import java.util.List;

public final class SimpleAlgorithms {

	static List<Character> alphabet = new ArrayList<Character>();
	static
	{
		// RUS
		int cnt = 0;
		for (int i = 1072;i < 1104;i++)
		{
			if (cnt == 6)
				alphabet.add('ё');
			alphabet.add((char)i);
			cnt += 1;
		}
		// ENG
		/*for (int i = 65; i<91;i++)
		{
			alphabet.add((char)i);
		}*/
		//SPACE
		alphabet.add(' ');
	}
	public static void printAlphabet()
	{
		for (char symbol : alphabet)
		{
			System.out.print(symbol);
		}
		System.out.print("\n");
	}
	
	private static int getFunction(int x)
	{
		return (3*x)+2;
	}
	
	public static String trithemiusEncrypt(String input)
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
			result.append(alphabet.get((t+point)%alphabet.size()));
		}
		return result.toString();
	}
	
	public static String trithemiusDecrypt(String input)
	{
		StringBuilder result = new StringBuilder();
		List<Character> local = new ArrayList<Character>();
		for (char c : alphabet) {
			 local.add(c);
	    }
		int counter = 0;
		for(char symbol : input.toCharArray())
		{
			int k = getFunction(counter);
			int point =  local.indexOf(symbol);
			int m = (point - k)%alphabet.size();
			//System.out.print(alphabet.get(m));
			if (m >= 0)
				result.append(alphabet.get(m));
			else
				result.append(alphabet.get(alphabet.size() + m));
			counter+=1;
		}
		return result.toString();
	}
	
	public static String caesarEncrypt(String input,int shift)
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
	
	public static String caesarDecrypt(String input,int shift)
	{

		List<Character> local = new ArrayList<Character>();
		for (char c : alphabet) {
			 local .add(c);
	    }
		StringBuilder result = new StringBuilder();
		for (char symbol : input.toCharArray())
		{
			int index = local.indexOf(symbol) - shift;
			result.append(index >= 0 ? alphabet.get(index) : alphabet.get(alphabet.size() + index));
		}
		return result.toString();
	}
	
	public static byte[] xorEncrypt(String msg, int key)
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
	
	public static String xorDecrypt(byte[] msg, int key)
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
