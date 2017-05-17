package hr.fer.zemris.java.tecaj.hw07.commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * Represents one of {@link ShellCommand}s which this Shell supports.
 * When this command is called, the program terminates.
 * @author Mislav Gillinger
 * @version 1.0
 */
public class ExitShellCommand implements ShellCommand {

	/** Command name */
	private String commandName = "exit";
	/** Command description. */
	private List<String> commandDescription = new ArrayList<>();
	
	{
		commandDescription.add("Terminates the program.");
	}
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) throws IOException {
		env.writeln("Bye bye!");
		return ShellStatus.TERMINATE;
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
