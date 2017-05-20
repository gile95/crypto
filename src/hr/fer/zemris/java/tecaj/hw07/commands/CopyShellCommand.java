package hr.fer.zemris.java.tecaj.hw07.commands;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * Represents one of {@link ShellCommand}s which this Shell supports.
 * Expects two arguments: source file name and destination file name.
 * Copies a source file to a destination file.
 * If destination file already exists, asks whether to overwrite it.
 * If the second argument is directory, copies the original file into that directory
 * using the original file name.
 * Supports file names with whitespaces, which must be bounded with quote marks. (example: copy D:/a.txt "D:/a  a.txt")
 * @author Mislav Gillinger
 * @version 1.0
 */
public class CopyShellCommand implements ShellCommand {

	/** Command name */
	private String commandName = "copy";
	/** Command description. */
	private List<String> commandDescription = new ArrayList<>();
	
	{
		commandDescription.add("Expects two arguments: source file name and destination file name.");
		commandDescription.add("Copies a source file to a destination file.");
		commandDescription.add("If destination file already exists, asks whether to overwrite it.");
		commandDescription.add("If the second argument is directory, copies the original file into that directory"
				+ "using the original file name.");
	}
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) throws IOException {
		
		String[] args = new String[2];
		
		if(arguments.contains("\"")){
			List<String> args1 = ShellCommand.splitArguments(arguments);
			args[0] = args1.get(0);
			args[1] = args1.get(1);
		}
		else{
			args = arguments.trim().split(" ", 2);
			if(args.length != 2){
				env.writeln("Command copy needs two arguments.");
				return ShellStatus.CONTINUE;
			}
		}
		File file1 = new File(args[0]);
		if(file1.isDirectory() || !file1.exists()){
			env.writeln("First argument is a directory, or it does not exist.");
			return ShellStatus.CONTINUE;
		}
	
		File file2 = new File(args[1]);
		
		BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
		
		if(file2.isFile() && file2.exists()){
			while(true){
				env.writeln("Destination file already exists. Do you want to overwrite it? (YES/NO)");
				String answer = inputReader.readLine();
				if(answer.equals("NO")){
					return ShellStatus.CONTINUE;
				}
				if(answer.equals("YES")){
					break;
				}
			}
		}
		
		if(file2.isDirectory()){
			file2 = file2.toPath().resolve(file1.toPath().getFileName()).toFile();
		}
		
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file1));
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file2));
		
		int data;
		while((data = bis.read()) != -1){
			bos.write(data);
			bos.flush();
		} 
		
		bis.close();
		bos.close();
		
		return ShellStatus.CONTINUE;
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
