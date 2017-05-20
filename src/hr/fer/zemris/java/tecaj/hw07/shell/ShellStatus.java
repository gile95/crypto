package hr.fer.zemris.java.tecaj.hw07.shell;

/**
 * Represents types of actions which a command can return. These values navigate the program execution.
 * @author Mislav Gillinger
 * @version 1.0
 */
public enum ShellStatus {
	/** Continues with the program execution, allow user to enter a new command. */
	CONTINUE, 
	/** Terminates the program execution. */
	TERMINATE;
}
