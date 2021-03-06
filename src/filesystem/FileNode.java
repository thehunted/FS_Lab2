package filesystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

public class FileNode 
{
	private String name;
	private String path;
	private boolean readAccess;
	private boolean writeAccess;
	private boolean viewAble;
	private boolean file; //true is a file, false is a folder
	private FileNode parent;
	private Hashtable<String, FileNode> children;
	
	public FileNode( )
	{
		//setup default mode
		super();
		readAccess = true;
		writeAccess = true;
		viewAble = true;
		parent = null;
		file = true;
		
		setChildren( new Hashtable<String, FileNode>() );
	}


	public String getName( ) 
	{
		return name;
	}


	public void setName( String name ) 
	{
		this.name = name;
	}


	public String getPath( ) 
	{
		return path;
	}


	public void setPath( String folderPath ) 
	{
		this.path = folderPath;
	}


	public boolean isReadAccess( ) 
	{
		return readAccess;
	}


	public void setReadAccess( boolean readAccess ) 
	{
		this.readAccess = readAccess;
	}


	public boolean isWriteAccess( ) 
	{
		return writeAccess;
	}

	public void setWriteAccess( boolean writeAccess ) 
	{
		this.writeAccess = writeAccess;
	}


	public boolean isViewAble( ) 
	{
		return viewAble;
	}


	public void setViewAble( boolean viewAble ) 
	{
		this.viewAble = viewAble;
	}
	
	public FileNode getParent( ) 
	{
		return parent;
	}


	public void setParent( FileNode parent )
	{
		this.parent = parent;
	}
	

	/**
	 * @return the children
	 */
	public Hashtable<String, FileNode> getChildren( ) 
	{
		return children;
	}

	/**
	 * @param children the children to set
	 */
	public void setChildren( Hashtable<String, FileNode> children )
	{
		this.children = children;
	}
	
	public void setType( boolean type ) 
	{
		file = type;
	}
	
	public boolean isFile( )
	{
		return ( file );
	}
	
	public boolean isFolder( )
	{
		return ( !file );
	}
	
	public String read( String arg, Metadata metadata, String user )
	{
		String fileOutput = "";
		
		if( !readAccess )
		{
			fileOutput = "User doesn't have read access";
		}
		else
		{
			if( isFolder() )
				fileOutput = readFolder( metadata, user );
			else
				fileOutput = readFile( );
		}
		
		return fileOutput;
	}
	
	public String write( String value )
	{
		String fileOutput = "";
		BufferedWriter bw = null;
		
		System.out.println( value );
		
		if( isFolder() )
		{
			System.out.println( getPath() + File.separator + getName() );
			File folder = new File( getPath() + File.separator + getName() );
			
			System.out.println( folder.getParent() + File.separator + value );
			File newDir = new File( folder.getParent() + File.separator + value);
	
			if ( folder.renameTo( newDir ) );
				setName( value );
		}
		else
		{
			
			try
			{
				bw = new BufferedWriter( new FileWriter( getPath() + File.separator + getName() ) );
				bw.append( value );
				bw.close();
			} 
			catch (IOException e) 
			{
				System.out.println("Can't find file " + path + "/" + name );
				e.printStackTrace();
			}
		}
		
		return fileOutput;
	}
	
	public boolean isRoot( )
	{
		return ( parent == null );
	}
	
	private String readFolder( Metadata metadata, String user )
	{
		String listOfNodes = "\n";
		//ArrayList<String> listOfFiles = new ArrayList<String>(), listOfFolders = new ArrayList<String>();
		Enumeration<String> keys = getChildren().keys();

		//Iterate through the list of files and print them out
		System.out.printf("\n%-20s %-5s %-10s %11s\n", "File/Folder Name", "Type", "Owner", user + " Access");
		System.out.println("-----------------------------------------------------------");
		while( keys.hasMoreElements() )
		{
			String key = (String) keys.nextElement();
			FileNode childNode = getChildren().get( key );
			String folderFile, owner, access = "";
			MetaRule rule = metadata.getMetaRule( key );
			
			if( childNode.isFolder() )
				folderFile = "Dir";
			else
				folderFile = "File";
			
			if( rule == null )
			{
				owner = "None";
				
				if( childNode.isFolder() )
					access = "rw";
				else
					access = "--";
				
			}
			else
			{
				owner = rule.getOwner();
				AceRule aceRule = rule.getACERule( user );
				
				if( aceRule == null )
				{
					if( owner.equals( user ) )
						access = "rw";
					else if( childNode.isFolder() )
						access = "rw";
					else
						access = "--";
				}
				else
				{
					if( aceRule.isReadable() )
						access += "r";
					else
						access += "-";
					
					if( aceRule.isWritable() )
						access += "w";
					else
						access += "-";
				}
			}
			
			System.out.printf( "%-20s %-5s %-10s %6s\n", childNode.getName(), folderFile, owner, access );
		}
		
		return "\n";
	}
	
	private String readFile( )
	{
		String fileOutput = "";
		BufferedReader br = null;
		System.out.println( "\nReading Contents of file " + getName() );
		System.out.println( "-----------------------------------------------------------" );
		
		try 
		{
			String sCurrentLine;
 
			br = new BufferedReader( new FileReader( getPath() + File.separator + getName() ) );
 
			while ( ( sCurrentLine = br.readLine() ) != null ) 
				fileOutput = sCurrentLine + "\n";
			
			br.close();
 
		} 
		catch (IOException e) 
		{
			System.out.println("Unable to open file " + getPath() + File.separator + getName() );
			e.printStackTrace();
		}
		
		return fileOutput + "<end file>\n\n";
	}
}
