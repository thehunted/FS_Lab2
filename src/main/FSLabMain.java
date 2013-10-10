package main;

import command.ParseInputCommand;
import filesystem.FileNode;
import filesystem.ParseFileSystem;

/**
 * 
 */

/**
 * @author bafarnwo
 *
 */
public class FSLabMain 
{

	/**
	 * @param args
	 */
	public static void main( String[] args ) 
	{
		ParseInputCommand commandPrompt = new ParseInputCommand();
		ParseFileSystem fsParser = new ParseFileSystem();
		FileNode rootNode = null;
		
		System.out.println("\n-------------------------------" );
		System.out.println("  _           _       ___   ");
		System.out.println(" | |         | |     |__ \\ ");
		System.out.println(" | |     __ _| |__      ) |");
		System.out.println(" | |    / _` | '_ \\    / / ");
		System.out.println(" | |___| (_| | |_) |  / /_ ");
		System.out.println(" |______\\__,_|_.__/  |____|\n");
		System.out.println("File System Lab 2" );
		System.out.println("Version 1.0" );
		System.out.println("-------------------------------\n\n" );
		
		//Here we'll need to parse the metafile
		
		//Parse root folder, still need to pass in metadata
		if( args.length > 0)
			rootNode = fsParser.parseAtRoot( args[0] );
		
		//Init the command prompt and start asking the user for commands
		if( rootNode != null )
			commandPrompt.initCommandLine( rootNode );
		else
			System.out.println("Please specify a root folder to parse on the commandline");
	}

}
