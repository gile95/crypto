package hr.fer.zemris.java.tecaj.hw07.commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * Represents one of {@link ShellCommand}s which this Shell supports.
 * If no arguments are provided, only prints a list of supported commands. 
 * If an argument which is one of the names of commands is provided, prints a command description.
 * @author Mislav Gillinger
 * @version 1.0
 */
public class HelpShellCommand implements ShellCommand {

	/** Command name */
	private String commandName = "help";
	/** Command description. */
	private List<String> commandDescription = new ArrayList<>();
	
	{
		commandDescription.add("If no arguments are provided, only prints a list of supported commands.");
		commandDescription.add("If an argument which is one of the names of commands is provided, prints a "
				+ "command description.");
	}
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) throws IOException {
		if(arguments == null){
			env.writeln("Supported commands are:");
			for(ShellCommand command : env.commands()){
				env.writeln(command.getCommandName());
			}
		}
		else{
			boolean found = false;
			
			for(ShellCommand command : env.commands()){
				if(command.getCommandName().equals(arguments)){
					found = true;
					env.writeln("Command name: " + command.getCommandName());
					env.writeln("Command description: ");
					for(String line : command.getCommandDescription()){
						env.writeln(line);
					}
				}
			}
			
			if(!found){
				env.writeln("Unknown command.");
			}
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
