package filesystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.StringTokenizer;

public class ParseMetadata 
{

	private final String DIR = "Directory:";
	private final String USERS = "Users:";
	private final String FILE = "File:";
	private final String OWNER = "Owner:";
	private final String ACE = "ACE:";
	private Metadata metaData;

	public ParseMetadata( )
	{
		super();
		metaData = new Metadata();
		
	}
	
	public Metadata getMetaData() 
	{
		return metaData;
	}	
	
	public Metadata parseMetaFile( String location )
	{
		File metaFile = null;
		BufferedReader br = null;
		
		try 
		{
			String line;
			
			metaFile = new File( location );
			br = new BufferedReader( new FileReader( metaFile ) );
 
			while ( ( line = br.readLine() ) != null ) 
				parseMetaLine( line );
			
			br.close();
 
		} 
		catch (IOException e) 
		{
			System.out.println("Unable to open file Metafile " );
			//e.printStackTrace();
		}
		
		return metaData;
	}


	public MetaRule parseMetaLine( String line ) 
	{
		StringTokenizer token = new StringTokenizer( line, " " );
		MetaRule rule = null;
		
		if( token.countTokens() != 0)
		{
			String ruleType = token.nextToken();
			
			if( ruleType.equals( USERS ) )
			{
				parseUsers( token );
			}
			else if( ruleType.equals( DIR ) || ruleType.equals( FILE ) )
			{
				rule = parseDirectoryFile( token );
			}
			
		}
		
		return rule;
	}

	private void parseUsers( StringTokenizer token )
	{
		while( token.hasMoreElements() )
		{
			String user = token.nextToken();
			metaData.addUsers(user, user);
		}
		
	}
	

	/**
	 * Example of value that will be parsed:
	 * Directory: dir1 Owner: user1 ACE: user2 allow rw
	 * File: foo1.txt Owner: user1 ACE: user1 deny rw ACE: user2 allow r
	 * @param token
	 */
	private MetaRule parseDirectoryFile( StringTokenizer token )
	{
		Hashtable <String, AceRule> aceRules = new Hashtable <String, AceRule>();
		MetaRule metarule = null;
		while( token.hasMoreElements() )
		{
			String fileNodeName;
			String owner;
			String userName, allowDeny, readWrite;
			
			//Directory/File Name
			fileNodeName = token.nextToken();
			
			//Owner Tag
			token.nextToken();
			
			//Owner Name
			owner = token.nextToken();
			
			//Parsing ACE values
			while( token.hasMoreElements() )
			{
				String ace = token.nextToken();

				if( ace.equals( ACE ) )
				{
					AceRule rule;
					userName = token.nextToken();
					allowDeny = token.nextToken();
					readWrite = token.nextToken();
					
					rule = new AceRule( userName, allowDeny, readWrite );
					aceRules.put( rule.getName(), rule );
				}
			}

			metarule = new MetaRule( owner, fileNodeName, aceRules );
			metaData.addMetaRule( fileNodeName, metarule );
		
		}
		
		return metarule;
	}

}
