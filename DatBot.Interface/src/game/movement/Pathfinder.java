package game.movement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import game.map.MapPoint;
import protocol.network.Network;
import utils.d2p.map.CellData;

public class Pathfinder {
	
	private Network network;

	private int DCost = 15;
	private int HeuristicCost = 10;
	private int HVCost = 10;

	// Fields
	private boolean IsFighting = false;
	private List<CellInfo> MapStatus = new ArrayList<CellInfo>();
	private int MaxX = 34;
	private int MaxY = 14;

	private int MinX = 0;
	private int MinY = -19;
	private boolean AllowDiag;
	private boolean AllowDiagCornering;
	private boolean AllowTroughEntity = true;
	private MapPoint AuxEndPoint;
	private int AuxEndX;
	private int AuxEndY;

	private int DistanceToEnd;
	private MapPoint EndPoint;
	private int EndX;
	private int EndY;
	private MovementPath MovPath;
	private int NowX;
	private int NowY;
	private List<OpenSquare> OpenList = new ArrayList<OpenSquare>();
	private int PreviousCellId;

	private MapPoint StartPoint;

	private int StartX;
	private int StartY;

	public Pathfinder(Network network)
	{
		this.network = network;
	}

	public MovementPath findPath(int FromCell, int ToCell, boolean ate) {
		return findPath(FromCell, ToCell, ate, true);
	}

	public MovementPath findPath(int FromCell, int ToCell) {
		return findPath(FromCell, ToCell, true, true);
	}

	// Bool : ate => Throug entity
	public MovementPath findPath(int FromCell, int ToCell, boolean ate, boolean adc) {
		MovPath = new MovementPath();
		MovPath.CellStart = new MapPoint(FromCell);
		MovPath.CellEnd = new MapPoint(ToCell);
		AllowDiag = adc;
		AllowDiagCornering = adc;
		AllowTroughEntity = ate;

		StartPathfinding(new MapPoint(FromCell), new MapPoint(ToCell));
		ProcessPathfinding();

		return MovPath;
	}

	public void StartPathfinding(MapPoint startP, MapPoint endP) {
		StartX = startP.X;
		StartY = startP.Y;
		EndX = endP.X;
		EndY = endP.Y;

		StartPoint = new MapPoint(startP.X, startP.Y);
		EndPoint = new MapPoint(endP.X, endP.Y);

		AuxEndPoint = StartPoint;
		AuxEndX = StartPoint.X;
		AuxEndY = StartPoint.Y;

		DistanceToEnd = StartPoint.DistanceToCell(EndPoint);

		for (int y = -19; y <= MaxY; y++)
			for (int x = 0; x <= MaxX; x++)
				MapStatus.add(new CellInfo(0, null, false, false, x, y));
		OpenList = new ArrayList<OpenSquare>();
		OpenSquare(StartY, StartX, null, 0, 0, false);
	}

	public void ProcessPathfinding()
    {
        int actualX = 0;
        int actualY = 0;
        int speed = 0;
        int moveCost = 0;

        boolean isDownRightEnd = false;
        boolean isDownRightStart = false;
        boolean isTopRightEnd = false;
        boolean isTopRightStart = false;

        MapPoint actualPoint = null;
        int actualDistanceToEnd = 0;
        double heuristic = 0;
        int square = 0;

        if (OpenList.size() > 0 && !IsClosed(EndY, EndX))
        {
            square = NearerSquare();
            NowY = OpenList.get(square).Y;
            NowX = OpenList.get(square).X;
            PreviousCellId = new MapPoint(NowX, NowY).CellId;
            CloseSquare(NowY, NowX);

            for (actualY = NowY - 1; actualY <= NowY + 1; actualY++)
            for (actualX = NowX - 1; actualX <= NowX + 1; actualX++)
                if (new MapPoint(actualX, actualY).IsInMap())
                    if (actualY >= MinY && actualY < MaxY && actualX >= MinX && actualX < MaxX &&
                        !(actualY == NowY && actualX == NowX) &&
                        (AllowDiag || actualY == NowY || actualX == NowX &&
                         (AllowDiagCornering || actualY == NowY || actualX == NowX ||
                          PointMov(NowX, NowY, PreviousCellId, AllowTroughEntity) ||
                          PointMov(actualX, NowY, PreviousCellId, AllowTroughEntity))))
                        if (!(!PointMov(NowX, actualY, PreviousCellId, AllowTroughEntity) &&
                              !PointMov(actualX, NowY, PreviousCellId, AllowTroughEntity) && !IsFighting &&
                              AllowDiag))
                            if (PointMov(actualX, actualY, PreviousCellId, AllowTroughEntity))
                                if (!IsClosed(actualY, actualX))
                                {
                                    if (actualX == EndX && actualY == EndY)
                                        speed = 1;
                                    else
                                        speed = (int) GetCellSpeed(new MapPoint(actualX, actualY).CellId,
                                            AllowTroughEntity);

                                    moveCost = GetCellInfo(NowY, NowX).MovementCost +
                                               (actualY == NowY || actualX == NowX ? HVCost : DCost) * speed;

                                    if (AllowTroughEntity)
                                    {
                                        isDownRightEnd = actualX + actualY == EndX + EndY;
                                        isDownRightStart = actualX + actualY == StartX + StartY;
                                        isTopRightEnd = actualX - actualY == EndX - EndY;
                                        isTopRightStart = actualX - actualY == StartX - StartY;
                                        actualPoint = new MapPoint(actualX, actualY);

                                        if (!isDownRightEnd && !isTopRightEnd ||
                                            !isDownRightStart && !isTopRightStart)
                                        {
                                            moveCost = moveCost + actualPoint.DistanceToCell(EndPoint);
                                            moveCost = moveCost + actualPoint.DistanceToCell(StartPoint);
                                        }

                                        if (actualX == EndX || actualY == EndY)
                                            moveCost = moveCost - 3;
                                        if (isDownRightEnd || isTopRightEnd || actualX + actualY == NowX + NowY ||
                                            actualX - actualY == NowX - NowY)
                                            moveCost = moveCost - 2;
                                        if (actualX == StartX || actualY == StartY)
                                            moveCost = moveCost - 3;
                                        if (isDownRightStart || isTopRightStart)
                                            moveCost = moveCost - 2;

                                        actualDistanceToEnd = actualPoint.DistanceToCell(EndPoint);
                                        if (actualDistanceToEnd < DistanceToEnd)
                                            if (actualX == EndX || actualY == EndY ||
                                                actualX + actualY == EndX + EndY ||
                                                actualX - actualY == EndX - EndY)
                                            {
                                                AuxEndPoint = actualPoint;
                                                AuxEndX = actualX;
                                                AuxEndY = actualY;
                                                DistanceToEnd = actualDistanceToEnd;
                                            }
                                    }

                                    if (IsOpened(actualY, actualX))
                                    {
                                        if (moveCost < GetCellInfo(actualY, actualX).MovementCost)
                                            OpenSquare(actualY, actualX, new int [] {NowY, NowX}, moveCost, 0, true);
                                    }
                                    else
                                    {
                                        heuristic = (double) (HeuristicCost) *
                                                    Math.sqrt((EndY - actualY) * (EndY - actualY) +
                                                              (EndX - actualX) * (EndX - actualX));
                                        OpenSquare(actualY, actualX, new int [] {NowY, NowX}, moveCost, heuristic,false);
                                    }
                                }
            ProcessPathfinding();
        }
        else
        {
            EndPathfinding();
        }
    }
	
    public void EndPathfinding()
    {
    	List<MapPoint> mapsArray = new ArrayList<MapPoint>();
        int parentY = 0;
        int parentX = 0;
        MapPoint btwPoint = null;
        List<MapPoint> tempArray = new ArrayList<MapPoint>();
        int i = 0;
        int actualX = 0;
        int actualY = 0;
        int thirdX = 0;
        int thirdY = 0;
        int btwX = 0;
        int btwY = 0;
        boolean endPointClosed = IsClosed(EndY, EndX);

        if (!endPointClosed)
        {
            EndPoint = AuxEndPoint;
            EndX = AuxEndX;
            EndY = AuxEndY;
            endPointClosed = true;
            MovPath.CellEnd = EndPoint;
        }
        PreviousCellId = -1;
        if (endPointClosed)
        {
            NowX = EndX;
            NowY = EndY;

            while (!(NowX == StartX) || !(NowY == StartY))
            {
                mapsArray.add(new MapPoint(NowX, NowY));
                parentY = GetCellInfo(NowY, NowX).Parent[0];
                parentX = GetCellInfo(NowY, NowX).Parent[1];
                NowX = parentX;
                NowY = parentY;
            }
            mapsArray.add(StartPoint);
            if (AllowDiag)
            {
                for (i = 0; i < mapsArray.size(); i++)
                {
                    tempArray.add(mapsArray.get(i));
                    PreviousCellId = mapsArray.get(i).CellId;
                    if (mapsArray.size() > i + 2 && !(mapsArray.get(i+2) == null) &&
                        mapsArray.get(i).DistanceToCell(mapsArray.get(i + 2)) == 1 &&
                        !IsChangeZone(mapsArray.get(i).CellId, mapsArray.get(i+1).CellId) &&
                        !IsChangeZone(mapsArray.get(i+1).CellId, mapsArray.get(i+2).CellId))
                    {
                        i += 1;
                    }
                    else
                    {
                        if (mapsArray.size() > i + 3 && !(mapsArray.get(i+3) == null) &&
                            mapsArray.get(i).DistanceToCell(mapsArray.get(i+3)) == 2)
                        {
                            actualX = mapsArray.get(i).X;
                            actualY = mapsArray.get(i).Y;
                            thirdX = mapsArray.get(i+3).X;
                            thirdY = mapsArray.get(i+3).Y;
                            btwX = actualX + (int) Math.round((thirdX - actualX) / 2.0);
                            btwY = actualY + (int) Math.round((thirdY - actualY) / 2.0);
                            btwPoint = new MapPoint(btwX, btwY);
                            if (PointMov(btwX, btwY, PreviousCellId, true) &&
                                GetCellSpeed(btwPoint.CellId, AllowTroughEntity) < 2)
                            {
                                tempArray.add(btwPoint);
                                PreviousCellId = btwPoint.CellId;
                                i += 2;
                            }
                        }
                        else
                        {
                            if (mapsArray.size() > i + 2 && !(mapsArray.get(i + 2) == null) &&
                                mapsArray.get(i).DistanceToCell(mapsArray.get(i)) == 2)
                            {
                                actualX = mapsArray.get(i).X;
                                actualY = mapsArray.get(i).Y;
                                thirdX = mapsArray.get(i + 2).X;
                                thirdY = mapsArray.get(i + 2).Y;
                                btwX = mapsArray.get(i + 1).X;
                                btwY = mapsArray.get(i + 2).Y;

                                if (actualX + actualY == thirdX + thirdY && !(actualX - actualY == btwX - btwY) &&
                                    !IsChangeZone(new MapPoint(actualX, actualY).CellId,
                                        new MapPoint(btwX, btwY).CellId) &&
                                    !IsChangeZone(new MapPoint(btwX, btwY).CellId,
                                        new MapPoint(thirdX, thirdY).CellId))
                                {
                                    i += 1;
                                }
                                else
                                {
                                    if (actualX - actualY == thirdX - thirdY &&
                                        !(actualX - actualY == btwX - btwY) &&
                                        !IsChangeZone(new MapPoint(actualX, actualY).CellId,
                                            new MapPoint(btwX, btwY).CellId) &&
                                        !IsChangeZone(new MapPoint(btwX, btwY).CellId,
                                            new MapPoint(thirdX, thirdY).CellId))
                                    {
                                        i += 1;
                                    }
                                    else
                                    {
                                        if (actualX == thirdX && !(actualX == btwX) &&
                                            GetCellSpeed(new MapPoint(actualX, btwY).CellId, AllowTroughEntity) <
                                            2 && PointMov(actualX, btwY, PreviousCellId, AllowTroughEntity))
                                        {
                                            btwPoint = new MapPoint(actualX, btwY);
                                            tempArray.add(btwPoint);
                                            PreviousCellId = btwPoint.CellId;
                                            i += 1;
                                        }
                                        else
                                        {
                                            if (actualY == thirdY && !(actualY == btwY) &&
                                                GetCellSpeed(new MapPoint(btwX, actualY).CellId,
                                                    AllowTroughEntity) < 2 &&
                                                PointMov(btwX, actualY, PreviousCellId, AllowTroughEntity))
                                            {
                                                btwPoint = new MapPoint(btwX, actualY);
                                                tempArray.add(btwPoint);
                                                PreviousCellId = btwPoint.CellId;
                                                i += 1;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                mapsArray = tempArray;
            }
            if (mapsArray.size() == 1)
                mapsArray = new ArrayList<MapPoint>();
            Collections.reverse(mapsArray);
            MovementPathFromArray(mapsArray.toArray(new MapPoint [mapsArray.size()]));
        }
    }

	public boolean PointMov(int x, int y, int cellId, boolean troughtEntities) {
		boolean isNewSystem = this.network.getMap().isIsUsingNewMovementSystem();
		MapPoint actualPoint = new MapPoint(x, y);
		CellData fCellData = null;
		CellData sCellData = null;
		boolean mov = false;
		int floor = 0;

		if (actualPoint.IsInMap()) {
			fCellData = this.network.getMap().getCells().get(actualPoint.CellId);
			mov = fCellData.isMov() && (!IsFighting || !fCellData.isNonWalkableDuringFight());

			if (!(mov == false) && isNewSystem && cellId != -1 && cellId != actualPoint.CellId) {
				sCellData = this.network.getMap().getCells().get(cellId);
				floor = (int) Math.abs(Math.abs(fCellData.getFloor()) - Math.abs(sCellData.getFloor()));
				if (!(sCellData.getMoveZone() == fCellData.getMoveZone()) && floor > 0 || sCellData.getMoveZone() == fCellData.getMoveZone()
						&& fCellData.getMoveZone() == 0 && floor > 11)
					mov = false;
			}
		} else {
			mov = false;
		}
		return mov;
	}
	
    public boolean IsOpened(int y, int x)
    {
        return GetCellInfo(y, x).Opened;
    }
	
    private double GetCellSpeed(int cellId, boolean throughEntities)
    {
        int speed = (int) this.network.getMap().getCells().get(cellId).getSpeed();
        MapPoint cell = new MapPoint(cellId);

        if (throughEntities)
        {
            if (!this.network.getMap().NoEntitiesOnCell(cellId))
                return 20;

            if (speed >= 0)
                return 1 + (5 - speed);

            return 1 + 11 + Math.abs(speed);
        }

        double cost = 1.0D;
        MapPoint adjCell = null;

        if (!this.network.getMap().NoEntitiesOnCell(cellId))
            cost += 0.3;

        adjCell = new MapPoint(cell.X + 1, cell.Y);
        if (adjCell != null && !this.network.getMap().NoEntitiesOnCell(adjCell.CellId))
            cost += 0.3;

        adjCell = new MapPoint(cell.X, cell.Y + 1);
        if (adjCell != null && !this.network.getMap().NoEntitiesOnCell(adjCell.CellId))
            cost += 0.3;

        adjCell = new MapPoint(cell.X - 1, cell.Y);
        if (adjCell != null && !this.network.getMap().NoEntitiesOnCell(adjCell.CellId))
            cost += 0.3;

        adjCell = new MapPoint(cell.X, cell.Y - 1);
        if (adjCell != null && !this.network.getMap().NoEntitiesOnCell(adjCell.CellId))
            cost += 0.3;

        // todo
        // if (m_context.IsCellMarked(cell))
        //                cost += 0.2;

        return cost;
    }

	public int NearerSquare() {
		int y = 0;
		double distance = 9999999;
		double tempDistance = 0;

		for (int tempY = 0; tempY < OpenList.size(); tempY++) {
			tempDistance = GetCellInfo(OpenList.get(tempY).Y, OpenList.get(tempY).X).Heuristic
					+ GetCellInfo(OpenList.get(tempY).Y, OpenList.get(tempY).X).MovementCost;
			if (tempDistance <= distance) {
				distance = tempDistance;
				y = tempY;
			}
		}
		return y;
	}
	
    public boolean IsChangeZone(int fCell, int sCell)
    {
        return this.network.getMap().getCells().get(fCell).getMoveZone() != this.network.getMap().getCells().get(sCell).getMoveZone() &&
               Math.abs(this.network.getMap().getCells().get(fCell).getFloor()) == Math.abs(this.network.getMap().getCells().get(sCell).getFloor());
    }

	public void CloseSquare(int y, int x) {
		for(int i = 0; i < OpenList.size() ; i++){
			if (OpenList.get(i).X == x && OpenList.get(i).Y == y){
				OpenList.remove(i);
			}
		}
		CellInfo cell = GetCellInfo(y, x);
		cell.Opened = false;
		cell.Closed = true;
	}

	public void OpenSquare(int y, int x, int[] parent, int moveCost, double heuristic, boolean newSquare) {
		if (!newSquare)
			for (OpenSquare op : OpenList) {
				if (op.Y == y && op.X == x) {
					newSquare = true;
					break;
				}
			}

		if (!newSquare) {
			OpenList.add(new OpenSquare(y, x));
			for(int i = 0; i < MapStatus.size() ; i++){
				if (MapStatus.get(i).X == x && MapStatus.get(i).Y == y) {
					MapStatus.remove(i);
				}
			}
			MapStatus.add(new CellInfo(heuristic, null, true, false, x, y));
		}

		CellInfo cell = GetCellInfo(y, x);
		cell.Parent = parent;
		cell.MovementCost = moveCost;
	}

	public CellInfo GetCellInfo(int y, int x) {
		CellInfo cell = null;
		try {
			for (CellInfo c : MapStatus) {
				if (c.X == x && c.Y == y) {
					cell = c;
					break;
				}
			}
		} catch (Exception e) {
			this.network.append("CELL IS NULL");
			cell = null;
		}
		return cell;
	}

	public boolean IsClosed(int y, int x) {
		CellInfo cellInfo = GetCellInfo(y, x);
		if (cellInfo == null || !cellInfo.Closed)
			return false;
		return true;
	}
	
    public void MovementPathFromArray(MapPoint[] squares)
    {
        PathElement path = null;

        for (int i = 0; i <= squares.length - 2; i++)
        {
            path = new PathElement();
            path.Cell = squares[i];
            path.Orientation = squares[i].OrientationTo(squares[i + 1]);
            MovPath.Cells.add(path);
        }
        System.out.println("---------------NOT COMPRESSED------------------");
        for (PathElement cells : MovPath.Cells) {
			this.network.append(cells.Cell.CellId + " - Orientation : " + cells.Orientation);
		}
        System.out.println("\n\n---------------COMPRESSED------------------");
        MovPath.Compress();
        for (PathElement cells : MovPath.Cells) {
			this.network.append(cells.Cell.CellId + " - Orientation : " + cells.Orientation);
		}
    }
}
