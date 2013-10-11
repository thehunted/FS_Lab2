package filesystem;

import java.io.File;
import java.util.Hashtable;

public class ParseFileSystem
{

	/**
	 * This method starts by parsing the root folder passed in then starts to 
	 * parse its child files.
	 * 
	 * @param rootFolder
	 * @return
	 */
	public FileNode parseAtRoot( String rootFolder )
	{
		File fileDirectory = new File( rootFolder );
		FileNode root = new FileNode();
		
		if( fileDirectory != null )
		{
			root = populateNode( fileDirectory, null );
			parseTree( fileDirectory, root, null );
		}

		return root;
	}
	
	/**
	 * A recursive function that parses all files and folders
	 * @param fileDirectory
	 * @param currentNode
	 * @param parent
	 */
	private void parseTree( File fileDirectory, FileNode currentNode, FileNode parent )
	{
		File[] list;
		Hashtable<String, FileNode> children = new Hashtable<String, FileNode>();
		list = fileDirectory.listFiles(); //Gets a list of files and folders
		
		for(int i = 0; i < list.length; i++)
		{
			FileNode node = populateNode( list[i], currentNode );//creates a new node
			
			if( list[i].isDirectory() )//If its a folder parses it's children DFS
				parseTree( list[i], node, currentNode );
			
			children.put( node.getName(), node );//Add this node to the list of children
		}
		
		//Make sure we add the children to our list
		currentNode.setChildren( children );
	}
	
	private FileNode populateNode( File file, FileNode parent )
	{
		FileNode node = new FileNode( );
		
		//Populate the node with all the necessary data
		//TODO: Still need to populate meta information
		//TODO: Need to add User information
		node.setName( file.getName() );
		node.setPath( file.getParent() );
		node.setParent( parent );
		node.setReadAccess( true );
		node.setViewAble( true );
		node.setWriteAccess( true );
		
		if( file.isDirectory() )
		{
			node.setChildren( new Hashtable<String, FileNode>() );
			node.setType( false );
		}
		else
		{
			node.setType( true );
		}

		return node;
	}

}
