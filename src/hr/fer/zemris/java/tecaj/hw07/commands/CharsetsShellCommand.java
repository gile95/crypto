package hr.fer.zemris.java.tecaj.hw07.commands;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * Represents one of {@link ShellCommand}s which this Shell supports.
 * Lists names of supported charsets for used Java platform.
 * @author Mislav Gillinger
 * @version 1.0
 */
public class CharsetsShellCommand implements ShellCommand {

	/** Command name */
	private String commandName = "charsets";
	/** Command description. */
	private List<String> commandDescription = new ArrayList<>();
	
	{
		commandDescription.add("Lists names of supported charsets for used Java platform.");
	}
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) throws IOException {
		if(arguments != null){
			env.writeln("Command charsets must not have any arguments!");
			return ShellStatus.CONTINUE;
		}
		
		for(String s : Charset.availableCharsets().keySet()){
			env.writeln(s);
		}
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
