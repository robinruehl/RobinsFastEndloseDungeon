package application;

public class CS {
	private String name;
	private double height;
	
	CS()
	{
		
	}
	
	CS(String name, double height)
	{
		this.name = name;
		this.height = height;
	}
	
	public boolean testName(String name)
	{
		if (name.length() < 5||name.length()>16)
		{
			return false;
		}
		return true;
	}
	
	public boolean testHeight(double height)
	{
		if(height<1||height>3) {
			return false;
		}
		return true;
	}
	
	public void testCS(String name, double height)
	{
		if(testHeight(height))
		{
			
		}
		if(testName(name))
		{
			
		}
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	
}
