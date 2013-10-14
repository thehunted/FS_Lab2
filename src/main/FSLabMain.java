package main;

import filesystem.FileNode;
import filesystem.Metadata;
import filesystem.ParseFileSystem;
import filesystem.ParseInputCommand;
import filesystem.ParseMetadata;

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
		ParseMetadata metaParser = new ParseMetadata();
		FileNode rootNode = null;
		Metadata metadata = null;
		
		//Banner for Program Start
		System.out.println("\n-------------------------------" );
		System.out.println("  _           _       ___   ");
		System.out.println(" | |         | |     |__ \\ ");
		System.out.println(" | |     __ _| |__      ) |");
		System.out.println(" | |    / _` | '_ \\    / / ");
		System.out.println(" | |___| (_| | |_) |  / /_ ");
		System.out.println(" |______\\__,_|_.__/  |____|\n");
		System.out.println(" File System Lab 2" );
		System.out.println(" Version 1.0" );
		System.out.println("-------------------------------\n\n" );
		
		//Parse root folder, still need to pass in metadata
		System.out.print(" Parsing Metadata .... ");
		if( args.length > 1)
			metadata = metaParser.parseMetaFile( args[1] );
		
		if( metadata != null)
			System.out.println(" Done!");
		else
			System.out.println(" Please specify path to the meta data file");
		
		//Parse root folder, still need to pass in metadata
		System.out.print(" Parsing Filesystem .... ");
		if( args.length > 0)
			rootNode = fsParser.parseAtRoot( args[0] );
		
		if( rootNode != null )
			System.out.println(" Done!");
		else
			System.out.println(" Please specify path to root of File System");
		
		if( rootNode != null && metadata != null )
		{
			//Init the command prompt and start asking the user for commands
			System.out.println(" Starting user command prompt....\n");
			commandPrompt.initCommandLine( rootNode, metadata );
		}
		else
		{
			System.out.println("\nThere are two commandline parameters: ");
			System.out.println(" 1. Path to the meta file");
			System.out.println(" 2. Path to the root of the file system ");
		}
	}

}
