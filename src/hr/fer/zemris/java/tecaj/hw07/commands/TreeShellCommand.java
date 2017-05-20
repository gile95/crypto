package hr.fer.zemris.java.tecaj.hw07.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * Represents one of {@link ShellCommand}s which this Shell supports.
 * Expects a single argument: directory name and prints a tree (each directory level shifts 
 * output two characters to the right).
 * @author Mislav Gillinger
 * @version 1.0
 */
public class TreeShellCommand implements ShellCommand {

	/** Command name */
	private String commandName = "tree";
	/** Command description. */
	private List<String> commandDescription = new ArrayList<>();
	
	{
		commandDescription.add("Expects a single argument: directory name and prints a tree (each directory"
				+ " level shifts output two characters to the right).");
	}
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) throws IOException {
		
		File file = new File(arguments);
		
		if(!file.isDirectory()){
			env.writeln("Argument must be directory!");
			return ShellStatus.CONTINUE;
		}
		
		Files.walkFileTree(Paths.get(arguments), new SimpleFileVisitor<Path>(){
			
			private int level;
			
			public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
				ispisi(dir);
				level++;
				return FileVisitResult.CONTINUE;
			}

			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				ispisi(file);
				return FileVisitResult.CONTINUE;
			}
			
			public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
				level--;
				return FileVisitResult.CONTINUE;
			}
			
			private void ispisi(Path file) {
				if(level == 0) {
					System.out.println(file.normalize().toAbsolutePath());
				}
				else{
					System.out.printf("%" + (2*level) + "s%s%n", "", file.getFileName());
				}
			}
		});
		
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
