package hr.fer.zemris.java.tecaj.hw07.shell;

import java.io.IOException;

/**
 * Represents an environment which handles the communication between commands and the shell itself, and 
 * communication between commands and output/input.
 * @author Mislav Gillinger
 * @version 1.0
 */
public interface Environment {

	/**
	 * Fetches the next command from given input. One command can also stretch through multiple lines.
	 * If command extends to the next line, it needs to end with a special character called 
	 * 'moreLinesSymbol'. The next line then also starts with a special character called 
	 * 'multiLineSymbol'.
	 * @return Command from the given input.
	 * @throws IOException If an IO error occurs.
	 */
	String readLine() throws IOException;
	/**
	 * Writes the given text to a specified output.
	 * @param text Text to be written out.
	 * @throws IOException If an IO error occurs.
	 */
	void write(String text) throws IOException;
	/**
	 * Writes the given text to a specified output and moves cursor to the next line.
	 * @param text Text to be written out.
	 * @throws IOException If an IO error occurs.
	 */
	void writeln(String text) throws IOException;
	/**
	 * Returns an {@link Iterable} over a list of {@link ShellCommand}s.
	 * @return An {@link Iterable} over a list of {@link ShellCommand}s.
	 */
	Iterable<ShellCommand> commands();
	/**
	 * Fetches the current multiLineSymbol.
	 * @return The current multiLineSymbol.
	 */
	Character getMultilineSymbol();
	/**
	 * Sets the multiLineSymbol on the given one.
	 * @param symbol New multiLineSymbol.
	 */
	void setMultilineSymbol(Character symbol);
	/**
	 * Fetches the current promptSymbol.
	 * @return The current promptSymbol.
	 */
	Character getPromptSymbol();
	/**
	 * Sets the promptSymbol on the given one.
	 * @param symbol New promptSymbol.
	 */
	void setPromptSymbol(Character symbol);
	/**
	 * Fetches the current moreLinesSymbol.
	 * @return The current moreLinesSymbol.
	 */
	Character getMorelinesSymbol();
	/**
	 * Sets the moreLinesSymbol on the given one.
	 * @param symbol New moreLinesSymbol.
	 */
	void setMorelinesSymbol(Character symbol);
}
