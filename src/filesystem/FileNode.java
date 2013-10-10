package filesystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileNode 
{
	private String name;
	private String path;
	private boolean readAccess;
	private boolean writeAccess;
	private boolean viewAble;
	private boolean file; //true is a file, false is a folder
	private FileNode parent;
	private ArrayList<FileNode> children;
	
	public FileNode( )
	{
		//setup default mode
		super();
		readAccess = true;
		writeAccess = true;
		viewAble = true;
		parent = null;
		setChildren(new ArrayList<FileNode>());
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
	public ArrayList<FileNode> getChildren( ) 
	{
		return children;
	}

	/**
	 * @param children the children to set
	 */
	public void setChildren( ArrayList<FileNode> children )
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
	
	public String read( )
	{
		String fileOutput = "";
		BufferedReader br = null;
		
		if( !readAccess )
		{
			fileOutput = "User doesn't have read access";
		}
		else
		{
			try 
			{
				String sCurrentLine;
	 
				br = new BufferedReader( new FileReader( path + name ) );
	 
				while ( ( sCurrentLine = br.readLine() ) != null ) 
					fileOutput = sCurrentLine;
				
				br.close();
	 
			} 
			catch (IOException e) 
			{
				System.out.println("Unable to open file " + path + "/" + name );
				//e.printStackTrace();
			}
		}
		
		return fileOutput;
	}
	
	public String write( String value)
	{
		String fileOutput = "";
		BufferedWriter bw = null;
		
		if( !writeAccess )
		{
			fileOutput = "User doesn't have read access";
		}
		else
		{
			try 
			{
				bw = new BufferedWriter( new FileWriter( path + name ) );
				bw.append( value );
				bw.close();
			} 
			catch (IOException e) 
			{
				System.out.println("Can't find file " + path + "/" + name );
				//e.printStackTrace();
			}
		}
		
		return fileOutput;
	}
	
	public boolean isRoot( )
	{
		return ( parent == null );
	}
	
	public boolean isAFolder( )
	{
		return false;
	}
	
}
