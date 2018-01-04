package utils.d2p.map;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import protocol.network.util.DofusDataReader;
import utils.d2p.MapManager;
import utils.d2p.map.elements.BasicElement;

public class Cell {

    private int CellId;
    private List<BasicElement> Elements = new ArrayList<BasicElement>();
    private int ElementsCount;

    // Methods
    public void init(DofusDataReader Reader, int MapVersion) throws IOException
    {
        setCellId(Reader.readShort());
        setElementsCount(Reader.readShort());
        for (int i = 0; i < getElementsCount(); i++)
        {
        	BasicElement be = BasicElement.GetElementFromType(Reader.readByte());
            be.Init(Reader, MapVersion);
            getElements().add(be);
        }
    }

	@Override
	public String toString()
	{
		return "Cell [CellId=" + getCellId() + ", Elements=" + getElements() + ", ElementsCount=" + getElementsCount() + "]";
	}

	public int getElementsCount()
	{
		return ElementsCount;
	}

	public void setElementsCount(int elementsCount)
	{
		ElementsCount = elementsCount;
	}

	public List<BasicElement> getElements()
	{
		return Elements;
	}

	public void setElements(List<BasicElement> elements)
	{
		Elements = elements;
	}

	public int getCellId()
	{
		return CellId;
	}

	public void setCellId(int cellId)
	{
		CellId = cellId;
	}
}
