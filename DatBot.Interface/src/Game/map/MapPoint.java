package Game.map;

import java.awt.Point;
import java.util.ArrayList;

public class MapPoint {
    public int CellId;
    public int X;
    public int Y;
    private static boolean _bInit;
    
    public static final int MAP_WIDTH = 14;
    public static final int MAP_HEIGHT = 20;
	private static final ArrayList<Point>  CELLPOS = new ArrayList<Point>();

    
    
    public MapPoint(int CellId)
    {
    	this.CellId = CellId;
    	this.setFromCellId();
    }
    
    public MapPoint(int x, int y){
    	this.X = x;
    	this.Y = y;
    	setFromCoords();
    }
    
    private void setFromCoords() {
        if(!_bInit)
        {
           init();
        }
        this.CellId = (this.X - this.Y) * MAP_WIDTH + this.Y + (this.X - this.Y) / 2;
	}

	private void setFromCellId() {
        if(!_bInit)
        {
           init();
        }
        Point _loc1_ = CELLPOS.get(this.CellId);
        this.X = _loc1_.x;
        this.Y = _loc1_.y;		
	}

	private static void init()
    {
    	int _loc4_ = 0;
    	_bInit = true;
    	int _loc1_ = 0;
    	int _loc2_ = 0;
    	int _loc3_ = 0;
    	int _loc5_ = 0;
        while(_loc5_ < MAP_HEIGHT)
        {
           _loc4_ = 0;
           while(_loc4_ < MAP_WIDTH)
           {
        	  CELLPOS.add(_loc3_, new Point(_loc1_ + _loc4_,_loc2_ + _loc4_));
              _loc3_++;
              _loc4_++;
           }
           _loc1_++;
           _loc4_ = 0;
           while(_loc4_ < MAP_WIDTH)
           {
         	  CELLPOS.add(_loc3_, new Point(_loc1_ + _loc4_,_loc2_ + _loc4_));
              _loc3_++;
              _loc4_++;
           }
           _loc2_--;
           _loc5_++;
        }
    }
	
    public int OrientationTo(MapPoint Point)
    {
        int num = Point.X > X ? 1 : Point.X < X ? -1 : 0;
        int num2 = Point.Y > Y ? 1 : Point.Y < Y ? -1 : 0;
        if (num == 1 && num2 == 1)
            return 0;
        if (num == 1 && num2 == 0)
            return 1;
        if (num == 1 && num2 == -1)
            return 2;
        if (num == 0 && num2 == -1)
            return 3;
        if (num == -1 && num2 == -1)
            return 4;
        if (num == -1 && num2 == 0)
            return 5;
        if (num == -1 && num2 == 1)
            return 6;
        if (num == 0 && num2 == 1)
            return 7;
        return -1;
    }

	public int DistanceToCell(MapPoint endPoint) {
		return Math.abs(this.X - endPoint.X) + Math.abs(this.Y - endPoint.Y);
	}

	public boolean IsInMap() {
        return X + Y >= 0 && X - Y >= 0 && X - Y < 40 && X + Y < 0x1c;
	}
	
	
}
