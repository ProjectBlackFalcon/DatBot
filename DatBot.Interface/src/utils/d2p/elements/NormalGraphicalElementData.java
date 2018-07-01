package utils.d2p.elements;

public class NormalGraphicalElementData extends GraphicalElementData {
    private int gfxId;
    private byte height;
    private boolean horizontalSymmetry;
    private short originX, originY;
    private short sizeX, sizeY;

    public NormalGraphicalElementData(int id, int type) {
        super(id, type);
    }

    public int getGfxId() {
        return gfxId;
    }

    public void setGfxId(int gfxId) {
        this.gfxId = gfxId;
    }

    public byte getHeight() {
        return height;
    }

    public void setHeight(byte height) {
        this.height = height;
    }

    public boolean isHorizontalSymmetry() {
        return horizontalSymmetry;
    }

    public void setHorizontalSymmetry(boolean horizontalSymmetry) {
        this.horizontalSymmetry = horizontalSymmetry;
    }

    public short getOriginX() {
        return originX;
    }

    public void setOriginX(short originX) {
        this.originX = originX;
    }

    public short getOriginY() {
        return originY;
    }

    public void setOriginY(short originY) {
        this.originY = originY;
    }

    public short getSizeX() {
        return sizeX;
    }

    public void setSizeX(short sizeX) {
        this.sizeX = sizeX;
    }

    public short getSizeY() {
        return sizeY;
    }

    public void setSizeY(short sizeY) {
        this.sizeY = sizeY;
    }
}
