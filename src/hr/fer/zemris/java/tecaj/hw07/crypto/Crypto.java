package hr.fer.zemris.java.tecaj.hw07.crypto;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * This class allows user to encrypt/decrypt given file using the AES cryptoalgorithm and the 128-bit encryption
 * key, or calculate and check the SHA-256 file digest.
 * Checking the sha-256 file digest can be done with command "checksha", which expects a path to a file as an argument.
 * Encrypting / decrypting can be done using the commands encrypt / decrypt. Its first argument is file to be encrypted
 * / decrypted, and the second one is a destination file which will be created. 
 * @author Mislav Gillinger
 * @version 1.0
 */
public class Crypto {

	/**
	 * Program execution starts with this method.
	 * @param args Command line arguments.
	 */
	public static void main(String[] args) {
		
		try{
			BufferedReader inputReader = null;
			try{
				inputReader = new BufferedReader(new InputStreamReader(System.in));
			}catch(Exception e){
				System.out.println("Couldn't read from standard input!");
			}
		
			
			switch(args[0]){
				case "checksha" : {
					processChecksha(inputReader, args[1]);
					break;
				}
				case "encrypt" :
				case "decrypt" : {
					boolean encrypt = false;
					if(args[0].equals("encrypt")) encrypt = true;
					
					System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
					System.out.print("> ");
					String keyText = inputReader.readLine();
					System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits):");
					System.out.print("> ");
					String ivText = inputReader.readLine();
					
					SecretKeySpec keySpec = new SecretKeySpec(hextobyte(keyText), "AES");
					AlgorithmParameterSpec paramSpec = new IvParameterSpec(hextobyte(ivText));
					Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
					cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
					
					crypt(cipher, args[1], args[2]);
					break;
				}
				default : {
					System.out.println("Invalid command!");
					break;
				}
			}
		}catch(GeneralSecurityException e){
			System.err.println("Security exception: " + e.getMessage());
			System.exit(-1);
		}catch(IOException e){
			System.err.println("IO exception: " + e.getMessage());
			System.exit(-1);
		}
	}
	
	/**
	 * Method used for encrypting / decrypting a file. 
	 * @param cipher Object which is instance of {@link Cipher}. It was previously initialized.
	 * @param oldFile New file will be made based on this file.
	 * @param newFile Destination file which will be created, containing the encrypted / decrypted data..
	 * @throws IOException If an IO error occurs.
	 * @throws IllegalBlockSizeException Thrown when the length of data provided to a block cipher is incorrect.
	 * @throws BadPaddingException Thrown when a particular padding mechanism is expected for the input data,
	 * 	but the data is not padded properly.
	 */
	private static void crypt(Cipher cipher, String oldFile, String newFile) throws IOException, 
																				IllegalBlockSizeException, 
																				BadPaddingException {
		
		BufferedInputStream fileReader = null;
		try {
			fileReader = new BufferedInputStream(new FileInputStream(new File("./" + oldFile)));
		} catch (Exception e) {
			System.out.println("Couldn't read from file!");
			System.exit(-1);
		}
		
		BufferedOutputStream fileWriter = null;
		try {
			fileWriter = new BufferedOutputStream(new FileOutputStream(new File("./" + newFile)));
		} catch (Exception e) {
			System.out.println("Couldn't read from file!");
			System.exit(-1);
		}
		
		byte[] bytes = new byte[16];
		
		while(fileReader.read(bytes) != -1){
			fileWriter.write(cipher.update(bytes));
			fileWriter.flush();
		}
		fileWriter.write(cipher.doFinal());
		fileWriter.flush();
		
		System.out.println("Completed.");
	}

	/**
	 * Calculates and checks sha-256 file digest.
	 * @param inputReader Reader which reads from System.in.
	 * @param file File which content will be digested.
	 * @throws NoSuchAlgorithmException Thrown when a particular cryptographic algorithm is requested but is not 
	 * available in the environment.
	 * @throws IOException Thrown if an IO error occurs.
	 */
	private static void processChecksha(BufferedReader inputReader, String file) throws NoSuchAlgorithmException, 
																					IOException {
		System.out.println("Please provide expected sha-256 digest for " + file + ":");
		System.out.print("> ");
		
		BufferedInputStream fileReader = null;
		try {
			fileReader = new BufferedInputStream(new FileInputStream(new File("./" + file)));
		} catch (Exception e) {
			System.out.println("Couldn't read from file!");
		}
		
		byte[] bytes = new byte[16];
		
		MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
		while(fileReader.read(bytes) != -1){
			messageDigest.update(bytes);
		}
		byte[] hash = messageDigest.digest();
		
		String expectedDigest = inputReader.readLine();
		
		if(new String(hash).equals(new String(hextobyte(expectedDigest)))){
			System.out.println("Digesting completed. Digest of " + file + " matches expected digest.");
		}
		else{
			
			String correctDigest = bytetohex(hash);
			
			System.out.println("Digesting completed. Digest of " + file + " does not match the expected digest. "
					+ "Digest was: " + correctDigest);
		}
	}

	/**
	 * Converts an array of bytes to a string which is hex-encoded.
	 * @param hash Array of bytes which will be converted.
	 * @return Hex-encoded string.
	 */
	private static String bytetohex(byte[] hash) {
		StringBuffer hexDigest = new StringBuffer();
		for(int i = 0; i < hash.length; i++){
			hexDigest.append(Integer.toString((hash[i]&0xff)+0x100, 16).substring(1));
		}
		return hexDigest.toString();
	}

	/**
	 * Converts a hex-encoded string into an array of bytes.
	 * @param keyText Hex-encoded string.
	 * @return Array of bytes.
	 */
	public static byte[] hextobyte(String keyText){
		int len = keyText.length();
	    byte[] data = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) {
	        data[i / 2] = (byte) ((Character.digit(keyText.charAt(i), 16) << 4)
	                             + Character.digit(keyText.charAt(i+1), 16));
	    }
	    return data;
	}
}
