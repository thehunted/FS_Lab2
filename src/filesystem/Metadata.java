package filesystem;

import java.util.Enumeration;
import java.util.Hashtable;

public class Metadata 
{

	private Hashtable< String, MetaRule> metaHash;
	private Hashtable< String, String > users;
	
	private final String DIR = "Directory:";
	private final String USERS = "Users:";
	private final String FILE = "File:";
	private final String OWNER = "Owner:";
	private final String ACE = "ACE:";
	
	private static final String READ 		= "read";
	private static final String WRITE 		= "write";
	private static final String ACCESS		= "access";
	
	public Metadata( )
	{
		metaHash = new Hashtable<String, MetaRule>();
		users = new Hashtable<String, String>();
	}
	
	public void addMetaRule( String key, MetaRule value )
	{
		metaHash.put(key, value);
	}
	
	public MetaRule getMetaRule( String key )
	{
		return metaHash.get( key );
	}
	
	public Enumeration<String> getMetaRuleKeys()
	{
		return metaHash.keys();
	}
	
	public void removeMetaRule( String key )
	{
		metaHash.remove( key );
	}
	
	public void addUsers( String key, String value )
	{
		users.put(key, value);
	}
	
	public String getUsersRule( String key )
	{
		return users.get( key );
	}
	
	public Enumeration<String> getUserRuleKeys( )
	{
		return users.keys();
	}
	
	public String toString( )
	{
		String metaDataValues = " List of Meta Rules\n";
		int i = 1;
		String type;
		
		metaDataValues += "---------------------------------------------------------\n ";
		metaDataValues += USERS + " ";
		
		//getting users
		Enumeration<String> keys = users.keys();
		while( keys.hasMoreElements() )
			metaDataValues += users.get( keys.nextElement() ) + " ";
		
		metaDataValues += "\n";
		
		//getting directory/file rules
		Enumeration<String> names = metaHash.keys();
		while( names.hasMoreElements() )
		{
			MetaRule rule = metaHash.get( names.nextElement() );
			
			if( rule.isFolder() )
				type = DIR;
			else
				type = FILE;
			
			metaDataValues += " " + i + ". " + type + " " + rule.getName() + " " + OWNER + " " + rule.getOwner() + " ";
			
			Enumeration<String> aceKeys = rule.getAceRuleKeys();
			while( aceKeys.hasMoreElements() )
			{
				AceRule ace = rule.getACERule( aceKeys.nextElement() );
				
				metaDataValues += ACE + " " + ace.getName() + " ";
				
				if( ace.isAllowed() )
					metaDataValues += "allow ";
				else
					metaDataValues += "deny ";
				
				if( ace.isReadable() )
					metaDataValues += "r";
				
				if( ace.isWritable() )
					metaDataValues += "w";
				metaDataValues += " ";
			}
			metaDataValues += "\n";
			i++;
		}
		
		return metaDataValues;
	}

	public boolean hasAccess( FileNode relativeNode, String accessType, String user ) 
	{
		MetaRule rule = metaHash.get( relativeNode.getName() );
		
		//This means there is rule specified and the default applies
		//Calling checkForAccess with null as the rule
		if( rule == null )
			return checkForAccess( null, accessType, relativeNode.isFolder() );
		
		//policy rule part 3
		//checks if user is the owner of the file
		//if so give rw and allowed unless specified by the ACE
		if( rule.isOwner( user ) )
		{
			//checking for overrides specified by the ACE
			if( rule.getACERule( user ) == null )
			{
				return true;
			}
		}
		//Check the ACE Rules for additional user level access
		AceRule aceRule = rule.getACERule( user );
				
		return checkForAccess( aceRule, accessType, rule.isFolder() );
	}
	
	private boolean checkForAccess( AceRule aceRule, String accessType, boolean folder )
	{
		/*//Policy Rule 4 allows users to "CD" into the file unless specified
		//Policy Rule 5 doesn't allow users to R or W unless specified
		if( aceRule != null )
		{
			if( accessType.equals( WRITE ) )
				return aceRule.isWritable();
			else if( accessType.equals( READ ) )
				return aceRule.isReadable();
			else 
				return aceRule.isAllowed();
		}
		else
		{
			//If there is no rule specified for the ACE user then:
			//Writable = false
			//Readable = false
			//Accessible = true
			if( accessType.equals( WRITE ) )
				return false;
			else if( accessType.equals( READ ) )
				return false;
			else 
				return true;
		}*/
		
		
		if( folder )
		{
			if( aceRule != null )
			{
				if( accessType.equals( WRITE ) )
					return aceRule.isWritable();
				else if( accessType.equals( READ ) )
					return aceRule.isReadable();
				else //allowed
					return true;
			}
			else
			{
				//If there is no rule specified for the ACE user then:
				//Writable = false
				//Readable = false
				//Accessible = true
				if( accessType.equals( WRITE ) )
					return true;
				else if( accessType.equals( READ ) )
					return true;
				else 
					return true;
			}
			
		}
		else //file
		{
			if( aceRule != null )
			{
				if( accessType.equals( WRITE ) )
					return aceRule.isWritable();
				else if( accessType.equals( READ ) )
					return aceRule.isReadable();
				else //allowed
					return true;
			}
			else
			{
				//If there is no rule specified for the ACE user then:
				//Writable = false
				//Readable = false
				//Accessible = true
				if( accessType.equals( WRITE ) )
					return false;
				else if( accessType.equals( READ ) )
					return false;
				else 
					return true;
			}
		}
		
	}

}
