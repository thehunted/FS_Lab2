package filesystem;

import java.util.Hashtable;

public class Metadata 
{

	private Hashtable< String, MetaRule> metaHash;
	private Hashtable< String, String > users;
	
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
	
	public void addUsers( String key, String value )
	{
		users.put(key, value);
	}
	
	public String getUsersRule( String key )
	{
		return users.get( key );
	}
	

}
