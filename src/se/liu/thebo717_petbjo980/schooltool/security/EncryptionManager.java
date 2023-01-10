package se.liu.thebo717_petbjo980.schooltool.security;

/**
 * Super simple string encryption.
 * Can encrypt and decrypt.
 */
public class EncryptionManager
{
    private final static int KEY = 37;

    public String encryptString(String s){
	StringBuilder builder = new StringBuilder();
	for(char c : s.toCharArray()){
	    builder.append(encryptChar(c));
	}
	return builder.toString();
    }

    public String decryptString(String s){
	StringBuilder builder = new StringBuilder();
	for(char c : s.toCharArray()){
	    builder.append(decryptChar(c));
	}
	return builder.toString();
    }

    public char encryptChar(char c){
	int c2 = c;
	return (char)(c2 * KEY);
    }

    public char decryptChar(char c){
	int c2 = c;
	return (char)(c2 / KEY);
    }
}
