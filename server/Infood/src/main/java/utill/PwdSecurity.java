package utill;

import java.security.MessageDigest;

public class PwdSecurity {

	public static String getSHA256(String pwd){
	    StringBuffer sbuf = new StringBuffer();
	    try {
	    	MessageDigest mDigest = MessageDigest.getInstance("SHA-256");
	    	mDigest.update(pwd.getBytes());
	    	
	    	byte[] msgStr = mDigest.digest();
	    	
	    	for(int i=0; i < msgStr.length; i++){
	    		byte tmpStrByte = msgStr[i];
	    		String tmpEncTxt = Integer.toString((tmpStrByte & 0xff) 
	    				+ 0x100, 16).substring(1);
	    		
	    		sbuf.append(tmpEncTxt);
	    	}
	    	
	    }catch(Exception e) {
	    	
	    }
	    return sbuf.toString();
	}

}
