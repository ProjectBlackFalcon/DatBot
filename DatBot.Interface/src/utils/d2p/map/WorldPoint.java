package utils.d2p.map;

public class WorldPoint {
	
    private long mapId;
    private long worldId;
    private int x;
    private int y;

    public WorldPoint(long mapId)
    {
        this.mapId = mapId;
        this.worldId = (mapId & 1073479680) >> 18;

        x = (int)(mapId >> 9 & 511);
        y = (int)(mapId & 511);

        if ((x & 256) == 256)
            x = -(x & 255);

        if ((y & 256) == 256)
            y = -(y & 255);
    }

	public long getMapId()
	{
		return mapId;
	}

	public void setMapId(long mapId)
	{
		this.mapId = mapId;
	}

	public long getWorldId()
	{
		return worldId;
	}

	public void setWorldId(long worldId)
	{
		this.worldId = worldId;
	}

	public int getX()
	{
		return x;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public int getY()
	{
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	@Override
	public String toString()
	{
		return "WorldPoint [mapId=" + mapId + ", worldId=" + worldId + ", x=" + x + ", y=" + y + "]";
	}
}
