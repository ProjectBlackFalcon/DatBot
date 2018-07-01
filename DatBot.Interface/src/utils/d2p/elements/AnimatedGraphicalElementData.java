package utils.d2p.elements;

public class AnimatedGraphicalElementData extends NormalGraphicalElementData {
    private int minDelay, maxDelay;

    public AnimatedGraphicalElementData(int id, int type) {
        super(id, type);
    }

    public int getMinDelay() {
        return minDelay;
    }

    public void setMinDelay(int minDelay) {
        this.minDelay = minDelay;
    }

    public int getMaxDelay() {
        return maxDelay;
    }

    public void setMaxDelay(int maxDelay) {
        this.maxDelay = maxDelay;
    }
}
