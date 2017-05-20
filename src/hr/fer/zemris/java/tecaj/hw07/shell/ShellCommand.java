package hr.fer.zemris.java.tecaj.hw07.shell;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Determines basic methods every command should have. If one command's arguments is a path to a file, and if 
 * that file contains whitespaces in its name, a path must be bounded with quote marks.
 * @author Mislav Gillinger
 * @version 1.0
 */
public interface ShellCommand {
	
	/**
	 * Processes the arguments and executes a command based on those arguments.
	 * @param env Implementation of {@link Environment} which represents a connection between a shell
	 * and a command.
	 * @param arguments Command arguments.
	 * @return One of {@link ShellStatus}es, depending on the command.
	 * @throws IOException Thrown if an IO error occurs.
	 */
	ShellStatus executeCommand(Environment env, String arguments) throws IOException;
	
	/**
	 * Fetches the command name.
	 * @return The command name.
	 */
	String getCommandName();
	
	/**
	 * Fetches the command description.
	 * @return The command description.
	 */
	List<String> getCommandDescription();
	
	/**
	 * If one of two of this method's arguments if a file containing a whitespace in its name, this method will
	 * return that file name, which was bounded with " and ", as a valid file name, without " and ".
	 * Example1 : "D:/first file.txt" "D:/second file.txt" -> D:/first file.txt, D:/second file.txt
	 * Example2 : "D:/first file.txt" D:/second.txt -> D:/first file.txt, D:/second.txt
	 * @param arguments Strings which can represent file names containing whitespaces.
	 * @return Valid list of two strings without quote marks.
	 */
	public static List<String> splitArguments(String arguments) {
		Pattern regex = Pattern.compile("\"*([^\"]*)\"*\\s+\"*([^\"]*)\"*");
		Matcher matcher = regex.matcher(arguments);
		
		String arg1 = null;
		String arg2 = null;
		
		if(matcher.find()){
			arg1 = matcher.group(1);
			arg2 = matcher.group(2);
		}
		
		List<String> args = new ArrayList<>();
		args.add(arg1);
		args.add(arg2);
		
		return args;
	}
}
