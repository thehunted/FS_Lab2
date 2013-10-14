package filesystem;

import java.util.Enumeration;
import java.util.Hashtable;

public class MetaRule 
{
	private String owner;
	private String fileNodeName;
	private Hashtable<String, AceRule> aceRules;
	private boolean folderOrFile;
	
	
	public MetaRule( String owner, String fileNodeName, Hashtable<String, AceRule> aceRules ) 
	{
		super( );
		this.owner = owner;
		this.fileNodeName = fileNodeName;
		this.aceRules = aceRules;
	}


	public String getOwner( ) 
	{
		return owner;
	}

	public void setfolderOrFile( boolean folderOrFile )
	{
		this.folderOrFile = folderOrFile;
	}
	
	public boolean isFolder( )
	{
		return folderOrFile;
	}
	
	public boolean isFile( )
	{
		return !folderOrFile;
	}
	
	public void setOwner( String owner ) 
	{
		this.owner = owner;
	}


	public String getName( ) 
	{
		return fileNodeName;
	}


	public void setName( String name ) 
	{
		this.fileNodeName = name;
	}
	
	public void addACERule( String key, AceRule value)
	{
		aceRules.put( key, value );
	}
	
	public AceRule getACERule( String key )
	{
		return aceRules.get( key );
	}
	
	public Enumeration<String> getAceRuleKeys( )
	{
		return aceRules.keys();
	}
	
	public boolean isOwner( String owner )
	{
		return this.owner.equals( owner );
	}
	

}
