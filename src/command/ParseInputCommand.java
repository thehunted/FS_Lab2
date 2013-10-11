package command;
import java.util.Scanner;
import java.util.StringTokenizer;

import filesystem.FileNode;

public class ParseInputCommand 
{
	private FileNode rootNode;
	private FileNode currentNode;
	private String user;
	
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
		user = "user1";
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
			System.out.print( user + "@" + currentNode.getName() + "> ");
			command = console.nextLine();
			result = parseCommand ( command );
	
			if( result != null )
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
			runUserCommand( token );
		else if( command.equals( CD ) )
			runCDCommand( token );
		else if( command.equals( PWD ) )
			result = runPWDCommand( token );
		else if( command.equals( READ ) )
			result = runREADCommand( token );
		else if( command.equals( WRITE ) )
			runWRITECommand( token );
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
				" set user <user>- changes the current user to <user>\n" +
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

		return null;
	}

	private String runCMCommand( StringTokenizer token ) 
	{

		
		return null;
	}

	private void runWRITECommand( StringTokenizer token) 
	{
		FileNode relativeNode;
		String arg = null;
		
		if( token.hasMoreTokens() )
		{
			arg= token.nextToken();
			relativeNode = resolvePath( arg );
		}
		else
		{
			relativeNode = currentNode;
		}
		
		relativeNode.write( arg );
	}

	private String runREADCommand( StringTokenizer token ) 
	{
		FileNode relativeNode;
		String arg = null;
		
		if( token.hasMoreTokens() )
		{
			arg= token.nextToken();
			relativeNode = resolvePath( arg );
		}
		else
		{
			relativeNode = currentNode;
		}
		
		return relativeNode.read( arg );
	}

	private String runPWDCommand( StringTokenizer token ) 
	{
		return resolvePathToRoot() + "\n";
	}

	private void runCDCommand( StringTokenizer token )
	{
		String arg = token.nextToken();
		
		currentNode = resolvePath( arg );
	}

	private void runUserCommand( StringTokenizer token ) 
	{
		if(token.nextToken().equals( "user") )
		{
			user = token.nextToken();
			currentNode = rootNode;
		}
	}
	
	private FileNode resolvePath( String arg )
	{
		StringTokenizer newPath = new StringTokenizer( arg, "/" );
		FileNode tempNode;
		
		if( arg.startsWith("/") )
			tempNode = rootNode;
		else
			tempNode = currentNode;
		
		if( newPath.countTokens() == 0 )
		{
			return rootNode;
		}
		
		do
		{
			String path = newPath.nextToken();
			
			if( path.equals("..") )
			{
				if( tempNode.getParent() != null )
				{
						tempNode = tempNode.getParent();
				}
				else
				{
					System.out.println("Can't navigate above the root");
					return currentNode;
				}
			}
			else
			{
				if( tempNode.getChildren().get( path ) != null )
				{
					tempNode = tempNode.getChildren().get( path );
				}
				else
				{
					System.out.println("Cannot find path specified");
					return currentNode;
				}
			}
			
		}while( newPath.hasMoreElements() );
		
		return tempNode;
	}
	
	private String resolvePathToRoot()
	{
		FileNode temp = currentNode;
		String pwd = temp.getName();
		
		while( temp.getParent() != null )
		{
			temp = temp.getParent();
			pwd = temp.getName() + "/" + pwd;
		}
		
		return "/" + pwd;
	}

}
