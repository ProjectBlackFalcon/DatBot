package utils.d2p.elements;

public class EntityGraphicalElementData extends GraphicalElementData {
    private String entityLook;
    private boolean horizontalSymmetry;
    private boolean playAnimation;
    private boolean playAnimStatic;
    private int minDelay, maxDelay;

    public EntityGraphicalElementData(int id, int type) {
        super(id, type);
    }

    public String getEntityLook() {
        return entityLook;
    }

    public void setEntityLook(String entityLook) {
        this.entityLook = entityLook;
    }

    public boolean isHorizontalSymmetry() {
        return horizontalSymmetry;
    }

    public void setHorizontalSymmetry(boolean horizontalSymmetry) {
        this.horizontalSymmetry = horizontalSymmetry;
    }

    public boolean isPlayAnimation() {
        return playAnimation;
    }

    public void setPlayAnimation(boolean playAnimation) {
        this.playAnimation = playAnimation;
    }

    public boolean isPlayAnimStatic() {
        return playAnimStatic;
    }

    public void setPlayAnimStatic(boolean playAnimStatic) {
        this.playAnimStatic = playAnimStatic;
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
