package filesystem;

public class AceRule 
{
	private String name;
	private boolean allowDeny;
	private boolean read;
	private boolean write;
	
	private final String ALLOW = "allow";
	private final String READABLE = "r";
	private final String WRITABLE = "w";
	
	public AceRule( String name, String allowDeny, String readWrite )
	{
		super( );
		this.name = name;
		
		if( allowDeny.equals( ALLOW ) )
			this.allowDeny = true;
		else
			this.allowDeny = false;
		
		if( readWrite.contains( READABLE ) )
			read = true;
		else
			read = false;
		
		if( readWrite.contains( WRITABLE ) )
			write = true;
		else
			write = false;
	}


	public String getName( ) 
	{
		return name;
	}


	public void setName( String name ) 
	{
		this.name = name;
	}


	public boolean isAllowed( ) 
	{
		return allowDeny;
	}


	public void setAllowDeny( boolean allowDeny ) 
	{
		this.allowDeny = allowDeny;
	}


	public boolean isReadable( ) 
	{
		return read;
	}


	public void setRead( boolean read ) 
	{
		this.read = read;
	}


	public boolean isWritable( ) 
	{
		return write;
	}


	public void setWritable( boolean write ) 
	{
		this.write = write;
	}

}
