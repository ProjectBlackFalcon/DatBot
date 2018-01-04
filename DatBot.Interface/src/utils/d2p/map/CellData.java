package utils.d2p.map;

import java.io.IOException;

import protocol.network.util.DofusDataReader;

public class CellData {
	
    private int Arrow;
    private boolean Blue;
    private boolean FarmCell;
    private int Floor;
    private boolean HavenbagCell;
    private boolean Los;
    private int Losmov = 3;
    private int MapChangeData;
    private boolean Mov;
    private long MoveZone;
    private boolean NonWalkableDuringFight;
    private boolean NonWalkableDuringRP;
    private boolean Red;
    private int Speed;
    private boolean Visible;
    private int linkedZone;
    
    public void init(DofusDataReader Reader, int MapVersion) throws IOException{
    	Floor = Reader.readByte() * 10;
        if (Floor != -1280)
        {
            boolean topArrow, bottomArrow, rightArrow, leftArrow = false;

            if (MapVersion >= 9)
            {
                int temp = Reader.readShort();
                Mov = (temp & 1) == 0;
                NonWalkableDuringFight = (temp & 2) != 0;
                NonWalkableDuringRP = (temp & 4) != 0;
                Los = (temp & 8) == 0;
                Blue = (temp & 16) != 0;
                Red = (temp & 32) != 0;
                Visible = (temp & 64) != 0;
                FarmCell = (temp & 128) != 0;
                if (MapVersion >= 10)
                {
                    HavenbagCell = (temp & 256) != 0;
                    topArrow = (temp & 512) != 0;
                    bottomArrow = (temp & 1024) != 0;
                    rightArrow = (temp & 2048) != 0;
                    leftArrow = (temp & 4096) != 0;
                }
                else
                {
                    topArrow = (temp & 256) != 0;
                    bottomArrow = (temp & 512) != 0;
                    rightArrow = (temp & 1024) != 0;
                    leftArrow = (temp & 2048) != 0;
                }
            }
            else
            {
                Losmov = Reader.readUnsignedByte();
                Los = (Losmov & 2) >> 1 == 1;
                Mov = (Losmov & 1) == 1;
                Visible = (Losmov & 64) >> 6 == 1;
                FarmCell = (Losmov & 32) >> 5 == 1;
                Blue = (Losmov & 16) >> 4 == 1;
                Red = (Losmov & 8) >> 3 == 1;
                NonWalkableDuringRP = (Losmov & 128) >> 7 == 1;
                NonWalkableDuringFight = (Losmov & 4) >> 2 == 1;
            }

            Speed = Reader.readByte();
            MapChangeData = Reader.readByte();

            if (MapVersion > 5)
                MoveZone = Reader.readUnsignedByte();
            if (MapVersion > 10 && (this.hasLinkedZoneFight() || this.hasLinkedZoneRP()))
            {
                linkedZone = Reader.readUnsignedByte();
            }
            if (MapVersion > 7 && MapVersion < 9)
                Arrow = 15 & Reader.readByte();
        }
    }
    
    public boolean hasLinkedZoneRP()
    {
        return this.Mov && !this.FarmCell;
    }

    public boolean hasLinkedZoneFight()
    {
        return this.Mov && !this.NonWalkableDuringFight && !this.FarmCell && this.HavenbagCell;
    }
    
	public int getArrow()
	{
		return Arrow;
	}
	public void setArrow(int arrow)
	{
		Arrow = arrow;
	}
	public boolean isBlue()
	{
		return Blue;
	}
	public void setBlue(boolean blue)
	{
		Blue = blue;
	}
	public boolean isFarmCell()
	{
		return FarmCell;
	}
	public void setFarmCell(boolean farmCell)
	{
		FarmCell = farmCell;
	}
	public int getFloor()
	{
		return Floor;
	}
	public void setFloor(int floor)
	{
		Floor = floor;
	}
	public boolean isHavenbagCell()
	{
		return HavenbagCell;
	}
	public void setHavenbagCell(boolean havenbagCell)
	{
		HavenbagCell = havenbagCell;
	}
	public boolean isLos()
	{
		return Los;
	}
	public void setLos(boolean los)
	{
		Los = los;
	}
	public int getLosmov()
	{
		return Losmov;
	}
	public void setLosmov(int losmov)
	{
		Losmov = losmov;
	}
	public int getMapChangeData()
	{
		return MapChangeData;
	}
	public void setMapChangeData(int mapChangeData)
	{
		MapChangeData = mapChangeData;
	}
	public boolean isMov()
	{
		return Mov;
	}
	public void setMov(boolean mov)
	{
		Mov = mov;
	}
	public long getMoveZone()
	{
		return MoveZone;
	}
	public void setMoveZone(long moveZone)
	{
		MoveZone = moveZone;
	}
	public boolean isNonWalkableDuringFight()
	{
		return NonWalkableDuringFight;
	}
	public void setNonWalkableDuringFight(boolean nonWalkableDuringFight)
	{
		NonWalkableDuringFight = nonWalkableDuringFight;
	}
	public boolean isNonWalkableDuringRP()
	{
		return NonWalkableDuringRP;
	}
	public void setNonWalkableDuringRP(boolean nonWalkableDuringRP)
	{
		NonWalkableDuringRP = nonWalkableDuringRP;
	}
	public boolean isRed()
	{
		return Red;
	}
	public void setRed(boolean red)
	{
		Red = red;
	}
	public int getSpeed()
	{
		return Speed;
	}
	public void setSpeed(int speed)
	{
		Speed = speed;
	}
	public boolean isVisible()
	{
		return Visible;
	}
	public void setVisible(boolean visible)
	{
		Visible = visible;
	}

	@Override
	public String toString()
	{
		return "CellData [Arrow=" + Arrow + ", Blue=" + Blue + ", FarmCell=" + FarmCell + ", Floor=" + Floor + ", HavenbagCell=" + HavenbagCell + ", Los=" + Los + ", Losmov=" + Losmov + ", MapChangeData=" + MapChangeData + ", Mov=" + Mov + ", MoveZone=" + MoveZone + ", NonWalkableDuringFight=" + NonWalkableDuringFight + ", NonWalkableDuringRP="
			+ NonWalkableDuringRP + ", Red=" + Red + ", Speed=" + Speed + ", Visible=" + Visible + ", linkedZone=" + linkedZone + "]";
	}

}
