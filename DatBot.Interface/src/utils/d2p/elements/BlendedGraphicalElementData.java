package utils.d2p.elements;

public class BlendedGraphicalElementData extends NormalGraphicalElementData {
    private String blendMode;

    public BlendedGraphicalElementData(int id, int type) {
        super(id, type);
    }

    public String getBlendMode() {
        return blendMode;
    }

    public void setBlendMode(String blendMode) {
        this.blendMode = blendMode;
    }
}
