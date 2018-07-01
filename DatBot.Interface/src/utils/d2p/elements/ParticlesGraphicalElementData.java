package utils.d2p.elements;

public class ParticlesGraphicalElementData extends GraphicalElementData {
    private short scriptId;

    public ParticlesGraphicalElementData(int id, int type) {
        super(id, type);
    }

    public short getScriptId() {
        return scriptId;
    }

    public void setScriptId(short scriptId) {
        this.scriptId = scriptId;
    }
}
