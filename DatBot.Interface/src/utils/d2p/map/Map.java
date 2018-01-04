package utils.d2p.map;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.network.util.DofusDataReader;

public class Map {

	private String key = "649ae451ca33ec53bbcbcc33becf15f4";

	private long BackgroundAlpha;
	private int BackgroundBlue;
	private List<Fixture> BackgroundFixtures;
	private int BackgroundGreen;
	private int BackgroundRed;
	private long BackgroundColor;
	private int BackgroundsCount;
	private int BottomNeighbourId;
	private boolean Encrypted;
	private int EncryptedVersion;
	private List<Fixture> ForegroundFixtures;
	private int ForegroundsCount;
	private long GridAlpha;
	private long GridBlue;
	private long GridGreen;
	private long GridRed;
	private long GridColor;
	private int GroundCRC;
	private int Id;
	private boolean IsUsingNewMovementSystem;
	private List<Layer> Layers;
	private int LayersCount;
	private int LeftNeighbourId;
	private int MapType;
	private int MapVersion;
	private int PresetId;
	private int RelativeId;
	private int RightNeighbourId;
	private int ShadowBonusOnEntities;
	private int SubAreaId;
	private int TopNeighbourId;
	private boolean UseLowPassFilter;
	private boolean UseReverb;
	private int ZoomOffsetX;
	private int ZoomOffsetY;
	private double ZoomScale;
	private List<CellData> Cells = new ArrayList<CellData>();
	private int CellsCount;

	public boolean IsLineOfSight(int cellId)
	{
		return cellId >= 0 && cellId < CellsCount && Cells.get(cellId).isLos();
	}

	public boolean IsWalkable(int cellId)
	{
		return cellId >= 0 && cellId < CellsCount && Cells.get(cellId).isMov();
	}

	private int tacticalModeTemplateId;
	private WorldPoint position;

	public Map()
	{
		BackgroundFixtures = new ArrayList<Fixture>();
		ForegroundFixtures = new ArrayList<Fixture>();
		Layers = new ArrayList<Layer>();
	}

	public void Init(DofusDataReader reader) throws IOException
	{
		byte[] decryptionKey = key.getBytes("UTF-8");
		int header = reader.readByte();
		MapVersion = reader.readByte();
		Id = reader.readInt();

		if (MapVersion >= 7)
		{
			Encrypted = reader.readBoolean();
			EncryptedVersion = reader.readByte();
			int dataLen = reader.readInt();
			if (Encrypted)
			{
				byte[] encryptedData = reader.readBytes(dataLen);

				for (int i = 0; i < encryptedData.length; i++)
					encryptedData[i] = (byte) (encryptedData[i] ^ decryptionKey[i % decryptionKey.length]);
				reader = new DofusDataReader(new ByteArrayInputStream(encryptedData));
				reader.dis.mark(0);
			}
		}

		RelativeId = reader.readInt();
        this.position = new WorldPoint(this.RelativeId);
		MapType = reader.readByte();
		SubAreaId = reader.readInt();
		TopNeighbourId = reader.readInt();
		BottomNeighbourId = reader.readInt();
		LeftNeighbourId = reader.readInt();
		RightNeighbourId = reader.readInt();
		ShadowBonusOnEntities = (int) reader.readInt();

		if (MapVersion >= 9)
		{
			int readColor = reader.readInt();
			BackgroundAlpha = (readColor & 4278190080L) >> 32;
			BackgroundRed = (readColor & 16711680) >> 16;
			BackgroundGreen = (readColor & 65280) >> 8;
			BackgroundBlue = readColor & 255;
			readColor = reader.readInt();
			GridAlpha = (readColor & 4278190080L) >> 32;
			GridRed = (readColor & 16711680) >> 16;
			GridGreen = (readColor & 65280) >> 8;
			GridBlue = readColor & 255;
			this.GridColor = (GridAlpha & 255) << 32 | (GridRed & 255) << 16 | (GridGreen & 255) << 8 | GridBlue & 255;
		}
		else if (MapVersion >= 3)
		{
			BackgroundRed = reader.readByte();
			BackgroundGreen = reader.readByte();
			BackgroundBlue = reader.readByte();
		}
		this.BackgroundColor = (this.BackgroundAlpha & 255) << 32 | (this.BackgroundRed & 255) << 16 | (this.BackgroundGreen & 255) << 8 | this.BackgroundBlue & 255;

		if (MapVersion >= 4)
		{
			ZoomScale = ((double) reader.readShort()) / 100;
			ZoomOffsetX = reader.readShort();
			ZoomOffsetY = reader.readShort();
			if (this.ZoomScale < 1)
			{
				this.ZoomScale = 1;
				this.ZoomOffsetX = this.ZoomOffsetY = 0;
			}
		}

		if (this.MapVersion > 10)
		{
			this.tacticalModeTemplateId = reader.readInt();
		}

		UseLowPassFilter = reader.readBoolean();
		UseReverb = reader.readBoolean();

		if (UseReverb)
		{
			PresetId = reader.readInt();
		}
		else
		{
			this.PresetId = -1;
		}
		BackgroundsCount = reader.readByte();
		for (int i = 0; i < BackgroundsCount; i++)
		{
			Fixture item = new Fixture();
			item.init(reader);
			BackgroundFixtures.add(item);
		}
		ForegroundsCount = reader.readByte();
		for (int i = 0; i < ForegroundsCount; i++)
		{
			Fixture fixture2 = new Fixture();
			fixture2.init(reader);
			ForegroundFixtures.add(fixture2);
		}
		CellsCount = 560;
		reader.readInt();
		GroundCRC = reader.readInt();
		LayersCount = reader.readByte();
		for (int i = 0; i < LayersCount; i++)
		{
			Layer layer = new Layer();
			layer.init(reader, MapVersion);
			Layers.add(layer);
		}
		long oldMvtSys = 0;
		for (int i = 0; i < CellsCount; i++)
		{
			CellData data = new CellData();
			data.init(reader, MapVersion);
			if (oldMvtSys == 0) oldMvtSys = data.getMoveZone();
			if (data.getMoveZone() != oldMvtSys) IsUsingNewMovementSystem = true;
			Cells.add(data);
		}
	}

	public String getKey()
	{
		return key;
	}

	public void setKey(String key)
	{
		this.key = key;
	}

	public long getBackgroundAlpha()
	{
		return BackgroundAlpha;
	}

	public void setBackgroundAlpha(long backgroundAlpha)
	{
		BackgroundAlpha = backgroundAlpha;
	}

	public int getBackgroundBlue()
	{
		return BackgroundBlue;
	}

	public void setBackgroundBlue(int backgroundBlue)
	{
		BackgroundBlue = backgroundBlue;
	}

	public List<Fixture> getBackgroundFixtures()
	{
		return BackgroundFixtures;
	}

	public void setBackgroundFixtures(List<Fixture> backgroundFixtures)
	{
		BackgroundFixtures = backgroundFixtures;
	}

	public int getBackgroundGreen()
	{
		return BackgroundGreen;
	}

	public void setBackgroundGreen(int backgroundGreen)
	{
		BackgroundGreen = backgroundGreen;
	}

	public int getBackgroundRed()
	{
		return BackgroundRed;
	}

	public void setBackgroundRed(int backgroundRed)
	{
		BackgroundRed = backgroundRed;
	}

	public long getBackgroundColor()
	{
		return BackgroundColor;
	}

	public void setBackgroundColor(long backgroundColor)
	{
		BackgroundColor = backgroundColor;
	}

	public int getBackgroundsCount()
	{
		return BackgroundsCount;
	}

	public void setBackgroundsCount(int backgroundsCount)
	{
		BackgroundsCount = backgroundsCount;
	}

	public int getBottomNeighbourId()
	{
		return BottomNeighbourId;
	}

	public void setBottomNeighbourId(int bottomNeighbourId)
	{
		BottomNeighbourId = bottomNeighbourId;
	}

	public boolean isEncrypted()
	{
		return Encrypted;
	}

	public void setEncrypted(boolean encrypted)
	{
		Encrypted = encrypted;
	}

	public int getEncryptedVersion()
	{
		return EncryptedVersion;
	}

	public void setEncryptedVersion(int encryptedVersion)
	{
		EncryptedVersion = encryptedVersion;
	}

	public List<Fixture> getForegroundFixtures()
	{
		return ForegroundFixtures;
	}

	public void setForegroundFixtures(List<Fixture> foregroundFixtures)
	{
		ForegroundFixtures = foregroundFixtures;
	}

	public int getForegroundsCount()
	{
		return ForegroundsCount;
	}

	public void setForegroundsCount(int foregroundsCount)
	{
		ForegroundsCount = foregroundsCount;
	}

	public long getGridAlpha()
	{
		return GridAlpha;
	}

	public void setGridAlpha(long gridAlpha)
	{
		GridAlpha = gridAlpha;
	}

	public long getGridBlue()
	{
		return GridBlue;
	}

	public void setGridBlue(long gridBlue)
	{
		GridBlue = gridBlue;
	}

	public long getGridGreen()
	{
		return GridGreen;
	}

	public void setGridGreen(long gridGreen)
	{
		GridGreen = gridGreen;
	}

	public long getGridRed()
	{
		return GridRed;
	}

	public void setGridRed(long gridRed)
	{
		GridRed = gridRed;
	}

	public long getGridColor()
	{
		return GridColor;
	}

	public void setGridColor(long gridColor)
	{
		GridColor = gridColor;
	}

	public int getGroundCRC()
	{
		return GroundCRC;
	}

	public void setGroundCRC(int groundCRC)
	{
		GroundCRC = groundCRC;
	}

	public int getId()
	{
		return Id;
	}

	public void setId(int id)
	{
		Id = id;
	}

	public boolean isIsUsingNewMovementSystem()
	{
		return IsUsingNewMovementSystem;
	}

	public void setIsUsingNewMovementSystem(boolean isUsingNewMovementSystem)
	{
		IsUsingNewMovementSystem = isUsingNewMovementSystem;
	}

	public List<Layer> getLayers()
	{
		return Layers;
	}

	public void setLayers(List<Layer> layers)
	{
		Layers = layers;
	}

	public int getLayersCount()
	{
		return LayersCount;
	}

	public void setLayersCount(int layersCount)
	{
		LayersCount = layersCount;
	}

	public int getLeftNeighbourId()
	{
		return LeftNeighbourId;
	}

	public void setLeftNeighbourId(int leftNeighbourId)
	{
		LeftNeighbourId = leftNeighbourId;
	}

	public int getMapType()
	{
		return MapType;
	}

	public void setMapType(int mapType)
	{
		MapType = mapType;
	}

	public int getMapVersion()
	{
		return MapVersion;
	}

	public void setMapVersion(int mapVersion)
	{
		MapVersion = mapVersion;
	}

	public int getPresetId()
	{
		return PresetId;
	}

	public void setPresetId(int presetId)
	{
		PresetId = presetId;
	}

	public int getRelativeId()
	{
		return RelativeId;
	}

	public void setRelativeId(int relativeId)
	{
		RelativeId = relativeId;
	}

	public int getRightNeighbourId()
	{
		return RightNeighbourId;
	}

	public void setRightNeighbourId(int rightNeighbourId)
	{
		RightNeighbourId = rightNeighbourId;
	}

	public int getShadowBonusOnEntities()
	{
		return ShadowBonusOnEntities;
	}

	public void setShadowBonusOnEntities(int shadowBonusOnEntities)
	{
		ShadowBonusOnEntities = shadowBonusOnEntities;
	}

	public int getSubAreaId()
	{
		return SubAreaId;
	}

	public void setSubAreaId(int subAreaId)
	{
		SubAreaId = subAreaId;
	}

	public int getTopNeighbourId()
	{
		return TopNeighbourId;
	}

	public void setTopNeighbourId(int topNeighbourId)
	{
		TopNeighbourId = topNeighbourId;
	}

	public boolean isUseLowPassFilter()
	{
		return UseLowPassFilter;
	}

	public void setUseLowPassFilter(boolean useLowPassFilter)
	{
		UseLowPassFilter = useLowPassFilter;
	}

	public boolean isUseReverb()
	{
		return UseReverb;
	}

	public void setUseReverb(boolean useReverb)
	{
		UseReverb = useReverb;
	}

	public int getZoomOffsetX()
	{
		return ZoomOffsetX;
	}

	public void setZoomOffsetX(int zoomOffsetX)
	{
		ZoomOffsetX = zoomOffsetX;
	}

	public int getZoomOffsetY()
	{
		return ZoomOffsetY;
	}

	public void setZoomOffsetY(int zoomOffsetY)
	{
		ZoomOffsetY = zoomOffsetY;
	}

	public double getZoomScale()
	{
		return ZoomScale;
	}

	public void setZoomScale(double zoomScale)
	{
		ZoomScale = zoomScale;
	}

	public List<CellData> getCells()
	{
		return Cells;
	}

	public void setCells(List<CellData> cells)
	{
		Cells = cells;
	}

	public int getCellsCount()
	{
		return CellsCount;
	}

	public void setCellsCount(int cellsCount)
	{
		CellsCount = cellsCount;
	}

	public int getTacticalModeTemplateId()
	{
		return tacticalModeTemplateId;
	}

	public void setTacticalModeTemplateId(int tacticalModeTemplateId)
	{
		this.tacticalModeTemplateId = tacticalModeTemplateId;
	}

	public WorldPoint getPosition()
	{
		return position;
	}

	public void setPosition(WorldPoint position)
	{
		this.position = position;
	}

	@Override
	public String toString()
	{
		return "Map [ BackgroundAlpha=" + BackgroundAlpha + ", BackgroundBlue=" + BackgroundBlue + ", BackgroundFixtures=" + BackgroundFixtures + ", BackgroundGreen=" + BackgroundGreen + ", BackgroundRed=" + BackgroundRed + ", BackgroundColor=" + BackgroundColor + ", BackgroundsCount=" + BackgroundsCount + ", BottomNeighbourId="
			+ BottomNeighbourId + ", Encrypted=" + Encrypted + ", EncryptedVersion=" + EncryptedVersion + ", ForegroundFixtures=" + ForegroundFixtures + ", ForegroundsCount=" + ForegroundsCount + ", GridAlpha=" + GridAlpha + ", GridBlue=" + GridBlue + ", GridGreen=" + GridGreen + ", GridRed=" + GridRed + ", GridColor=" + GridColor + ", GroundCRC="
			+ GroundCRC + ", Id=" + Id + ", IsUsingNewMovementSystem=" + IsUsingNewMovementSystem + ", Layers=" + Layers + ", LayersCount=" + LayersCount + ", LeftNeighbourId=" + LeftNeighbourId + ", MapType=" + MapType + ", MapVersion=" + MapVersion + ", PresetId=" + PresetId + ", RelativeId=" + RelativeId + ", RightNeighbourId="
			+ RightNeighbourId + ", ShadowBonusOnEntities=" + ShadowBonusOnEntities + ", SubAreaId=" + SubAreaId + ", TopNeighbourId=" + TopNeighbourId + ", UseLowPassFilter=" + UseLowPassFilter + ", UseReverb=" + UseReverb + ", ZoomOffsetX=" + ZoomOffsetX + ", ZoomOffsetY=" + ZoomOffsetY + ", ZoomScale=" + ZoomScale + ", Cells=" + Cells
			+ ", CellsCount=" + CellsCount + ", tacticalModeTemplateId=" + tacticalModeTemplateId + ", position=" + position + "]";
	}

	public boolean NoEntitiesOnCell(int cellId)
	{
		return true;
	}

	public boolean NothingOnCell(int i)
	{
        return IsWalkable(i);
	}
}
