package hr.fer.zemris.java.tecaj.hw07.commands;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * Represents one of {@link ShellCommand}s which this Shell supports.
 * Expects a single argument: file name, and produces its hex-output as illustrated below.
 * On the right side of the image only a standard subset of characters is shown;
 * for all other characters a '.' is printed instead.
 * (Replaces all bytes whose value is less than 32 or greater than 127 with '.')
 * <br>example -> 00000000: 31 2e 20 4f 62 6a 65 63|74 53 74 61 63 6b 20 69 | 1. ObjectStack i
 * @author Mislav Gillinger
 * @version 1.0
 */
public class HexdumpShellCommand implements ShellCommand {

	/** Command name */
	private String commandName = "hexdump";
	/** Command description. */
	private List<String> commandDescription = new ArrayList<>();
	
	{
		commandDescription.add("Expects a single argument: file name, and produces its hex-output"
				+ " as illustrated below.");
		commandDescription.add("On the right side of the image only a standard subset of characters is shown;"
				+ " for all other characters a '.' is printed instead.");
		commandDescription.add("(Replaces all bytes whose value is less than 32 or greater than 127 with '.')");
		commandDescription.add("example -> 00000000: 31 2e 20 4f 62 6a 65 63|74 53 74 61 63 6b 20 69 | 1. ObjectStack i");
	}
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) throws IOException {
		
		File file = new File(arguments);
		
		if(file.isDirectory()){
			env.writeln("Argument in command hexdump must be a file!");
			return ShellStatus.CONTINUE;
		}
		if(!file.canRead()){
			env.writeln("First argument must be file that exists!");
			return ShellStatus.CONTINUE;
		}
		
		BufferedInputStream inputReader = new BufferedInputStream(new FileInputStream(file));
		byte[] buffer = new byte[16];
		int offset = 0;
		int num = 0;
		
		while((num = inputReader.read(buffer)) != -1){
			
			for(int i = 0; i < 8 - String.valueOf(offset).length(); i++){
				env.write("0");
			}
			env.write(offset + " ");
			
			String hex = bytetohex(buffer);
			char[] hexArray = hex.toCharArray();
			for(int i = 0; i < 32; i = i + 2){
				if(hexArray[i] == '0' && hexArray[i+1] == '0'){
					env.write("  ");
				}
				else{
					env.write(String.valueOf(hexArray[i]));
					env.write(String.valueOf(hexArray[i+1]));
				}
				
				if(i == 14){
					env.write("|");
				}
				else{
					env.write(" ");
				}
			}
			env.write("| ");
			
			for(int i = 0; i < 16; i++){
				if(buffer[i] < 32 || buffer[i] > 127){
					buffer[i] = 46;
				}
			}
			
			byte[] print = new byte[num];
			for(int i = 0; i < num; i++){
				print[i] = buffer[i];
			}
			
			env.writeln(new String(print));
			
			buffer = new byte[16];	
			offset += 10;
		}
		
		inputReader.close();
		
		return ShellStatus.CONTINUE;
	}

	/**
	 * Converts an array of bytes to a string which is hex.encoded.
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
	
	@Override
	public String getCommandName() {
		return commandName;
	}

	@Override
	public List<String> getCommandDescription() {
		return commandDescription;
	}

}
