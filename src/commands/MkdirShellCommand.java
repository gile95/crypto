package hr.fer.zemris.java.tecaj.hw07.commands;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * Represents one of {@link ShellCommand}s which this Shell supports.
 * Takes a single argument: directory name, and creates the appropriate directory structure.
 * @author Mislav Gillinger
 * @version 1.0
 */
public class MkdirShellCommand implements ShellCommand {

	/** Command name */
	private String commandName = "mkdir";
	/** Command description. */
	private List<String> commandDescription = new ArrayList<>();

	{
		commandDescription.add("Takes a single argument: directory name, and creates the appropriate"
				+ " directory structure.");
	}
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) throws IOException {
		
		File file = new File(arguments);
		
		file.mkdirs();
		
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
