package hr.fer.zemris.java.tecaj.hw07.commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * Represents one of {@link ShellCommand}s which this Shell supports.
 * If one argument is provided, it returns a symbol used for given characteristics. It can be 
 * PROMPT, MORELINES or MULTILINE. 
 * If two arguments are provided, first one represents a symbol which we want to change, and the second
 * represents a new symbol. Valid first arguments are PROMPT, MORELINES or MULTILINE.
 * @author Mislav Gillinger
 * @version 1.0
 */
public class SymbolShellCommand implements ShellCommand{

	/** Command name */
	private String commandName = "symbol";
	/** Command description. */
	private List<String> commandDescription = new ArrayList<>();
	
	{
		commandDescription.add("If one argument is provided, it returns a symbol used for given characteristics.");
		commandDescription.add("It can be PROMPT, MORELINES or MULTILINE.");
		commandDescription.add("If two arguments are provided, first one represents a symbol which we want to"
				+ " change, and the second represents a new symbol.");
		commandDescription.add("Valid first arguments are PROMPT, MORELINES or MULTILINE.");
	}
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) throws IOException {
		String[] elements = arguments.split("\\s+");
		
		if(elements.length > 2 || elements.length < 1){
			env.writeln("Command symbol has invalid number of arguments!");
			return ShellStatus.CONTINUE;
		}
		else if (elements.length == 1){
			switch (elements[0]){
				case "PROMPT" : {
					env.writeln("Symbol for PROMPT is '" + env.getPromptSymbol() + "'");
					return ShellStatus.CONTINUE;
				}
				case "MORELINES" : {
					env.writeln("Symbol for MORELINES is '" + env.getMorelinesSymbol() + "'");
					return ShellStatus.CONTINUE;
				}
				case "MULTILINE" : {
					env.writeln("Symbol for MULTILINES is '" + env.getMultilineSymbol() + "'");
					return ShellStatus.CONTINUE;
				}
				default : {
					env.writeln("Invalid syntax of command symbol!");
					return ShellStatus.CONTINUE;
				}
			}
		}
		
		Character oldSymbol;
		switch (elements[0]){
			case "PROMPT" : {
				oldSymbol = env.getPromptSymbol();
				env.setPromptSymbol(elements[1].charAt(0));
				break;
			}
			case "MORELINES" : {
				oldSymbol = env.getMorelinesSymbol();
				env.setMorelinesSymbol(elements[1].charAt(0));
				break;
			}
			case "MULTILINE" : {
				oldSymbol = env.getMultilineSymbol();
				env.setMultilineSymbol(elements[1].charAt(0));
				break;
			}
			default : {
				env.writeln("Invalid syntax of command symbol!");
				return ShellStatus.CONTINUE;
			}
		}
			
		env.writeln("Symbol for " + elements[0] + " changed from '" + oldSymbol + "' to '" + elements[1] + "'");
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
