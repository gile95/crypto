package hr.fer.zemris.java.tecaj.hw07.shell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Implementation of interface {@link Environment}. It uses standard input and standard output to
 * read or write data.
 * @author Mislav Gillinger
 * @version 1.0
 */
public class EnvironmentImpl implements Environment {

	/** Symbol which represents a multiLineSymbol. */
	private Character multilineSymbol;
	/** Symbol which represents a promptSymbol. */
	private Character promptSymbol;
	/** Symbol which represents a moreLinesSymbol. */
	private Character morelinesSymbol;
	
	/** Reader which reads from stdin. */
	private BufferedReader inputReader;
	/** Writer which writes on stdout. */
	private BufferedWriter outputWriter;
	
	/**
	 * Creates a new EnvironmentImpl with multiLineSymbol set to '|', promptSymbol set to '>' and 
	 * moreLinesSymbol set to '\'.
	 */
	public EnvironmentImpl(){
		
		multilineSymbol = '|';
		promptSymbol = '>';
		morelinesSymbol = '\\';
		
		inputReader = new BufferedReader(new InputStreamReader(System.in));
		outputWriter = new BufferedWriter(new OutputStreamWriter(System.out));
		
	}
	
	@Override
	public String readLine() throws IOException {
				
		StringBuilder command = new StringBuilder();
		String line = inputReader.readLine();
		line = line.trim();
		
		if(!line.endsWith(morelinesSymbol.toString())){
			return line;
		}
		
		while(line.endsWith(morelinesSymbol.toString())){
			line = line.substring(0, line.length() - 1);
			command.append(line);
			write(multilineSymbol + " ");
			line = inputReader.readLine();
		}
		
		command.append(line);
		
		return command.toString();
		
	}

	@Override
	public void write(String text) throws IOException {
		outputWriter.write(text);
		outputWriter.flush();
	}

	@Override
	public void writeln(String text) throws IOException {
		outputWriter.write(text);
		outputWriter.flush();
		outputWriter.write("\n");
		outputWriter.flush();
	}

	@Override
	public Iterable<ShellCommand> commands() {
		return MyShell.commands.values();
	}

	@Override
	public Character getMultilineSymbol() {
		return multilineSymbol;
	}

	@Override
	public void setMultilineSymbol(Character symbol) {
		multilineSymbol = symbol;
	}

	@Override
	public Character getPromptSymbol() {
		return promptSymbol;
	}

	@Override
	public void setPromptSymbol(Character symbol) {
		promptSymbol = symbol;
	}

	@Override
	public Character getMorelinesSymbol() {
		return morelinesSymbol;
	}

	@Override
	public void setMorelinesSymbol(Character symbol) {
		morelinesSymbol = symbol;
	}

}
