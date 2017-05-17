package hr.fer.zemris.java.tecaj.hw07.commands;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * Represents one of {@link ShellCommand}s which this Shell supports.
 * Takes one or two arguments. The first argument is path to some file and is mandatory. The 
 * second argument is charset name that should be used to interpret chars from bytes. If not provided,
 * a default platform charset should be used. 
 * This command opens given file and writes its content to a specified output.
 * @author Mislav Gillinger
 * @version 1.0
 */
public class CatShellCommand implements ShellCommand {

	/** Command name */
	private String commandName = "cat";
	/** Command description. */
	private List<String> commandDescription = new ArrayList<>();
	
	{
		commandDescription.add("First argument is mandatory and it represents a path to some file.");
		commandDescription.add("Second argument is optional and represents a charset name that should be used to"
				+ "interpret chars from bytes.");
		commandDescription.add("This command opens given file and writes its content to specified output.");
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
		}
		
		File file = new File(args[0]);
		
		if(file.isDirectory()){
			env.writeln("First argument must be file!");
			return ShellStatus.CONTINUE;
		}
		if(!file.canRead()){
			env.writeln("First argument must be file that exists!");
			return ShellStatus.CONTINUE;
		}
		
		Charset charset;
		if(args.length == 2){
			if(Charset.availableCharsets().containsKey(args[1])){
				charset = Charset.availableCharsets().get(args[1]);
			}
			else{
				env.writeln("Unknown charset!");
				return ShellStatus.CONTINUE;
			}
		}
		else if (args.length == 1){
			charset = Charset.defaultCharset();
		}
		else{
			env.writeln("Invalid number of arguments in command cat!");
			return ShellStatus.CONTINUE;
		}
		
		BufferedReader br = new BufferedReader( 
								new InputStreamReader( 
										new BufferedInputStream( 
												new FileInputStream(args[0])
										),charset
								)
							);
		String line;
		while((line = br.readLine()) != null){
			env.writeln(line);
		}
		
		br.close();
		
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
