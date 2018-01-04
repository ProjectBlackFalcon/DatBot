package utils.d2p.map.elements;

public class ColorMultiplicator {
	
    private double Blue;
    private double Green;
    private double Red;

    public ColorMultiplicator(int red, int green, int blue)
    {
        Red = red;
        Green = green;
        Blue = blue;
    }

	public double getBlue()
	{
		return Blue;
	}

	public void setBlue(double blue)
	{
		Blue = blue;
	}

	public double getGreen()
	{
		return Green;
	}

	public void setGreen(double green)
	{
		Green = green;
	}

	public double getRed()
	{
		return Red;
	}

	public void setRed(double red)
	{
		Red = red;
	}

	@Override
	public String toString()
	{
		return "ColorMultiplicator [Blue=" + Blue + ", Green=" + Green + ", Red=" + Red + "]";
	}

}
