package cn.test;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.util.Arrays;
import java.util.Base64;

public class RC4 {
	private static final int SBOX_LENGTH = 256;
	private byte[] key = new byte[SBOX_LENGTH - 1];
    private int[] sbox = new int[SBOX_LENGTH];
    

    public RC4() {
        reset();
    }
    
    private void reset() {
        Arrays.fill(key, (byte) 0);
        Arrays.fill(sbox, 0);
    }

    
    
    public String encryptToBase64(String input,String key) throws InvalidKeyException, UnsupportedEncodingException {
	    reset();
        setKey(key);
        byte[] crypt = crypt(input.getBytes("UTF-8"));
        reset();
        return Base64.getEncoder().encodeToString(crypt);
    }
    
    public String decryptFromBase64(String input,String key) throws InvalidKeyException, UnsupportedEncodingException {
    	reset();
        setKey(key);
        byte[] msg = crypt(Base64.getDecoder().decode(input));
        reset();
        return new String(msg,"UTF-8");
    }
    
    
    public String encryptToHex(String input,String key) throws InvalidKeyException, UnsupportedEncodingException {
    	reset();
    	setKey(key);
    	byte[] crypt = crypt(input.getBytes("UTF-8"));
        reset();
        return bytesToHex(crypt);
    	
    }
    
    public String decryptFromHex(String input,String key) throws InvalidKeyException, UnsupportedEncodingException{
    	reset();
        setKey(key);
        byte[] msg = crypt(HextoBytes(input));
        reset();
        return new String(msg,"UTF-8");
    }
    
    
    public byte[] encrypt(byte[] input,String key) throws InvalidKeyException, UnsupportedEncodingException {
    	reset();
        setKey(key);
        byte[] msg = crypt(input);
        reset();
        return msg;
    	
    }
   

    private byte[] crypt(final byte[] msg) {
        sbox = initSBox(key);
        byte[] code = new byte[msg.length];
        int i = 0;
        int j = 0;
        for (int n = 0; n < msg.length; n++) {
            i = (i + 1) % SBOX_LENGTH;
            j = (j + sbox[i]) % SBOX_LENGTH;
            swap(i, j, sbox);
            int rand = sbox[(sbox[i] + sbox[j]) % SBOX_LENGTH];
            code[n] = (byte) (rand ^ msg[n]);
        }
        return code;
    }

    private int[] initSBox(byte[] key) {
        int[] sbox = new int[SBOX_LENGTH];
        int j = 0;

        for (int i = 0; i < SBOX_LENGTH; i++) {
            sbox[i] = i;
        }

        for (int i = 0; i < SBOX_LENGTH; i++) {
            j = (j + sbox[i] + (key[i % key.length]) & 0xFF) % SBOX_LENGTH;
      
            swap(i, j, sbox);
        }
        return sbox;
    }

    private void swap(int i, int j, int[] sbox) {
        int temp = sbox[i];
        sbox[i] = sbox[j];
        sbox[j] = temp;
    }

    private void setKey(String key) throws InvalidKeyException, UnsupportedEncodingException {
        this.key = key.getBytes("UTF-8");
    }
    
    
    private String bytesToHex(byte[] bytes) {
        char[] buf = new char[bytes.length * 2];
        int index = 0;
        for(byte b : bytes) { 
            buf[index++] = HEX_CHAR[b >>> 4 & 0xf];
            buf[index++] = HEX_CHAR[b & 0xf];
        }
        
        
        return new String(buf).toUpperCase();
    }
	
	
	// HEx to byte[]
	 private byte[] HextoBytes(String str) {
        if(str == null || str.trim().equals("")) {
            return new byte[0];
        }

        byte[] bytes = new byte[str.length() / 2];
        for(int i = 0; i < str.length() / 2; i++) {
            String subStr = str.substring(i * 2, i * 2 + 2);
            bytes[i] = (byte) Integer.parseInt(subStr, 16);
        }

        return bytes;
    }

}



