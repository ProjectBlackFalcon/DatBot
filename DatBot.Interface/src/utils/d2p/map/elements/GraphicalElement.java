package utils.d2p.map.elements;

import java.io.IOException;

import protocol.network.util.DofusDataReader;

public class GraphicalElement extends BasicElement {
	
    private int Altitude;
    private int ElementId;
    private ColorMultiplicator FinalTeint;
    private ColorMultiplicator Hue;
    private int Identifier;
    private double OffsetX;
    private double OffsetY;
    private double PixelOffsetX;
    private double PixelOffsetY;
    private ColorMultiplicator Shadow;
	

	@Override
	public void Init(DofusDataReader Reader, int mapVersion)
	{
        try
		{
			ElementId = Reader.readInt();
	        Hue = new ColorMultiplicator(Reader.readByte(), Reader.readByte(), Reader.readByte());
	        Shadow = new ColorMultiplicator(Reader.readByte(), Reader.readByte(), Reader.readByte());
	        if (mapVersion <= 4)
	        {
	            OffsetX = Reader.readByte();
	            OffsetY = Reader.readByte();
	            PixelOffsetX = OffsetX * 43;
	            PixelOffsetY = OffsetY * 21.5;
	        }
	        else
	        {
	            PixelOffsetX = Reader.readShort();
	            PixelOffsetY = Reader.readShort();
	            OffsetX = PixelOffsetX / 43;
	            OffsetY = PixelOffsetY / 21.5;
	        }
	        Altitude = Reader.readByte();
	        Identifier = Reader.readInt();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public int getAltitude()
	{
		return Altitude;
	}


	public void setAltitude(int altitude)
	{
		Altitude = altitude;
	}


	public int getElementId()
	{
		return ElementId;
	}


	public void setElementId(int elementId)
	{
		ElementId = elementId;
	}


	public ColorMultiplicator getFinalTeint()
	{
		return FinalTeint;
	}


	public void setFinalTeint(ColorMultiplicator finalTeint)
	{
		FinalTeint = finalTeint;
	}


	public ColorMultiplicator getHue()
	{
		return Hue;
	}


	public void setHue(ColorMultiplicator hue)
	{
		Hue = hue;
	}


	public int getIdentifier()
	{
		return Identifier;
	}


	public void setIdentifier(int identifier)
	{
		Identifier = identifier;
	}


	public double getOffsetX()
	{
		return OffsetX;
	}


	public void setOffsetX(double offsetX)
	{
		OffsetX = offsetX;
	}


	public double getOffsetY()
	{
		return OffsetY;
	}


	public void setOffsetY(double offsetY)
	{
		OffsetY = offsetY;
	}


	public double getPixelOffsetX()
	{
		return PixelOffsetX;
	}


	public void setPixelOffsetX(double pixelOffsetX)
	{
		PixelOffsetX = pixelOffsetX;
	}


	public double getPixelOffsetY()
	{
		return PixelOffsetY;
	}


	public void setPixelOffsetY(double pixelOffsetY)
	{
		PixelOffsetY = pixelOffsetY;
	}


	public ColorMultiplicator getShadow()
	{
		return Shadow;
	}


	public void setShadow(ColorMultiplicator shadow)
	{
		Shadow = shadow;
	}

	@Override
	public String toString()
	{
		return "GraphicalElement [Altitude=" + Altitude + ", ElementId=" + ElementId + ", FinalTeint=" + FinalTeint + ", Hue=" + Hue + ", Identifier=" + Identifier + ", OffsetX=" + OffsetX + ", OffsetY=" + OffsetY + ", PixelOffsetX=" + PixelOffsetX + ", PixelOffsetY=" + PixelOffsetY + ", Shadow=" + Shadow + "]";
	}

}
