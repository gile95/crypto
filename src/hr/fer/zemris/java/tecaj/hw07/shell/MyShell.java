package hr.fer.zemris.java.tecaj.hw07.shell;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import hr.fer.zemris.java.tecaj.hw07.commands.CatShellCommand;
import hr.fer.zemris.java.tecaj.hw07.commands.CharsetsShellCommand;
import hr.fer.zemris.java.tecaj.hw07.commands.CopyShellCommand;
import hr.fer.zemris.java.tecaj.hw07.commands.ExitShellCommand;
import hr.fer.zemris.java.tecaj.hw07.commands.HelpShellCommand;
import hr.fer.zemris.java.tecaj.hw07.commands.HexdumpShellCommand;
import hr.fer.zemris.java.tecaj.hw07.commands.LsShellCommand;
import hr.fer.zemris.java.tecaj.hw07.commands.MkdirShellCommand;
import hr.fer.zemris.java.tecaj.hw07.commands.SymbolShellCommand;
import hr.fer.zemris.java.tecaj.hw07.commands.TreeShellCommand;

/**
 * Represents a shell which is able to work with files and to process data. 
 * 
 * Supported commands are :	<li>{@link SymbolShellCommand},</li>
 * 							<li>{@link CharsetsShellCommand},</li>
 * 							<li>{@link CatShellCommand},</li>
 * 							<li>{@link LsShellCommand},</li>
 * 							<li>{@link TreeShellCommand},</li>
 * 							<li>{@link CopyShellCommand},</li>
 * 							<li>{@link MkdirShellCommand},</li>
 * 							<li>{@link HexdumpShellCommand},</li>
 * 							<li>{@link ExitShellCommand},</li>
 * 							<li>{@link HelpShellCommand}.</li>
 * 
 * Multiple line commands are supported. When a command stretches through multiple lines, on the end of
 * each but the last line it needs to contain a symbol which represents an end of one of multiple lines.
 * Each that kind of line but first one must containat a beginning a symbol which represents a 
 * beginning of one of multiple lines command.
 * @author Mislav Gillinger
 * @version 1.0
 */
public class MyShell {

	/** Map which contains all supported command instances. */
	static Map<String, ShellCommand> commands;
	
	static {
		commands = new LinkedHashMap<>();
		commands.put("symbol", new SymbolShellCommand());
		commands.put("charsets", new CharsetsShellCommand());
		commands.put("cat", new CatShellCommand());
		commands.put("ls", new LsShellCommand());
		commands.put("tree", new TreeShellCommand());
		commands.put("copy", new CopyShellCommand());
		commands.put("mkdir", new MkdirShellCommand());
		commands.put("hexdump", new HexdumpShellCommand());
		commands.put("exit", new ExitShellCommand());
		commands.put("help", new HelpShellCommand());
	}
	
	/**
	 * Program execution starts with this method.	
	 * @param args Command line arguments.
	 * @throws IOException Thrown if an IO error occurs.
	 */
	public static void main(String[] args) throws IOException {
	
		Environment environment = new EnvironmentImpl();
	
		environment.writeln("Welcome to MyShell v 1.0");
		
		ShellStatus status = ShellStatus.CONTINUE;
		
		while(status != ShellStatus.TERMINATE){
			System.out.print(environment.getPromptSymbol() + " ");
			String line = environment.readLine();
			String commandName = getCommandName(line);
			ShellCommand command = commands.get(commandName);
			if(command == null){
				System.out.println("Invalid command name!");
				continue;
			}
			String arguments = getArguments(line);
			status = command.executeCommand(environment, arguments);
		}
	}

	/**
	 * Fetches command arguments.
	 * @param line Command.
	 * @return Command arguments.
	 */
	private static String getArguments(String line) {
		String[] args = line.split("\\s+", 2);
		if(args.length == 2) {
			return args[1];
		}
		return null;
	}

	/**
	 * Fetches a command name.
	 * @param line Command.
	 * @return Command name.
	 */
	private static String getCommandName(String line) {
		String[] args = line.split("\\s+", 2);
		return args[0];
	}
}
