package hr.fer.zemris.java.tecaj.hw07.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * Represents one of {@link ShellCommand}s which this Shell supports.
 * Takes a single argument – directory – and writes a list of all sub-directories in form:
 * file type - size - creation date - file name
 * The output consists of 4 columns. First column indicates if current object is directory
 * (d), readable (r), writable (w) and executable (x). Second column contains object size in bytes that
 * is right aligned and occupies 10 characters. Follows file creation date/time and finally file name.
 * @author Mislav Gillinger
 * @version 1.0
 */
public class LsShellCommand implements ShellCommand {

	/** Command name */
	private String commandName = "ls";
	/** Command description. */
	private List<String> commandDescription = new ArrayList<>();
	
	{
		commandDescription.add("Takes a single argument – directory – and writes a list of all sub-directories"
				+ " in form: file type - size - creation date - file name");
		commandDescription.add("The output consists of 4 columns.");
		commandDescription.add("First column indicates if current object is directory (d), readable (r), "
				+ "writable (w) and executable (x).");
		commandDescription.add("Second column contains object size in bytes that is right aligned and "
				+ "occupies 10 characters.");
		commandDescription.add("Follows file creation date/time and finally file name.");
	}
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) throws IOException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		File file = new File(arguments);
		
		if(!file.canRead()){
			env.writeln("First argument must be file that exists!");
			return ShellStatus.CONTINUE;
		}
		
		for(File child : file.listFiles()){
			
			String type = determineType(child);
			
			Long size;
			if(child.isFile()){
				size = child.length();
			}
			else{
				size = calculateDirSize(child);
			}
			
			Path path = Paths.get(child.toString());
			BasicFileAttributeView faView = Files.getFileAttributeView(
					path, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS
			);
			BasicFileAttributes attributes = faView.readAttributes();
			
			FileTime fileTime = attributes.creationTime();
			String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
			String time = formattedDateTime;
			
			String name = child.getName();
			
			System.out.format("%s %10d %s %s%n", type, size, time, name);
		}
		
		return ShellStatus.CONTINUE;
	}

	/**
	 * Calculates a size of a directory.
	 * @param child Directory which size will be calculated.
	 * @return Size of a directory.
	 */
	private Long calculateDirSize(File child) {
		long length = 0;
	    for (File file : child.listFiles()) {
	        if (file.isFile())
	            length += file.length();
	        else
	            length += calculateDirSize(file);
	    }
	    return length;
	}

	/**
	 * Determines whether a file is directory, is it readable, writable or executable.
	 * @param child File to be analyzed.
	 * @return String representation where a letter means that specific characteristics is true, or 
	 * '-' if not. 
	 */
	private String determineType(File child) {
		StringBuilder type = new StringBuilder();
		
		if(child.isDirectory()){
			type.append("d");
		}
		else{
			type.append("-");
		}
		if(child.canRead()){
			type.append("r");
		}
		else{
			type.append("-");
		}
		if(child.canWrite()){
			type.append("w");
		}
		else{
			type.append("-");
		}
		if(child.canExecute()){
			type.append("x");
		}
		else{
			type.append("-");
		}
		return type.toString();
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
