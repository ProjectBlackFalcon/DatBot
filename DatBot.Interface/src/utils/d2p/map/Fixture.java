package utils.d2p.map;

import java.io.IOException;

import protocol.network.util.DofusDataReader;

public class Fixture {
	private int fixtureId;
    private int offsetX;
    private int offsetY;
    private int rotation;
    private int xScale;
    private int yScale;
    private int redMultiplier;
    private int greenMultiplier;
    private int blueMultiplier;
    private int hue;
    private long alpha;
    
    public void init(DofusDataReader reader) throws IOException
    {
       this.fixtureId = reader.readInt();
       this.offsetX = reader.readShort();
       this.offsetY = reader.readShort();
       this.rotation = reader.readShort();
       this.xScale = reader.readShort();
       this.yScale = reader.readShort();
       this.redMultiplier = reader.readByte();
       this.greenMultiplier = reader.readByte();
       this.blueMultiplier = reader.readByte();
       this.hue = redMultiplier |this.greenMultiplier |this.blueMultiplier;
       this.alpha = reader.readUnsignedByte();
    }
    
	public int getFixtureId()
	{
		return fixtureId;
	}
	public void setFixtureId(int fixtureId)
	{
		this.fixtureId = fixtureId;
	}
	public int getOffsetX()
	{
		return offsetX;
	}
	public void setOffsetX(int offsetX)
	{
		this.offsetX = offsetX;
	}
	public int getOffsetY()
	{
		return offsetY;
	}
	public void setOffsetY(int offsetY)
	{
		this.offsetY = offsetY;
	}
	public int getRotation()
	{
		return rotation;
	}
	public void setRotation(int rotation)
	{
		this.rotation = rotation;
	}
	public int getxScale()
	{
		return xScale;
	}
	public void setxScale(int xScale)
	{
		this.xScale = xScale;
	}
	public int getyScale()
	{
		return yScale;
	}
	public void setyScale(int yScale)
	{
		this.yScale = yScale;
	}
	public int getRedMultiplier()
	{
		return redMultiplier;
	}
	public void setRedMultiplier(int redMultiplier)
	{
		this.redMultiplier = redMultiplier;
	}
	public int getGreenMultiplier()
	{
		return greenMultiplier;
	}
	public void setGreenMultiplier(int greenMultiplier)
	{
		this.greenMultiplier = greenMultiplier;
	}
	public int getBlueMultiplier()
	{
		return blueMultiplier;
	}
	public void setBlueMultiplier(int blueMultiplier)
	{
		this.blueMultiplier = blueMultiplier;
	}
	public int getHue()
	{
		return hue;
	}
	public void setHue(int hue)
	{
		this.hue = hue;
	}
	public long getAlpha()
	{
		return alpha;
	}
	public void setAlpha(long alpha)
	{
		this.alpha = alpha;
	}

	@Override
	public String toString()
	{
		return "Fixture [fixtureId=" + fixtureId + ", offsetX=" + offsetX + ", offsetY=" + offsetY + ", rotation=" + rotation + ", xScale=" + xScale + ", yScale=" + yScale + ", redMultiplier=" + redMultiplier + ", greenMultiplier=" + greenMultiplier + ", blueMultiplier=" + blueMultiplier + ", hue=" + hue + ", alpha=" + alpha + "]";
	}

}
