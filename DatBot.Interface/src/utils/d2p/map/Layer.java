package utils.d2p.map;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import protocol.network.util.DofusDataReader;
import utils.d2p.MapManager;

public class Layer {
	
    private int layerId;
    private int cellsCount;
    private List<Cell> cells = new ArrayList<Cell>();
    
    public void init(DofusDataReader reader, int mapVersion) throws IOException
    {
        if (mapVersion >= 9)
            layerId = reader.readByte();
        else
            layerId = reader.readInt();

        cellsCount = reader.readShort();
        
        for (int i = 0; i < cellsCount; i++)
        {
        	Cell item = new Cell();
            item.init(reader, mapVersion);
            cells.add(item);
        }
    }
    
	public int getLayerId()
	{
		return layerId;
	}
	public void setLayerId(int layerId)
	{
		this.layerId = layerId;
	}
	public int getCellsCount()
	{
		return cellsCount;
	}
	public void setCellsCount(int cellsCount)
	{
		this.cellsCount = cellsCount;
	}
	public List<Cell> getCells()
	{
		return cells;
	}
	public void setCells(List<Cell> cells)
	{
		this.cells = cells;
	}

	@Override
	public String toString()
	{
		return "Layer [layerId=" + layerId + ", cellsCount=" + cellsCount + ", cells=" + cells + "]";
	}
}
