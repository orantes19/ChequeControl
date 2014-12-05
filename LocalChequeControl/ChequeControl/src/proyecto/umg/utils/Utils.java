package proyecto.umg.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.naming.Context;
import javax.naming.InitialContext;

public class Utils {
	public static String SHA256 = "SHA-256";

	public static String convertSha2(String string){
		return getStringMessageDigest(string, SHA256);
	}
	
	public static Integer obtenerIntJNDI(String nombre) {
		try {
			Context ctx = new InitialContext();
			return (Integer) ctx.lookup("java:comp/env/" + nombre);
		} catch (Exception ex) {
			System.out.println("WARN: obtenerStringJNDI() " + ex.getMessage());
		}
		return 0;
	}
	
	public static String getStringMessageDigest(String message, String algorithm){
		
		        byte[] digest = null;
		
		        byte[] buffer = message.getBytes();
		
		        try {
		
		            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);		
		            messageDigest.reset();		
		            messageDigest.update(buffer);		
		            digest = messageDigest.digest();		
		        } catch (NoSuchAlgorithmException ex) {		
		            System.out.println("Error creando Digest");		        }
		
		        return toHexadecimal(digest);
		
		    }
	
	 /***
	
	     * Convierte un arreglo de bytes a String usando valores hexadecimales
	
	     * @param digest arreglo de bytes a convertir
	
	     * @return String creado a partir de <code>digest</code>
	
	     */
	
	    private static String toHexadecimal(byte[] digest){
	
	        String hash = "";	
	        for(byte aux : digest) {	
	            int b = aux & 0xff;	
	            if (Integer.toHexString(b).length() == 1) hash += "0";	
	            hash += Integer.toHexString(b);	
	        }
	
	        return hash;
	
	    }
}
