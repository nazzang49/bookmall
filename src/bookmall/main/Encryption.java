package bookmall.main;

import java.security.*;

public class Encryption {

    public String encryptSHA256(String str){
    	String sha = ""; 
    	try{
    		MessageDigest sh = MessageDigest.getInstance("SHA-256"); 
    		sh.update(str.getBytes()); 
    		byte byteData[] = sh.digest();
    		StringBuffer sb = new StringBuffer(); 
    		for(int i = 0 ; i < byteData.length ; i++){
    			sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
    		}
    		sha = sb.toString();
    	}catch(NoSuchAlgorithmException e){
    		System.out.println("[에러발생]");
    		sha = null; 
    	}
    	return sha;
    }
    
    public String encryptMD5(String str){
    	String md5 = ""; 
    	try{
    		MessageDigest md = MessageDigest.getInstance("MD5"); 
    		md.update(str.getBytes()); 
    		byte byteData[] = md.digest();
    		StringBuffer sb = new StringBuffer(); 
    		for(int i = 0 ; i < byteData.length ; i++){
    			sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
    		}
    		md5 = sb.toString();
    	} catch(NoSuchAlgorithmException e){
    		System.out.println("[에러발생]");
    		md5 = null; 
    	}
    	return md5;
    }
}
