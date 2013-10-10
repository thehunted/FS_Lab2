package command;
import java.util.Scanner;
import java.util.StringTokenizer;

import filesystem.FileNode;

public class ParseInputCommand 
{
	private FileNode rootNode;
	private FileNode currentNode;
	
	//This is a list of supported user commands
	private static final String SET_USER 	= "set";
	private static final String CD 			= "cd";
	private static final String PWD 		= "pwd";
	private static final String READ 		= "read";
	private static final String WRITE 		= "write";
	private static final String CM 			= "cm";
	private static final String LM 			= "lm";
	private static final String RM 			= "rm";
	private static final String HELP 		= "help";
	private static final String EXIT		= "exit";
	
	public ParseInputCommand( )
	{
		super();
	}
	
	/**
	 * This will initialize the user command line input method
	 */
	public void initCommandLine( FileNode root )
	{
		String command = null, result = null;
		Scanner console = new Scanner( System.in );
		
		rootNode = root;
		currentNode = root;
		
		//This will wait on the user input forever unless the user types exit
		do
		{
			System.out.print(currentNode.getName() + "> ");
			command = console.nextLine();
			result = parseCommand ( command );
	
			System.out.print( result );
			
		}while( !command.equals( EXIT ) );
		
		console.close();
	}
	
	/**
	 * This method will take a string input and return a the result from the user
	 * Every might not return an indication that it worked.
	 * 
	 * @param command
	 * @return
	 */
	private String parseCommand( String input )
	{
		String result = "", command = "";
		StringTokenizer token = new StringTokenizer( input, " "); //Tokenizes the input
				
		//checks to make sure the input is valid, meaning not blank line
		if( token.countTokens() == 0 )
			return result;
		
		//gets the next token i.e command
		command = token.nextToken();
		
		//parses the first token which will always be the command
		if( command.equals( EXIT ) )
			return result;
		else if( command.equals( SET_USER ) )
			result = runUserCommand( token );
		else if( command.equals( CD ) )
			result = runCDCommand( token );
		else if( command.equals( PWD ) )
			result = runPWDCommand( token );
		else if( command.equals( READ ) )
			result = runREADCommand( token );
		else if( command.equals( WRITE ) )
			result = runWRITECommand( token );
		else if( command.equals( CM ) )
			result = runCMCommand( token );
		else if( command.equals( LM ) )
			result = runLMCommand( token );
		else if( command.equals( RM ) )
			result = runRMCommand( token );
		else if( command.equals( HELP ) )
			result = runHELPCommand( );
		else
			result = command + " is an unknown command use 'help'\n";
		
		return result;
	}

	/**
	 * Displays a menu to the user of all the available commands
	 * 
	 * @return
	 */
	private String runHELPCommand( ) 
	{
		String printCommands;
		
		//Creates a fake menu and lists out the commands and their functions
		printCommands = 
				"\nFile System Lab2 Command List \n" +
				"--------------------------------------------------------------------\n" +
				" set user <user>- changes the curren user to <user>\n" +
				" cd <dir> - changes the ccurrent working director to <dir>\n" +
				" pwd - prints present working directory\n" +
				" read <resource> - Reads the resource\n" +
				" write <resource> <value> - Writes to the resouce\n"+
				" cm <resource> <value>- This sets the metata data for a give resource\n" +
				" lm - lists the current metadata values\n" +
				" rm <number> - removes the metadata rule specified by num\n" +
				" exit - exit closes the program\n\n";
		
		return printCommands;
	}

	private String runRMCommand( StringTokenizer token ) 
	{

		return null;
	}

	private String runLMCommand( StringTokenizer token ) 
	{
		String listOfNodes = "\n";
		
		//Iterate through the list of files and print them out
		for( int i = 0; i < currentNode.getChildren().size(); i++ )
		{
			listOfNodes += currentNode.getChildren().get(i).getName() + "\n";
		}
		
		listOfNodes += "\n";
		return listOfNodes;
	}

	private String runCMCommand( StringTokenizer token ) 
	{

		return null;
	}

	private String runWRITECommand( StringTokenizer token) 
	{


		return null;
	}

	private String runREADCommand( StringTokenizer token ) 
	{


		return null;
	}

	private String runPWDCommand( StringTokenizer token ) 
	{


		return null;
	}

	private String runCDCommand( StringTokenizer token )
	{


		return null;
	}

	private String runUserCommand( StringTokenizer token) 
	{
		
		
		return null;
	}

}
