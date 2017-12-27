package game.map;

import java.util.ArrayList;
import java.util.List;

import game.Entity;

public class Map {
    public void setBackgroundAlpha(long backgroundAlpha) {
		BackgroundAlpha = backgroundAlpha;
	}
	public void setBackgroundBlue(long backgroundBlue) {
		BackgroundBlue = backgroundBlue;
	}
	public void setBackgroundFixtures(ArrayList<Fixture> backgroundFixtures) {
		BackgroundFixtures = backgroundFixtures;
	}
	public void setBackgroundGreen(long backgroundGreen) {
		BackgroundGreen = backgroundGreen;
	}
	public void setBackgroundRed(long backgroundRed) {
		BackgroundRed = backgroundRed;
	}
	public void setBackgroundsCount(long backgroundsCount) {
		BackgroundsCount = backgroundsCount;
	}
	public void setBottomNeighbourId(long bottomNeighbourId) {
		BottomNeighbourId = bottomNeighbourId;
	}
	public void setEncrypted(boolean encrypted) {
		Encrypted = encrypted;
	}
	public void setEncryptedVersion(long encryptedVersion) {
		EncryptedVersion = encryptedVersion;
	}
	public void setForegroundFixtures(ArrayList<Fixture> foregroundFixtures) {
		ForegroundFixtures = foregroundFixtures;
	}
	public void setForegroundsCount(long foregroundsCount) {
		ForegroundsCount = foregroundsCount;
	}
	public void setGridAlpha(long gridAlpha) {
		GridAlpha = gridAlpha;
	}
	public void setGridBlue(long gridBlue) {
		GridBlue = gridBlue;
	}
	public void setGridGreen(long gridGreen) {
		GridGreen = gridGreen;
	}
	public void setGridRed(long gridRed) {
		GridRed = gridRed;
	}
	public void setGroundCRC(String groundCRC) {
		GroundCRC = groundCRC;
	}
	public void setId(long id) {
		Id = id;
	}
	public void setIsUsingNewMovementSystem(boolean isUsingNewMovementSystem) {
		IsUsingNewMovementSystem = isUsingNewMovementSystem;
	}
	public void setLayers(List<Layer> layers) {
		Layers = layers;
	}
	public void setLayersCount(long layersCount) {
		LayersCount = layersCount;
	}
	public void setLeftNeighbourId(long leftNeighbourId) {
		LeftNeighbourId = leftNeighbourId;
	}
	public void setMapType(long mapType) {
		MapType = mapType;
	}
	public void setMapVersion(long mapVersion) {
		MapVersion = mapVersion;
	}
	public void setPresetId(long presetId) {
		PresetId = presetId;
	}
	public void setRelativeId(long relativeId) {
		RelativeId = relativeId;
	}
	public void setRightNeighbourId(long rightNeighbourId) {
		RightNeighbourId = rightNeighbourId;
	}
	public void setShadowBonusOnEntities(long shadowBonusOnEntities) {
		ShadowBonusOnEntities = shadowBonusOnEntities;
	}
	public void setSubAreaId(long subAreaId) {
		SubAreaId = subAreaId;
	}
	public void setTopNeighbourId(long topNeighbourId) {
		TopNeighbourId = topNeighbourId;
	}
	public void setUseLowPassFilter(boolean useLowPassFilter) {
		UseLowPassFilter = useLowPassFilter;
	}
	public void setUseReverb(boolean useReverb) {
		UseReverb = useReverb;
	}
	public void setZoomOffsetX(long zoomOffsetX) {
		ZoomOffsetX = zoomOffsetX;
	}
	public void setZoomOffsetY(long zoomOffsetY) {
		ZoomOffsetY = zoomOffsetY;
	}
	public void setZoomScale(double zoomScale) {
		ZoomScale = zoomScale;
	}
	public void setCells(List<CellData> cells) {
		Cells = cells;
	}

	public static long getBackgroundAlpha() {
		return BackgroundAlpha;
	}
	public static long getBackgroundBlue() {
		return BackgroundBlue;
	}
	public static List<Fixture> getBackgroundFixtures() {
		return BackgroundFixtures;
	}
	public static long getBackgroundGreen() {
		return BackgroundGreen;
	}
	public static long getBackgroundRed() {
		return BackgroundRed;
	}
	public static long getBackgroundsCount() {
		return BackgroundsCount;
	}
	public static long getBottomNeighbourId() {
		return BottomNeighbourId;
	}
	public static boolean isEncrypted() {
		return Encrypted;
	}
	public static long getEncryptedVersion() {
		return EncryptedVersion;
	}
	public static List<Fixture> getForegroundFixtures() {
		return ForegroundFixtures;
	}
	public static long getForegroundsCount() {
		return ForegroundsCount;
	}
	public static long getGridAlpha() {
		return GridAlpha;
	}
	public static long getGridBlue() {
		return GridBlue;
	}
	public static long getGridGreen() {
		return GridGreen;
	}
	public static long getGridRed() {
		return GridRed;
	}
	public static String getGroundCRC() {
		return GroundCRC;
	}
	public static long getId() {
		return Id;
	}
	public static boolean isIsUsingNewMovementSystem() {
		return IsUsingNewMovementSystem;
	}
	public static List<Layer> getLayers() {
		return Layers;
	}
	public static long getLayersCount() {
		return LayersCount;
	}
	public static long getLeftNeighbourId() {
		return LeftNeighbourId;
	}
	public static long getMapType() {
		return MapType;
	}
	public static long getMapVersion() {
		return MapVersion;
	}
	public static long getPresetId() {
		return PresetId;
	}
	public static long getRelativeId() {
		return RelativeId;
	}
	public static long getRightNeighbourId() {
		return RightNeighbourId;
	}
	public static long getShadowBonusOnEntities() {
		return ShadowBonusOnEntities;
	}
	public static long getSubAreaId() {
		return SubAreaId;
	}
	public static long getTopNeighbourId() {
		return TopNeighbourId;
	}
	public static boolean isUseLowPassFilter() {
		return UseLowPassFilter;
	}
	public static boolean isUseReverb() {
		return UseReverb;
	}
	public static long getZoomOffsetX() {
		return ZoomOffsetX;
	}
	public static long getZoomOffsetY() {
		return ZoomOffsetY;
	}
	public static double getZoomScale() {
		return ZoomScale;
	}
	public static List<CellData> getCells() {
		return Cells;
	}
	public static long BackgroundAlpha;
    public static long BackgroundBlue;
    public static List<Fixture> BackgroundFixtures;
    public static long BackgroundGreen;
    public static long BackgroundRed;
    public static long BackgroundsCount;
    public static long BottomNeighbourId;
    public static boolean Encrypted;
    public static long EncryptedVersion;
    public static List<Fixture> ForegroundFixtures;
    public static long ForegroundsCount;
    public static long GridAlpha;
    public static long GridBlue;
    public static long GridGreen;
    public static long GridRed;
    public static String GroundCRC;
    public static long Id;
    public static boolean IsUsingNewMovementSystem;
    public static List<Layer> Layers;
    public static long LayersCount;
    public static long LeftNeighbourId;
    public static long MapType;
    public static long MapVersion;
    public static long PresetId;
    public static long RelativeId;
    public static long RightNeighbourId;
    public static long ShadowBonusOnEntities;
    public static long SubAreaId;
    public static long TopNeighbourId;
    public static boolean UseLowPassFilter;
    public static boolean UseReverb;
    public static long ZoomOffsetX;
    public static long ZoomOffsetY;
    public static double ZoomScale;
    public static List<CellData> Cells;
    public static List<Entity> Entities = new ArrayList<Entity>();
    
    
	public static boolean NoEntitiesOnCell(int cellId) {
		for (Entity e : Entities) {
			if (e.cellId == cellId) return false;
		}
		return true;
	} 
	
    public static boolean NothingOnCell(int cellId)
    {
//    	&& NoEntitiesOnCell(cellId) 
        return IsWalkable(cellId);
    }
    
    public static boolean IsLineOfSight(int cellId)
    {
        return cellId >= 0 && cellId < 560 && Cells.get(cellId).Los;
    }

    public static boolean IsWalkable(int cellId)
    {
        return cellId >= 0 && cellId < 560 && Cells.get(cellId).Mov;
    }
}
